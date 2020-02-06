package com.leverx.odata.model.converter;

import com.leverx.odata.model.Employee;
import lombok.NoArgsConstructor;

import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class EmployeeConverter {

    public static Employee convertFromProperties(Map<String, Object> properties) {
        Employee employee = new Employee();
        String name = (String) properties.get("Name");
        employee.setName(name);
        return employee;
    }
}
