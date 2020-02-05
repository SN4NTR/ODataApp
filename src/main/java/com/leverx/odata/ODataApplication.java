package com.leverx.odata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class ODataApplication {

    public static void main(String[] args) {
        SpringApplication.run(ODataApplication.class, args);
    }
}
