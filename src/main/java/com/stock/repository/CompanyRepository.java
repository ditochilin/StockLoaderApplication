package com.stock.repository;

import com.stock.dto.CompanyDto;
import com.stock.dto.StockInfoDto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public interface CompanyRepository extends MongoRepository<CompanyDto, String> {

    default List<StockInfoDto> findStocksByVolume(){
        List<CompanyDto> companies = findByStockInfoWhenExists();

        Comparator<StockInfoDto> stocksComparator =  new Comparator<StockInfoDto>() {
            @Override
            public int compare(StockInfoDto stock1, StockInfoDto stock2) {
                return compareByVolume(stock1, stock2);
            }
        };

        return companies.stream()
                .map(CompanyDto::getStockInfo)
                .sorted(stocksComparator.thenComparing(StockInfoDto::getCompanyName))
                .limit(5)
                .collect(Collectors.toList());
    }

    default List<StockInfoDto> findStocksChangePrice() {
        List<CompanyDto> companies = findByStockInfoWhenExists();

        return companies.stream()
                .map(CompanyDto::getStockInfo)
                .sorted(Comparator.comparing(StockInfoDto::getChangePercent).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }
    // use previousVolume or volume DESC
    default int compareByVolume(StockInfoDto stockInfo1, StockInfoDto stockInfo2){
        return getActualVolume(stockInfo2) - getActualVolume(stockInfo1);
    }

    // TODO optimize ternar ???
    default Integer getActualVolume(StockInfoDto stock){
        return stock.getVolume() != null ? stock.getVolume()
                                         : stock.getPreviousVolume() != null ? stock.getPreviousVolume() : 0;
    }

    @Query(value="{ stockInfo : { $exists : true } }")
    List<CompanyDto> findByStockInfoWhenExists();

}
