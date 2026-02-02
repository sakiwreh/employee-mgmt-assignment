package com.example.EMS.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<String> login(HttpServletResponse response){
        Cookie cookie = new Cookie("auth_token","demo_token_value");
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.ok("Your login attempt was successfull | Authentication cookie is set.");
    }
}
