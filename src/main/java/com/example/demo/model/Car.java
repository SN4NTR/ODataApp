package com.example.demo.model;

import lombok.Data;
import org.apache.olingo.odata2.api.annotation.edm.*;

@Data
@EdmEntityType
@EdmEntitySet
public class Car {

    @EdmKey
    @EdmProperty
    private int id;

    @EdmProperty
    private String model;

    @EdmProperty
    private Double price;

    @EdmProperty
    private Integer productionYear;

    @EdmNavigationProperty
    private Manufacturer manufacturer;
}
