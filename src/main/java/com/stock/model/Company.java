package com.stock.model;

import lombok.Data;
import java.util.Date;

@Data
public class Company {
    //@Id
    private String name;
    private String symbol;
    private String exchange;
    private String exchangeSuffix;
    private String exchangeName;
    private Date date;
    private String type;
    private String iexId;
    private String region;
    private String currency;
    private Boolean isEnabled;
    private String figi;
    private String cik;
    private String lei;

}
