package com.tecnocampus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by santi on 10/10/16.
 */
@Controller
public class controller {

    @RequestMapping("/")
    public String requestMapping() {

        return "index";
    }


}
