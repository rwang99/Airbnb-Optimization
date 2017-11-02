package com.richardwang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class OptimizeController {

    @RequestMapping(value = "/")
    public String getHomepage(){
        return "/home";
    }

/*
    @RequestMapping
    public String listingDetails(){

        return "/details";
    }
*/

}

