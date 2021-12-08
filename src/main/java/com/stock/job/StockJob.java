package com.stock.job;

import com.google.gson.JsonSyntaxException;
import com.stock.dto.CompanyDto;
import com.stock.dto.StockInfoDto;
import com.stock.repository.CompanyRepository;
import com.stock.service.StockLoaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
@EnableAsync
public class StockJob {

    private final StockLoaderService stockLoaderService;
    private final CompanyRepository companyRepository;

    private List<CompanyDto> dbCompanies;

    private int counter;

    @Autowired
    private SQSHandler sqsHandler;

    @PostConstruct
    public void initLoadingStocks() {
        loadStocks();
    }


    @Scheduled(fixedRate = 50000)
    public void loadStocks() {
        log.info("======================================== COUNTER: " + ++counter);
        log.info("Start getting companies");
        List<CompanyDto> companies = new ArrayList<>();
        List<StockInfoDto> stocks = new ArrayList<>();
        try {
            companies = stockLoaderService.loadCompanies();
            stocks = stockLoaderService.stockInfoCollectorHandler(companies);
        } catch (RestClientException | JsonSyntaxException ex) {
            log.error(ex.getMessage());
            sqsHandler.sendErrorNotificationToSQS(ex.getMessage());
            return;
        }

        log.info("Loaded {} stockInfos entities.", stocks.size());

        updateCompaniesInDB(companies);

        log.info("All stockInfos saved in DataBase.", stocks.size());
    }

    private void updateCompaniesInDB(List<CompanyDto> loadedCompanies) {
        if (dbCompanies == null || dbCompanies.isEmpty()) {
            dbCompanies = companyRepository.findAll();
        }

        if (dbCompanies.isEmpty()) {
            companyRepository.saveAll(loadedCompanies);
            return;
        }

        List<CompanyDto> updatedCompanies = getCompaniesDiff(loadedCompanies, dbCompanies);
        List<CompanyDto> companiesToDelete = getCompaniesDiff(dbCompanies, loadedCompanies);

        if (!companiesToDelete.isEmpty()) {
            log.info("===============  Companies will be deleted =================  : " + companiesToDelete.size());
            companyRepository.deleteAll(companiesToDelete);
        }

        if (!updatedCompanies.isEmpty()) {
            log.info("===============  Companies will be updated =================  : " + updatedCompanies.size());
            sqsHandler.sendSuccessNotificationsToSQS(updatedCompanies);
            dbCompanies = companyRepository.saveAll(updatedCompanies);
        }
    }

    // save those Companies from collection, that is not in remove
    private List<CompanyDto> getCompaniesDiff(List<CompanyDto> collection, List<CompanyDto> remove) {
        return (List<CompanyDto>) CollectionUtils.removeAll(collection, remove);
    }

    // The @Scheduled annotation is activated by
    // ScheduledAnnotationBeanPostProcessor.postProcessAfterInitialization() which is invoked after
    // any bean initialization callbacks (like InitializingBean's afterPropertiesSet
    // or a custom init-method).

    @Scheduled(cron = "*/20 * * * * *")
    public void consoleOutputHandler() {
        log.info("*************  The top 5 highest value stocks  *******************");

        for (StockInfoDto stock : companyRepository.findStocksByVolume()) {
            log.info("Company : {}, Volume: {}, PrevVolume {}", stock.getCompanyName(), stock.getVolume(), stock.getPreviousVolume());
        }

        log.info("******  The most recent 5 companies that have the greatest change percent in stock value  ****");
        for (StockInfoDto stock : companyRepository.findStocksChangePrice()) {
            log.info("Company : {}, changePrice: {}, PrevPrice {}", stock.getCompanyName(), stock.getChangePercent(), stock.getLatestPrice() + (stock.getChangePercent() * stock.getLatestPrice()));
        }
    }
}
