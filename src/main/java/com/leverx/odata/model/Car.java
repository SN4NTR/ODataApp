package com.leverx.odata.model;

import lombok.Data;
import org.apache.olingo.odata2.annotation.processor.core.util.AnnotationHelper;
import org.apache.olingo.odata2.api.annotation.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.annotation.edm.EdmEntityType;
import org.apache.olingo.odata2.api.annotation.edm.EdmKey;
import org.apache.olingo.odata2.api.annotation.edm.EdmNavigationProperty;
import org.apache.olingo.odata2.api.annotation.edm.EdmProperty;

@Data
@EdmEntityType(name = "Car")
@EdmEntitySet(name = "CarSet")
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
