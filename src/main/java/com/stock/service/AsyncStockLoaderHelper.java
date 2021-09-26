package com.stock.service;

import com.stock.config.AppConfig;
import com.stock.model.Company;
import com.stock.model.StockInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Component
public class AsyncStockLoaderHelper {

    private final AppConfig appConfig;
    private Logger logger = LoggerFactory.getLogger(AsyncStockLoaderHelper.class);
    private final RestTemplate restTemplate;

    public AsyncStockLoaderHelper(RestTemplateBuilder restTemplateBuilder, AppConfig appConfig) {
        this.restTemplate = restTemplateBuilder.build();
        this.appConfig = appConfig;
    }

    @Async("threadPoolCustomTaskExecutor")
    public CompletableFuture<StockInfo> getStockInfo(Company company) throws InterruptedException {
        logger.info("Looking up at " + Thread.currentThread().getName() + " : "+ company.getSymbol());
        String url = String.format(appConfig.getStockInfoURI(),
                company.getSymbol());
        StockInfo result = restTemplate.getForObject(url, StockInfo.class);
        return CompletableFuture.completedFuture(result);
    }

}
