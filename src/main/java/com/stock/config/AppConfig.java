package com.stock.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Value("${companiesURL}")
    private String companiesURI;

    @Value("${stockInfoURL}")
    private String stockInfoURI;

    public String getStockInfoURI() {
        return stockInfoURI;
    }

    public String getCompaniesURI() {
        return companiesURI;
    }
}
