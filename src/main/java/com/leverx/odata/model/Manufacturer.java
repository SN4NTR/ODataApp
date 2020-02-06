package com.leverx.odata.model;

import lombok.Data;
import org.apache.olingo.odata2.api.annotation.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.annotation.edm.EdmEntityType;
import org.apache.olingo.odata2.api.annotation.edm.EdmKey;
import org.apache.olingo.odata2.api.annotation.edm.EdmNavigationProperty;
import org.apache.olingo.odata2.api.annotation.edm.EdmProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

import static com.leverx.odata.model.constant.EntityConstant.MANUFACTURER_SET_NAME;
import static com.leverx.odata.model.constant.EntityConstant.MANUFACTURER_TYPE_NAME;
import static javax.persistence.GenerationType.AUTO;

@Data
@Entity
@EdmEntityType(name = MANUFACTURER_TYPE_NAME)
@EdmEntitySet(name = MANUFACTURER_SET_NAME)
public class Manufacturer {

    @Id
    @EdmKey
    @EdmProperty
    @GeneratedValue(strategy = AUTO)
    private int id;

    @EdmProperty
    private String name;

    @EdmProperty
    private Integer founded;

    @OneToMany
    @EdmNavigationProperty
    private List<Car> cars = new ArrayList<>();
}
