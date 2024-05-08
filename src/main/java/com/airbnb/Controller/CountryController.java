package com.airbnb.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/countries")
public class CountryController {

    @PostMapping("/addcountry")
    public String addCountry(){
       return "done";
    }
}
