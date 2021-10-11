package com.stock.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import java.util.Date;

@Data
@Document(collection = "company")
public class CompanyDto {

    @Id
    private String id;

    @JsonProperty("name")
    private String name;
    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("exchange")
    private String exchange;
    @JsonProperty("exchangeSuffix")
    private String exchangeSuffix;
    @JsonProperty("exchangeName")
    private String exchangeName;
    @JsonProperty("date")
    private Date date;
    @JsonProperty("region")
    private String region;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("isEnabled")
    private Boolean isEnabled;

    @OneToOne(mappedBy = "symbol", cascade = CascadeType.ALL)
    private StockInfoDto stockInfo;
}
