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
import java.util.List;

import static com.leverx.odata.model.constant.EntityConstant.COMPANY_SET_NAME;
import static com.leverx.odata.model.constant.EntityConstant.COMPANY_TYPE_NAME;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@EdmEntityType(name = COMPANY_TYPE_NAME)
@EdmEntitySet(name = COMPANY_SET_NAME)
public class Company {

    @Id
    @EdmKey
    @EdmProperty
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    @EdmProperty
    private String name;

    @EdmProperty
    private Integer founded;

    @EdmNavigationProperty
    @OneToMany(fetch = EAGER, cascade = REMOVE, mappedBy = "company")
    private List<Employee> employees;
}
