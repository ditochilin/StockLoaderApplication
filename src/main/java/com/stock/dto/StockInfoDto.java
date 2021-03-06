package com.stock.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Data
@Setter
public class StockInfoDto {
    @Id
    private String id;
    private Long avgTotalVolume;
    private String calculationPrice;
    private Double change;
    @Setter(AccessLevel.NONE) private Double changePercent;
    private Double close;
    private String closeSource;
    private String closeTime;
    private String companyName;
    private String currency;
    private Double delayedPrice;
    private String delayedPriceTime;
    private Double extendedChange;
    private Double extendedChangePercent;
    private Double extendedPrice;
    private String extendedPriceTime;
    private Double high;
    private String highSource;
    private String highTime;
    private Double iexAskPrice;
    private Double iexAskSize;
    private Double iexBidPrice;
    private Double iexBidSize;
    private Double iexClose;
    private String iexCloseTime;
    private String iexLastUpdated;
    private Double iexMarketPercent;
    private Double iexOpen;
    private String iexOpenTime;
    private Double iexRealtimePrice;
    private Double iexRealtimeSize;
    private Double iexVolume;
    private String lastTradeTime;
    private Double latestPrice;
    private String latestSource;
    private String latestTime;
    private String latestUpdate;
    private String latestVolume;
    private Double low;
    private String lowSource;
    private String lowTime;
    private Double marketCap;
    private String oddLotDelayedPrice;
    private String oddLotDelayedPriceTime;
    private Double open;
    private String openTime;
    private String openSource;
    private Double peRatio;
    private Double previousClose;
    private Integer previousVolume;
    private String primaryExchange;
    private String symbol;
    private Integer volume;
    private Double week52High;
    private Double week52Low;
    private Double ytdChange;
    private Boolean isUSMarketOpen;

    public Double getChangePercent() {
        return this.changePercent == null ? 0 : this.changePercent;
    }
}
