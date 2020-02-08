package com.leverx.odata.odata.datasource;

import com.leverx.odata.model.Company;
import com.leverx.odata.model.Employee;
import com.leverx.odata.repository.CompanyRepository;
import com.leverx.odata.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.apache.olingo.odata2.annotation.processor.core.datasource.DataSource;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmFunctionImport;
import org.apache.olingo.odata2.api.exception.ODataApplicationException;
import org.apache.olingo.odata2.api.exception.ODataNotFoundException;
import org.apache.olingo.odata2.api.exception.ODataNotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.leverx.odata.model.constant.EntityConstant.COMPANY_SET_NAME;
import static com.leverx.odata.model.constant.EntityConstant.EMPLOYEE_SET_NAME;
import static org.apache.olingo.odata2.api.exception.ODataNotFoundException.ENTITY;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AppDataSource implements DataSource {

    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;

    private static final String ID_KEY = "Id";

    @Override
    public List<?> readData(EdmEntitySet entitySet) throws ODataNotImplementedException, EdmException {
        String entitySetName = entitySet.getName();

        if (EMPLOYEE_SET_NAME.equals(entitySetName)) {
            return employeeRepository.findAll();
        } else if (COMPANY_SET_NAME.equals(entitySetName)) {
            return companyRepository.findAll();
        }
        throw new ODataNotImplementedException();
    }

    @Override
    public Object readData(EdmEntitySet entitySet, Map<String, Object> keys) throws ODataNotImplementedException, ODataNotFoundException, EdmException {
        String entitySetName = entitySet.getName();
        Integer id = (Integer) keys.get(ID_KEY);

        switch (entitySetName) {
            case EMPLOYEE_SET_NAME:
                Optional<Employee> employeeOpt = employeeRepository.findById(id);
                return employeeOpt.orElseThrow(() -> new ODataNotFoundException(ENTITY));
            case COMPANY_SET_NAME:
                Optional<Company> companyOpt = companyRepository.findById(id);
                return companyOpt.orElseThrow(() -> new ODataNotFoundException(ENTITY));
            default:
                throw new ODataNotImplementedException();
        }
    }

    @Override
    public Object readData(EdmFunctionImport function, Map<String, Object> parameters, Map<String, Object> keys) throws ODataNotImplementedException, ODataNotFoundException, EdmException, ODataApplicationException {
        throw new ODataNotImplementedException();
    }

    @Override
    public Object readRelatedData(EdmEntitySet sourceEntitySet, Object sourceData, EdmEntitySet targetEntitySet, Map<String, Object> targetKeys) throws ODataNotImplementedException, ODataNotFoundException, EdmException, ODataApplicationException {
        String targetEntityName = targetEntitySet.getName();

        switch (targetEntityName) {
            case EMPLOYEE_SET_NAME:
                Integer companyId = ((Company) sourceData).getId();
                Optional<Company> companyOpt = companyRepository.findById(companyId);
                Company company = companyOpt.orElseThrow(() -> new ODataNotFoundException(ENTITY));
                return company.getEmployees();
            case COMPANY_SET_NAME:
                Integer employeeId = ((Employee) sourceData).getId();
                Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);
                Employee employee = employeeOpt.orElseThrow(() -> new ODataNotFoundException(ENTITY));
                return employee.getCompany();
            default:
                throw new ODataNotImplementedException();
        }
    }

    @Override
    public BinaryData readBinaryData(EdmEntitySet entitySet, Object mediaLinkEntryData) throws ODataNotImplementedException, ODataNotFoundException, EdmException, ODataApplicationException {
        throw new ODataNotImplementedException();
    }

    @Override
    public Object newDataObject(EdmEntitySet entitySet) throws ODataNotImplementedException, EdmException, ODataApplicationException {
        String entitySetName = entitySet.getName();

        switch (entitySetName) {
            case EMPLOYEE_SET_NAME:
                return new Employee();
            case COMPANY_SET_NAME:
                return new Company();
            default:
                throw new ODataNotImplementedException();
        }
    }

    @Override
    public void writeBinaryData(EdmEntitySet entitySet, Object mediaLinkEntryData, BinaryData binaryData) throws ODataNotImplementedException, ODataNotFoundException, EdmException, ODataApplicationException {
        throw new ODataNotImplementedException();
    }

    @Override
    public void deleteData(EdmEntitySet entitySet, Map<String, Object> keys) throws ODataNotImplementedException, ODataNotFoundException, EdmException, ODataApplicationException {
        String entitySetName = entitySet.getName();
        Integer id = (Integer) keys.get(ID_KEY);

        switch (entitySetName) {
            case EMPLOYEE_SET_NAME:
                Optional<Employee> employeeOpt = employeeRepository.findById(id);
                Employee employee = employeeOpt.orElseThrow(() -> new ODataNotFoundException(ENTITY));
                employeeRepository.delete(employee);
                break;
            case COMPANY_SET_NAME:
                Optional<Company> companyOpt = companyRepository.findById(id);
                Company company = companyOpt.orElseThrow(() -> new ODataNotFoundException(ENTITY));
                companyRepository.delete(company);
                break;
            default:
                throw new ODataNotImplementedException();
        }
    }

    @Override
    public void createData(EdmEntitySet entitySet, Object data) throws ODataNotImplementedException, EdmException, ODataApplicationException {
        String entitySetName = entitySet.getName();

        switch (entitySetName) {
            case EMPLOYEE_SET_NAME:
                Employee employee = (Employee) data;
                employeeRepository.save(employee);
                break;
            case COMPANY_SET_NAME:
                Company company = (Company) data;
                companyRepository.save(company);
                break;
            default:
                throw new ODataNotImplementedException();
        }
    }

    @Override
    public void deleteRelation(EdmEntitySet sourceEntitySet, Object sourceData, EdmEntitySet targetEntitySet, Map<String, Object> targetKeys) throws ODataNotImplementedException, ODataNotFoundException, EdmException, ODataApplicationException {
        throw new ODataNotImplementedException();
    }

    @Override
    public void writeRelation(EdmEntitySet sourceEntitySet, Object sourceData, EdmEntitySet targetEntitySet, Map<String, Object> targetKeys) throws ODataNotImplementedException, ODataNotFoundException, EdmException, ODataApplicationException {
        throw new ODataNotImplementedException();
    }
}
