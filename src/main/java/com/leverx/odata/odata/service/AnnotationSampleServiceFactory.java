package com.leverx.odata.odata.service;

import com.leverx.odata.context.AppContext;
import com.leverx.odata.odata.processor.EntityODataProcessor;
import org.apache.olingo.odata2.annotation.processor.core.edm.AnnotationEdmProvider;
import org.apache.olingo.odata2.api.ODataService;
import org.apache.olingo.odata2.api.ODataServiceFactory;
import org.apache.olingo.odata2.api.edm.provider.EdmProvider;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataContext;
import org.apache.olingo.odata2.api.processor.ODataSingleProcessor;
import org.springframework.context.ApplicationContext;

public class AnnotationSampleServiceFactory extends ODataServiceFactory {

    private static final String MODEL_PACKAGE = "com.leverx.odata.model";
    private static final String PROCESSOR_BEAN_NAME = "entityODataProcessor";

    @Override
    public ODataService createService(ODataContext ctx) throws ODataException {
        EdmProvider edmProvider = new AnnotationEdmProvider(MODEL_PACKAGE);
        ApplicationContext applicationContext = AppContext.getApplicationContext();
        ODataSingleProcessor oDataSingleProcessor = (EntityODataProcessor) applicationContext.getBean(PROCESSOR_BEAN_NAME);
        return createODataSingleProcessorService(edmProvider, oDataSingleProcessor);
    }
}
