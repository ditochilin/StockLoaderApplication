package com.stock.job;

import com.stock.dto.CompanyDto;
import com.stock.dto.StockInfoDto;
import com.stock.repository.CompanyRepository;
import com.stock.service.StockLoaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
@EnableAsync
public class StockJob {

    private final StockLoaderService stockLoaderService;
    private final CompanyRepository companyRepository;

    @PostConstruct
    public void loadStocks() {

        if (!isDatabaseEmpty()) {
            return;
            // clearDatabase();
        }
        log.info("Start getting companies");
        List<CompanyDto> companies = stockLoaderService.loadCompanies();
        List<StockInfoDto> stocks = stockLoaderService.stockInfoCollectorHandler(companies);

        log.info("Loaded {} stockInfos entities.", stocks.size());

        saveCompanies(companies);
        log.info("All stockInfos saved in DataBase.", stocks.size());

    }

    private void clearDatabase() {
        companyRepository.deleteAll();
    }

    private boolean isDatabaseEmpty() {
        return companyRepository.count() == 0;
    }

    private void saveCompanies(List<CompanyDto> companies) {
        companyRepository.saveAll(companies);
    }

    // The @Scheduled annotation is activated by
    // ScheduledAnnotationBeanPostProcessor.postProcessAfterInitialization() which is invoked after
    // any bean initialization callbacks (like InitializingBean's afterPropertiesSet
    // or a custom init-method).

    @Scheduled(cron = "*/5 * * * * *")
    public void consoleOutputHandler() {
        log.info("*************  The top 5 highest value stocks  *******************");

        for (StockInfoDto stock : companyRepository.findStocksByVolume()) {
            log.info("Company : {}, Volume: {}, PrevVolume {}", stock.getCompanyName(),
                    stock.getVolume(),
                    stock.getPreviousVolume());
        }

        log.info("******  The most recent 5 companies that have the greatest change percent in stock value  ****");
        for (StockInfoDto stock : companyRepository.findStocksChangePrice()
        ) {
            log.info("Company : {}, changePrice: {}, PrevPrice {}", stock.getCompanyName(),
                    stock.getChangePercent(),
                    stock.getLatestPrice() + (stock.getChangePercent() * stock.getLatestPrice()));
        }
    }
}
