package com.leverx.odata.odata.processor;

import com.leverx.odata.model.Company;
import com.leverx.odata.model.Employee;
import com.leverx.odata.repository.CompanyRepository;
import com.leverx.odata.repository.EmployeeRepository;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmLiteralKind;
import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.edm.EdmSimpleType;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.ep.EntityProviderWriteProperties;
import org.apache.olingo.odata2.api.ep.EntityProviderWriteProperties.ODataEntityProviderPropertiesBuilder;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.exception.ODataNotFoundException;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.api.processor.ODataSingleProcessor;
import org.apache.olingo.odata2.api.uri.KeyPredicate;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntityUriInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.leverx.odata.model.constant.EntityConstant.COMPANY_SET_NAME;
import static com.leverx.odata.model.constant.EntityConstant.EMPLOYEE_SET_NAME;
import static org.apache.olingo.odata2.api.ep.EntityProviderWriteProperties.serviceRoot;
import static org.apache.olingo.odata2.api.exception.ODataNotFoundException.ENTITY;

@Component
public class EntityODataProcessor extends ODataSingleProcessor {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public ODataResponse readEntity(GetEntityUriInfo uriInfo, String contentType) throws ODataException {
        EdmEntitySet edmEntitySet = uriInfo.getStartEntitySet();
        String entitySetName = edmEntitySet.getName();
        KeyPredicate keyPredicate = uriInfo.getKeyPredicates().get(0);
        int id = getKeyValue(keyPredicate);

        if (uriInfo.getNavigationSegments().size() == 0) {
            if (EMPLOYEE_SET_NAME.equals(entitySetName)) {
                Map<String, Object> employees = getEmployeeMap(id);
                return getODataResponse(contentType, edmEntitySet, employees);
            } else if (COMPANY_SET_NAME.equals(entitySetName)) {
                Map<String, Object> companies = getCompanyMap(id);
                return getODataResponse(contentType, edmEntitySet, companies);
            }
            throw new ODataNotFoundException(ENTITY);
        } else if (uriInfo.getNavigationSegments().size() == 1) {
            if (EMPLOYEE_SET_NAME.equals(edmEntitySet.getName())) {
                Optional<Employee> employeeOpt = employeeRepository.findById(id);
                Employee employee = employeeOpt.orElseThrow(IllegalArgumentException::new);
                int companyId = employee.getCompany().getId();
                Map<String, Object> company = getCompanyMap(companyId);
                return getODataResponse(contentType, edmEntitySet, company);
            } else if (COMPANY_SET_NAME.equals(edmEntitySet.getName())) {
                Map<String, Object> employee = getEmployeeMap(id);
                return getODataResponse(contentType, edmEntitySet, employee);
            }
            throw new ODataNotFoundException(ENTITY);
        }
        throw new ODataNotFoundException(ENTITY);
    }

    @Override
    public ODataResponse readEntitySet(GetEntitySetUriInfo uriInfo, String contentType) throws ODataException {
        EdmEntitySet edmEntitySet = uriInfo.getStartEntitySet();

        if (uriInfo.getNavigationSegments().size() == 0) {
            if (EMPLOYEE_SET_NAME.equals(edmEntitySet.getName())) {
                List<Map<String, Object>> employees = getEmployeesMap();
                URI uri = getContext().getPathInfo().getServiceRoot();
                EntityProviderWriteProperties properties = serviceRoot(uri).build();
                return EntityProvider.writeFeed(contentType, edmEntitySet, employees, properties);
            } else if (COMPANY_SET_NAME.equals(edmEntitySet.getName())) {
                List<Map<String, Object>> companies = getCompaniesMap();
                URI uri = getContext().getPathInfo().getServiceRoot();
                EntityProviderWriteProperties properties = serviceRoot(uri).build();
                return EntityProvider.writeFeed(contentType, edmEntitySet, companies, properties);
            }
            throw new ODataNotFoundException(ENTITY);
        } else if (uriInfo.getNavigationSegments().size() == 1) {
            if (COMPANY_SET_NAME.equals(edmEntitySet.getName())) {
                int id = getKeyValue(uriInfo.getKeyPredicates().get(0));
                List<Map<String, Object>> employees = getAllEmployeesFromCompany(id);

                URI uri = getContext().getPathInfo().getServiceRoot();
                EntityProviderWriteProperties properties = serviceRoot(uri).build();
                return EntityProvider.writeFeed(contentType, edmEntitySet, employees, properties);
            }
            throw new ODataNotFoundException(ENTITY);
        }
        throw new ODataNotFoundException(ENTITY);
    }

    private List<Map<String, Object>> getAllEmployeesFromCompany(int id) {
        Optional<Company> companyOpt = companyRepository.findById(id);
        Company company = companyOpt.orElseThrow(IllegalArgumentException::new);
        List<Employee> employees = company.getEmployees();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Employee employee : employees) {
            int employeeId = employee.getId();
            Map<String, Object> employeeMap = getEmployeeMap(employeeId);
            result.add(employeeMap);
        }
        return result;
    }

    private ODataResponse getODataResponse(String contentType, EdmEntitySet edmEntitySet, Map<String, Object> data) throws ODataException {
        URI serviceRoot = getContext().getPathInfo().getServiceRoot();
        ODataEntityProviderPropertiesBuilder propertiesBuilder = serviceRoot(serviceRoot);
        return EntityProvider.writeEntry(contentType, edmEntitySet, data, propertiesBuilder.build());
    }

    private Map<String, Object> getEmployeeMap(int id) {
        Optional<Employee> employeeOpt = employeeRepository.findById(id);
        Employee employee = employeeOpt.orElseThrow(IllegalArgumentException::new);

        Map<String, Object> employeeMap = new HashMap<>();
        employeeMap.put("Id", employee.getId());
        employeeMap.put("Name", employee.getName());
        return employeeMap;
    }

    private List<Map<String, Object>> getEmployeesMap() {
        List<Map<String, Object>> employeeSet = new ArrayList<>();

        List<Employee> employees = employeeRepository.findAll();
        for (Employee employee : employees) {
            int id = employee.getId();
            Map<String, Object> employeeMap = getEmployeeMap(id);
            employeeSet.add(employeeMap);
        }
        return employeeSet;
    }

    private Map<String, Object> getCompanyMap(int id) {
        Optional<Company> companyOpt = companyRepository.findById(id);
        Company company = companyOpt.orElseThrow(IllegalArgumentException::new);

        Map<String, Object> companyMap = new HashMap<>();
        companyMap.put("Id", company.getId());
        companyMap.put("Name", company.getName());
        companyMap.put("Founded", company.getFounded());
        return companyMap;
    }

    private List<Map<String, Object>> getCompaniesMap() {
        List<Map<String, Object>> companiesSet = new ArrayList<>();

        List<Company> companies = companyRepository.findAll();
        for (Company company : companies) {
            int id = company.getId();
            Map<String, Object> employeeMap = getEmployeeMap(id);
            companiesSet.add(employeeMap);
        }
        return companiesSet;
    }

    private int getKeyValue(KeyPredicate key) throws ODataException {
        EdmProperty property = key.getProperty();
        EdmSimpleType type = (EdmSimpleType) property.getType();
        return type.valueOfString(key.getLiteral(), EdmLiteralKind.DEFAULT, property.getFacets(), Integer.class);
    }
}
