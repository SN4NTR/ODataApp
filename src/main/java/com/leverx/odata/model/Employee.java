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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static com.leverx.odata.model.constant.EntityConstant.EMPLOYEE_SET_NAME;
import static com.leverx.odata.model.constant.EntityConstant.EMPLOYEE_TYPE_NAME;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.AUTO;

@Data
@Entity
@EdmEntityType(name = EMPLOYEE_TYPE_NAME)
@EdmEntitySet(name = EMPLOYEE_SET_NAME)
public class Employee {

    @Id
    @EdmKey
    @EdmProperty
    @GeneratedValue(strategy = AUTO)
    private int id;

    @EdmProperty
    private String name;

    @EdmNavigationProperty
    @ManyToOne(fetch = EAGER)
    private Company company;
}
