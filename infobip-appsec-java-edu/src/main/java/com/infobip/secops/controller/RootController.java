package com.infobip.secops.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class RootController {

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @RequestMapping("/")
    public String root(Model model) {
        List<Endpoint> endpoints = Arrays.asList(
            // DirController
            new Endpoint("/dir", "Command injection - vulnerability with dot bypass"),
            new Endpoint("/dir/safe", "Command injection - example of proper Safe API usage"),
            new Endpoint("/dir/notsafe", "Command injection - example of wrong Safe API usage"),
            // PersonController
            new Endpoint("/persons", "SQL injection - vulnerability with frontend bypass"),
            new Endpoint("/persons/denylist", "SQL injection - example of weak sanitization"),
            new Endpoint("/persons/safe", "SQL injection - example of proper query usage"),
            // GreeterController
            new Endpoint("/greet", "Reflected XSS - POST variant"),
            new Endpoint("/greet/get", "Reflected XSS - GET variant"),
            // FileController
            new Endpoint("/file", "File upload"),
            // SourceController
            new Endpoint("/source", "Server side request forgery - unrestricted"),
            // RevengeSourceController
            new Endpoint("/revenge/source", "Bypassing fixed hosts"),
            //  ExcelController
            new Endpoint("/excel", "XML External Entities injection")
        );
        model.addAttribute("endpoints", endpoints);
        return "home";
    }

    private class Endpoint {
        private String path;
        private String description;

        public Endpoint(String path, String description) {
            this.path = path;
            this.description = description;
        }

        public String getPath() {
            return path;
        }

        public String getDescription() {
            return description;
        }
    }
}

