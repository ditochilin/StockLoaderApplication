package com.stock.parser;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.stock.dto.CompanyDto;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CompanyParser {

    private final static Gson gson = new Gson();

    public static List<CompanyDto> parseCompanies(String jsonCompanies) throws JsonSyntaxException {
        return Arrays.stream(gson.fromJson(jsonCompanies, CompanyDto[].class)).collect(Collectors.toList());
    }

}
