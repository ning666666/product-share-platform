package com.share.platform.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerController {  
  
    @GetMapping("/platform-swagger-ui.html")
    public String redirectToSwaggerUi() {  
        return "forward:/swagger-ui.html";  
    }  
}