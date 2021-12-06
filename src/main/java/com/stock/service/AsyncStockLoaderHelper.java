package com.stock.service;

import com.stock.config.AppConfig;
import com.stock.dto.CompanyDto;
import com.stock.dto.StockInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
@RequiredArgsConstructor
public class AsyncStockLoaderHelper {

    private final AppConfig appConfig;
    private final RestTemplate restTemplate;

    @Async("threadPoolCustomTaskExecutor")
    public CompletableFuture<StockInfoDto> getStockInfo(CompanyDto companyDto) {
        log.info("Looking up at {} : {} - {}", Thread.currentThread().getName(), companyDto.getSymbol(), companyDto.getName());
        String url = String.format(appConfig.getStockInfoURI(),
                companyDto.getSymbol());
        StockInfoDto stockInfoDto = restTemplate.getForObject(url, StockInfoDto.class);

        companyDto.setStockInfo(stockInfoDto);
        return CompletableFuture.completedFuture(stockInfoDto);
    }
}
