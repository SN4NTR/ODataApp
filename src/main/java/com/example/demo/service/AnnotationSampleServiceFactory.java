package com.example.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.olingo.odata2.annotation.processor.api.AnnotationServiceFactory;
import org.apache.olingo.odata2.api.ODataService;
import org.apache.olingo.odata2.api.ODataServiceFactory;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataContext;

@Slf4j
public class AnnotationSampleServiceFactory extends ODataServiceFactory {

    private static final String MODEL_PACKAGE = "com.example.demo.model";

    private static ODataService annotationODataService;

    static {
        try {
            annotationODataService = AnnotationServiceFactory.createAnnotationService(MODEL_PACKAGE);
        } catch (ODataException e) {
            log.error("Exception during data source initialization generation", e);
        }
    }

    @Override
    public ODataService createService(ODataContext ctx) {
        return annotationODataService;
    }
}
