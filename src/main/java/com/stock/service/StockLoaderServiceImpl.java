package com.stock.service;

import com.stock.config.AppConfig;
import com.stock.dto.CompanyDto;
import com.stock.dto.StockInfoDto;
import com.stock.parser.CompanyParser;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class StockLoaderServiceImpl implements StockLoaderService {

    private final AppConfig appConfig;
    private final AsyncStockLoaderHelper asyncStockLoaderHelper;


    @SneakyThrows
    public List<StockInfoDto> stockInfoCollectorHandler(List<CompanyDto> companies) {
        List<CompletableFuture<StockInfoDto>> futureList = new ArrayList<>();

        for (CompanyDto company : companies) {
            futureList.add(asyncStockLoaderHelper.getStockInfo(company));
        }
        CompletableFuture.allOf(
                futureList.toArray(new CompletableFuture[futureList.size()]))
                .exceptionally(ex -> {
                    log.error(ex.getMessage());
                    return null;
                })
                .join();

        Map<Boolean, List<CompletableFuture<StockInfoDto>>> collectedStockInfoMap =
                futureList.stream().collect(
                        Collectors.partitioningBy(
                                CompletableFuture::isCompletedExceptionally));


        log.info("StockInfo collection was loaded.");
        return collectedStockInfoMap.get(false).stream().map(
                s -> s.join()
        ).collect(Collectors.toList());

    }

    @Override
    public List<CompanyDto> loadCompanies() {
        log.info("Starting load stock data...");
        RestTemplate restTemplate = new RestTemplate();

        String jsonCompanies = restTemplate.getForObject(appConfig.getCompaniesURI(), String.class);
        log.info("Companies loaded: "+ jsonCompanies.length());



        List<CompanyDto> companyDtoList = CompanyParser.parseCompanies(jsonCompanies);
        log.info("Companies were parsed: "+ companyDtoList.size());

        // return enabled companies
        return companyDtoList.stream().filter(s -> s.getIsEnabled()).collect(Collectors.toList());
    }
}
