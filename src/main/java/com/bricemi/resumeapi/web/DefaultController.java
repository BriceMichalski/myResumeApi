package com.bricemi.resumeapi.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {

    @GetMapping("/")
    public String helloWorld(){
        return "{\"msg\":\"Hello World\"}";
    }

}
