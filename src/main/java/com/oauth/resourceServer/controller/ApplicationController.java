package com.oauth.resourceServer.controller;


import com.oauth.resourceServer.security.SecurityUtil;
import com.oauth.resourceServer.security.anotation.Admin;
import com.oauth.resourceServer.security.anotation.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationController {

    @Admin
    @GetMapping("/admin")
    public String helloWorld() {
        return "I am admin";
    }

    @User
    @GetMapping("/simple-user")
    public String simpleUserController() {
        return "I am a simple user";
    }

    @Admin
    @GetMapping("/clientId")
    public String getClientId() {
        return SecurityUtil.getClientId();
    }


}
