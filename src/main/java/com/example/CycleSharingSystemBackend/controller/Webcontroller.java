package com.example.CycleSharingSystemBackend.controller;


import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Webcontroller {
    @GetMapping("/")
    public  String hone(Model model){
        return "index";
    }
}
