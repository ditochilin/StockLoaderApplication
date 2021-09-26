package com.stock.service;

import com.stock.model.Company;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface StockLoaderService {
    List<Company> loadCompanies();

    void stockInfoCollectorHandler(List<Company> companies) throws InterruptedException, ExecutionException;
}
