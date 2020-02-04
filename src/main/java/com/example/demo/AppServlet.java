package com.example.demo;

import org.apache.olingo.odata2.core.servlet.ODataServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "appServlet",
        initParams = {@WebInitParam(name = "org.apache.olingo.odata2.service.factory",
        value = "com.example.demo.service.AnnotationSampleServiceFactory")}, urlPatterns = "/*")
public class AppServlet extends ODataServlet {
}
