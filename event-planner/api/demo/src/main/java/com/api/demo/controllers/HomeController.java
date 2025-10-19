package com.api.demo.controllers;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@RestController
public class HomeController {
     private  RequestMappingHandlerMapping handlerMapping;

    @Autowired
    public HomeController(RequestMappingHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    @GetMapping("/")
    public Map<String, Object> index() {
        List<Map<String, Object>> endpoints = handlerMapping.getHandlerMethods().entrySet().stream()
                .map(entry -> {
                    var info = new LinkedHashMap<String, Object>();
                    info.put("methods", entry.getKey().getMethodsCondition().getMethods());
                    return info;
                })
                .collect(Collectors.toList());

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "🚀 Welcome to The Evynt app API");
        response.put("version", "v1.0");
        response.put("routes", endpoints);
        return response;
    }
    

}
