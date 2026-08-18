package com.example.taskmanager.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// handles incoming http requests and decides what happens in response
@Controller //returns view for thymeleaft, not json, this is a server based rendering scheme
public class LoginController {

    @GetMapping("/")
    public String home(Principal principal) {
        return principal != null ? "redirect:/dashboard" : "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
}