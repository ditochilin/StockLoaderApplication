package com.stock.service;

import com.stock.dto.CompanyDto;
import com.stock.dto.StockInfoDto;

import java.util.List;

public interface StockLoaderService {
    List<CompanyDto> loadCompanies();
    List<StockInfoDto> stockInfoCollectorHandler(List<CompanyDto> companies);
}
