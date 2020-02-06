package com.leverx.odata.model.converter;

import com.leverx.odata.model.Company;
import lombok.NoArgsConstructor;

import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CompanyConverter {

    public static Company convertFromProperties(Map<String, Object> properties) {
        Company company = new Company();
        String name = (String) properties.get("Name");
        Integer founded = (Integer) properties.get("Founded");
        company.setName(name);
        company.setFounded(founded);
        return company;
    }
}
