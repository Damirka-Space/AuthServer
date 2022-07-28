package com.damirka.authserver.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class TestController {

    @GetMapping("/hello")
    @ResponseBody
    public String test() {
        return "hello, world";
    }
}
