package com.leverx.odata.model.constant;

import lombok.NoArgsConstructor;
import org.apache.olingo.odata2.api.edm.FullQualifiedName;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class EntityConstant {

    public static final String CAR_SET_NAME = "Cars";
    public static final String CAR_TYPE_NAME = "Car";
    public static final String MANUFACTURER_SET_NAME = "Manufacturers";
    public static final String MANUFACTURER_TYPE_NAME = "Manufacturer";

    public static final String NAMESPACE = "com.leverx.odata";

    public static final FullQualifiedName CAR_TYPE = new FullQualifiedName(NAMESPACE, CAR_TYPE_NAME);
    public static final FullQualifiedName MANUFACTURER_TYPE = new FullQualifiedName(NAMESPACE, MANUFACTURER_TYPE_NAME);

    public static final String CAR_ROLE = "Car_Manufacturer";
    public static final String MANUFACTURER_ROLE = "Manufacturer_Car";

    public static final String ENTITY_CONTAINER = "CarsAndManufacturersContainer";

    public static final String ASSOCIATION_CARS_MANUFACTURERS = "Cars_Manufacturers";
    public static final String ASSOCIATION_MANUFACTURERS_CARS = "Manufacturers_Cars";
}
