package com.leverx.odata.model.constant;

import lombok.NoArgsConstructor;
import org.apache.olingo.odata2.api.edm.FullQualifiedName;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class EntityConstant {

    public static final String EMPLOYEE_SET_NAME = "Employees";
    public static final String EMPLOYEE_TYPE_NAME = "Employee";
    public static final String COMPANY_SET_NAME = "Companies";
    public static final String COMPANY_TYPE_NAME = "Company";

    public static final String NAMESPACE = "com.leverx.odata";

    public static final FullQualifiedName EMPLOYEE_TYPE = new FullQualifiedName(NAMESPACE, EMPLOYEE_TYPE_NAME);
    public static final FullQualifiedName COMPANY_TYPE = new FullQualifiedName(NAMESPACE, COMPANY_TYPE_NAME);

    public static final String EMPLOYEE_ROLE = "Employee_Company";
    public static final String COMPANY_ROLE = "Company_Employee";

    public static final String ENTITY_CONTAINER = "EmployeesWithCompanyContainer";

    public static final String ASSOCIATION_EMPLOYEES_COMPANIES = "Employees_Companies";
    public static final String ASSOCIATION_COMPANIES_EMPLOYEES = "Companies_Employees";
}
