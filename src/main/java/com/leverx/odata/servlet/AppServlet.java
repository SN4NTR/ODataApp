package com.leverx.odata.servlet;

import org.apache.olingo.odata2.core.servlet.ODataServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "appServlet", urlPatterns = "/odata/*", loadOnStartup = 1,
        initParams = {
                @WebInitParam(
                        name = "org.apache.olingo.odata2.service.factory",
                        value = "com.leverx.odata.odata.service.AnnotationSampleServiceFactory"
                )
        })
public class AppServlet extends ODataServlet {
}
