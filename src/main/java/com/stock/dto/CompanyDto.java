package com.stock.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import java.util.Date;


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

    public CompanyDto() {
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public String getExchange() {
        return this.exchange;
    }

    public String getExchangeSuffix() {
        return this.exchangeSuffix;
    }

    public String getExchangeName() {
        return this.exchangeName;
    }

    public Date getDate() {
        return this.date;
    }

    public String getRegion() {
        return this.region;
    }

    public String getCurrency() {
        return this.currency;
    }

    public Boolean getIsEnabled() {
        return this.isEnabled;
    }

    public StockInfoDto getStockInfo() {
        return this.stockInfo;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("symbol")
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @JsonProperty("exchange")
    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    @JsonProperty("exchangeSuffix")
    public void setExchangeSuffix(String exchangeSuffix) {
        this.exchangeSuffix = exchangeSuffix;
    }

    @JsonProperty("exchangeName")
    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    @JsonProperty("date")
    public void setDate(Date date) {
        this.date = date;
    }

    @JsonProperty("region")
    public void setRegion(String region) {
        this.region = region;
    }

    @JsonProperty("currency")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @JsonProperty("isEnabled")
    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public void setStockInfo(StockInfoDto stockInfo) {
        this.stockInfo = stockInfo;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof CompanyDto)) return false;
        final CompanyDto other = (CompanyDto) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$symbol = this.getSymbol();
        final Object other$symbol = other.getSymbol();
        if (this$symbol == null ? other$symbol != null : !this$symbol.equals(other$symbol)) return false;
        final Object this$exchange = this.getExchange();
        final Object other$exchange = other.getExchange();
        if (this$exchange == null ? other$exchange != null : !this$exchange.equals(other$exchange)) return false;
        final Object this$exchangeSuffix = this.getExchangeSuffix();
        final Object other$exchangeSuffix = other.getExchangeSuffix();
        if (this$exchangeSuffix == null ? other$exchangeSuffix != null : !this$exchangeSuffix.equals(other$exchangeSuffix))
            return false;
        final Object this$exchangeName = this.getExchangeName();
        final Object other$exchangeName = other.getExchangeName();
        if (this$exchangeName == null ? other$exchangeName != null : !this$exchangeName.equals(other$exchangeName))
            return false;
        final Object this$date = this.getDate();
        final Object other$date = other.getDate();
        if (this$date == null ? other$date != null : !this$date.equals(other$date)) return false;
        final Object this$region = this.getRegion();
        final Object other$region = other.getRegion();
        if (this$region == null ? other$region != null : !this$region.equals(other$region)) return false;
        final Object this$currency = this.getCurrency();
        final Object other$currency = other.getCurrency();
        if (this$currency == null ? other$currency != null : !this$currency.equals(other$currency)) return false;
        final Object this$isEnabled = this.getIsEnabled();
        final Object other$isEnabled = other.getIsEnabled();
        if (this$isEnabled == null ? other$isEnabled != null : !this$isEnabled.equals(other$isEnabled)) return false;
        final Object this$stockInfo = this.getStockInfo();
        final Object other$stockInfo = other.getStockInfo();
        if (this$stockInfo == null ? other$stockInfo != null : !this$stockInfo.equals(other$stockInfo)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof CompanyDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $symbol = this.getSymbol();
        result = result * PRIME + ($symbol == null ? 43 : $symbol.hashCode());
        final Object $exchange = this.getExchange();
        result = result * PRIME + ($exchange == null ? 43 : $exchange.hashCode());
        final Object $exchangeSuffix = this.getExchangeSuffix();
        result = result * PRIME + ($exchangeSuffix == null ? 43 : $exchangeSuffix.hashCode());
        final Object $exchangeName = this.getExchangeName();
        result = result * PRIME + ($exchangeName == null ? 43 : $exchangeName.hashCode());
        final Object $date = this.getDate();
        result = result * PRIME + ($date == null ? 43 : $date.hashCode());
        final Object $region = this.getRegion();
        result = result * PRIME + ($region == null ? 43 : $region.hashCode());
        final Object $currency = this.getCurrency();
        result = result * PRIME + ($currency == null ? 43 : $currency.hashCode());
        final Object $isEnabled = this.getIsEnabled();
        result = result * PRIME + ($isEnabled == null ? 43 : $isEnabled.hashCode());
        final Object $stockInfo = this.getStockInfo();
        result = result * PRIME + ($stockInfo == null ? 43 : $stockInfo.hashCode());
        return result;
    }

    public String toString() {
        return "CompanyDto(id=" + this.getId() + ", name=" + this.getName() + ", symbol=" + this.getSymbol() + ", exchange=" + this.getExchange() + ", exchangeSuffix=" + this.getExchangeSuffix() + ", exchangeName=" + this.getExchangeName() + ", date=" + this.getDate() + ", region=" + this.getRegion() + ", currency=" + this.getCurrency() + ", isEnabled=" + this.getIsEnabled() + ", stockInfo=" + this.getStockInfo() + ")";
    }
}
