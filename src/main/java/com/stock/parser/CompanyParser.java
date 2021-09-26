package com.stock.parser;

import com.google.gson.Gson;
import com.stock.model.Company;
import java.util.Arrays;
import java.util.List;

public class CompanyParser {

    public static List<Company> parseCompanies(String jsonCompanies){
        Gson gson = new Gson();
        Company[] companies = gson.fromJson(jsonCompanies, Company[].class);
        return Arrays.asList(companies);
    }

}
