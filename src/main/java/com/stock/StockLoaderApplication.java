package com.stock;

import com.stock.service.StockLoaderService;
import com.stock.service.StockLoaderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

@SpringBootApplication
public class StockLoaderApplication {

    private Logger logger = LoggerFactory.getLogger(StockLoaderServiceImpl.class);

    @Autowired
    private StockLoaderService loaderService;

    public static void main(String[] args) {
        // close the application context to shut down the custom ExecutorService
        SpringApplication.run(StockLoaderApplication.class).close();
    }



    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() throws InterruptedException, ExecutionException {
        logger.info("Application StockLoader has been started...");

        loaderService.stockInfoCollectorHandler(loaderService.loadCompanies());
    }
}
