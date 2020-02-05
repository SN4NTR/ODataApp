package com.leverx.odata.model;

import lombok.Data;
import org.apache.olingo.odata2.api.annotation.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.annotation.edm.EdmEntityType;
import org.apache.olingo.odata2.api.annotation.edm.EdmKey;
import org.apache.olingo.odata2.api.annotation.edm.EdmNavigationProperty;
import org.apache.olingo.odata2.api.annotation.edm.EdmProperty;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Data
@EdmEntityType(name = "Manufacturer")
@EdmEntitySet(name = "ManufacturerSet")
public class Manufacturer {

    @EdmKey
    @EdmProperty
    private int id;

    @EdmProperty
    private String name;

    @EdmProperty
    private Calendar founded;

    @EdmNavigationProperty
    private List<Car> cars = new ArrayList<>();
}
