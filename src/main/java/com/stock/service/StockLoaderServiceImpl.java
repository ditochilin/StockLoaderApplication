package com.stock.service;

import com.stock.config.AppConfig;
import com.stock.model.Company;
import com.stock.model.StockInfo;
import com.stock.parser.CompanyParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class StockLoaderServiceImpl implements StockLoaderService {

    private Logger logger = LoggerFactory.getLogger(StockLoaderServiceImpl.class);

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private AsyncStockLoaderHelper asyncStockLoaderHelper;

    private List<Company> companyList;


    @Override
    public void stockInfoCollectorHandler(List<Company> companies) throws InterruptedException {
        List<CompletableFuture<StockInfo>> futureList = new ArrayList<>();

        long start = System.currentTimeMillis();

        for (Company company : companies) {
            futureList.add(asyncStockLoaderHelper.getStockInfo(company));
        }
        CompletableFuture.allOf(
                futureList.toArray(new CompletableFuture[futureList.size()]))
                .exceptionally(ex -> {
                    logger.error(ex.getMessage());
                    return null;
                })
                .join();

        Map<Boolean, List<CompletableFuture<StockInfo>>> collectedStockInfoMap =
                futureList.stream().collect(
                        Collectors.partitioningBy(
                                CompletableFuture::isCompletedExceptionally));


        // TODO rudimentary get ???
        List<StockInfo> stockInfoList = collectedStockInfoMap.get(false).stream().map(
                el -> {
                    try {
                        return el.get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
        ).collect(Collectors.toList());

        stockInfoList.stream().forEach(stockInfo -> logger.info("StockInfo: "+ stockInfo.getCompanyName()));

        logger.info("Elapsed time: " + (System.currentTimeMillis() - start));

    }

    @Override
    public List<Company> loadCompanies() {
        logger.info("Starting load stock data...");

        RestTemplate restTemplate = new RestTemplate();

        String jsonCompanies = restTemplate.getForObject(appConfig.getCompaniesURI(), String.class);
        logger.info("Companies loaded: "+ jsonCompanies.length());

        companyList = CompanyParser.parseCompanies(jsonCompanies);
        logger.info("Companies were parsed: "+companyList.size());

        return companyList;
    }



}
