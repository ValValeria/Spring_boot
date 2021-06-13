package com.example.webapp.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@PreAuthorize("isAnonymous()")
public class LoginController {

    @GetMapping("/login")
    private String index(){
        return "login";
    }
}
