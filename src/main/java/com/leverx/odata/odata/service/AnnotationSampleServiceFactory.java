package com.leverx.odata.odata.service;

import com.leverx.odata.context.AppContext;
import com.leverx.odata.odata.datasource.AppDataSource;
import org.apache.olingo.odata2.annotation.processor.core.ListsProcessor;
import org.apache.olingo.odata2.annotation.processor.core.datasource.AnnotationValueAccess;
import org.apache.olingo.odata2.annotation.processor.core.datasource.DataSource;
import org.apache.olingo.odata2.annotation.processor.core.datasource.ValueAccess;
import org.apache.olingo.odata2.annotation.processor.core.edm.AnnotationEdmProvider;
import org.apache.olingo.odata2.api.ODataService;
import org.apache.olingo.odata2.api.ODataServiceFactory;
import org.apache.olingo.odata2.api.edm.provider.EdmProvider;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataContext;
import org.springframework.context.ApplicationContext;

public class AnnotationSampleServiceFactory extends ODataServiceFactory {

    private static final String MODEL_PACKAGE = "com.leverx.odata.model";
    private static final String DATA_SOURCE_BEAN_NAME = "appDataSource";

    @Override
    public ODataService createService(ODataContext ctx) throws ODataException {
        EdmProvider edmProvider = new AnnotationEdmProvider(MODEL_PACKAGE);
        ValueAccess valueAccess = new AnnotationValueAccess();
        ApplicationContext applicationContext = AppContext.getApplicationContext();
        DataSource dataSource = (AppDataSource) applicationContext.getBean(DATA_SOURCE_BEAN_NAME);
        ListsProcessor listsProcessor = new ListsProcessor(dataSource, valueAccess);
        return createODataSingleProcessorService(edmProvider, listsProcessor);
    }
}
