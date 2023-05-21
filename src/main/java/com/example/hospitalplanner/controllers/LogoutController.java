package com.example.hospitalplanner.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/logout")
public class LogoutController {

    @Autowired
    private HttpSession session;

    @GetMapping
    public String logout() {
        // Invalidate the session
        session.invalidate();

        // Redirect to the login page or any other page you desire
        return "redirect:/login";
    }
}