package com.example.SwizzSoft_Sms_app.SecurityAndJwt.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContentController {

    @GetMapping("/home")
    public String handleWelcome(){
        return "home";
    }

    @GetMapping("/admin/home")
    public String handleAdmin(){
        return "home_admin";
    }

    @GetMapping("/user/home")
    public String handleUser(){
        return "home_user";
    }


}
