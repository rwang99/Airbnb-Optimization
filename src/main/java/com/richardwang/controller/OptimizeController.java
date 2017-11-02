package com.richardwang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class OptimizeController {

    @RequestMapping(value = "/")
    public String getHomepage(){
        return "/home";
    }

    @GetMapping("/name")
    public String getPopularNames(){

        return "tomato";
    }
/*
    @RequestMapping
    public String listingDetails(){

        return "/details";
    }
*/

}

