package com.example.taskmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.taskmanager.model.User;

import com.example.taskmanager.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

// handles incoming http requests and decides what happens in response
@Controller //returns view for thymeleaft, not json, this is a server based rendering scheme
public class RegistrationController {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    public RegistrationController(PasswordEncoder pEncoder, UserRepository uRepo){
        this.passwordEncoder = pEncoder;
        this.userRepository = uRepo;
        
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }
    
    @PostMapping("/register") 
    
    public String postRegister(@RequestParam String  username, @RequestParam String password) {
        if (username == null || username.isBlank() || username.length() < 6) {
            return "redirect:/register?error";
        }
        if (userRepository.findByUsername(username).isPresent()){
            return "redirect:/register?error";
        }
        if (password == null || password.isBlank() || password.length() < 12){
            return "redirect:/register?error";
        }
        
        String encoded = passwordEncoder.encode(password);
        User newUser = new User(username, encoded, "USER");
        userRepository.save(newUser);
        return "redirect:/login";
    }
}