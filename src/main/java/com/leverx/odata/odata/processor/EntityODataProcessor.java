package com.leverx.odata.odata.processor;

import com.leverx.odata.model.Car;
import com.leverx.odata.model.Manufacturer;
import com.leverx.odata.repository.CarRepository;
import com.leverx.odata.repository.ManufacturerRepository;
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
import org.apache.olingo.odata2.api.uri.info.GetEntityUriInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.leverx.odata.model.constant.EntityConstant.CAR_SET_NAME;
import static com.leverx.odata.model.constant.EntityConstant.MANUFACTURER_SET_NAME;
import static org.apache.olingo.odata2.api.exception.ODataNotFoundException.ENTITY;

@Component
public class EntityODataProcessor extends ODataSingleProcessor {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Override
    public ODataResponse readEntity(GetEntityUriInfo uriInfo, String contentType) throws ODataException {
        EdmEntitySet edmEntitySet = uriInfo.getStartEntitySet();
        String entitySetName = edmEntitySet.getName();
        KeyPredicate keyPredicate = uriInfo.getKeyPredicates().get(0);
        int id = getKeyValue(keyPredicate);

        if (uriInfo.getNavigationSegments().size() == 0) {
            if (CAR_SET_NAME.equals(entitySetName)) {
                Map<String, Object> cars = getCarMap(id);
                return getODataResponse(contentType, edmEntitySet, cars);
            } else if (MANUFACTURER_SET_NAME.equals(entitySetName)) {
                Map<String, Object> manufacturers = getManufacturerMap(id);
                return getODataResponse(contentType, edmEntitySet, manufacturers);
            }
            throw new ODataNotFoundException(ENTITY);
        } else if (uriInfo.getNavigationSegments().size() == 1) {
            if (CAR_SET_NAME.equals(edmEntitySet.getName())) {
                Map<String, Object> cars = getCarMap(id);
                return getODataResponse(contentType, edmEntitySet, cars);
            }
            throw new ODataNotFoundException(ENTITY);
        }
        throw new ODataNotFoundException(ENTITY);
    }

    private ODataResponse getODataResponse(String contentType, EdmEntitySet edmEntitySet, Map<String, Object> data) throws ODataException {
        URI serviceRoot = getContext().getPathInfo().getServiceRoot();
        ODataEntityProviderPropertiesBuilder propertiesBuilder = EntityProviderWriteProperties.serviceRoot(serviceRoot);
        return EntityProvider.writeEntry(contentType, edmEntitySet, data, propertiesBuilder.build());
    }

    private Map<String, Object> getCarMap(int id) {
        Optional<Car> carOpt = carRepository.findById(id);
        Car car = carOpt.orElseThrow(IllegalArgumentException::new);

        Map<String, Object> carMap = new HashMap<>();
        carMap.put("Id", car.getId());
        carMap.put("Model", car.getModel());
        carMap.put("Price", car.getPrice());
        carMap.put("ProductionYear", car.getProductionYear());
        carMap.put("Manufacturer", car.getManufacturer());

        return carMap;
    }

    private Map<String, Object> getManufacturerMap(int id) {
        Optional<Manufacturer> manufacturerOpt = manufacturerRepository.findById(id);
        Manufacturer manufacturer = manufacturerOpt.orElseThrow(IllegalArgumentException::new);

        Map<String, Object> manufacturerMap = new HashMap<>();
        manufacturerMap.put("Id", manufacturer.getId());
        manufacturerMap.put("Name", manufacturer.getName());
        manufacturerMap.put("Founded", manufacturer.getFounded());
        manufacturerMap.put("Cars", manufacturer.getCars());

        return manufacturerMap;
    }

    private int getKeyValue(KeyPredicate key) throws ODataException {
        EdmProperty property = key.getProperty();
        EdmSimpleType type = (EdmSimpleType) property.getType();
        return type.valueOfString(key.getLiteral(), EdmLiteralKind.DEFAULT, property.getFacets(), Integer.class);
    }
}
