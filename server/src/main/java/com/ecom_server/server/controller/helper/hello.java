package com.ecom_server.server.controller.helper;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class hello {

    @GetMapping("api/hello")
    public String sayHello(){
        return("Hello Working Yay...");
    }


}

