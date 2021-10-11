package com.stock.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

@Configuration
@PropertySource("classpath:application.properties")
@EnableScheduling
public class AppConfig {

    @Value("${iexapis.client.url}")
    private String clientURL;

    @Value("${companiesURI}")
    private String companiesURI;

    @Value("${stockInfoURI}")
    private String stockInfoURI;

    @Value("${iexapis.client.token}")
    private String token;

    public String getStockInfoURI() {
        return clientURL + stockInfoURI + "?token=" + token;
    }

    public String getCompaniesURI() {
        return clientURL + companiesURI + "?token=" + token;
    }

    //=====================  BEANS ===============================

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }


}
