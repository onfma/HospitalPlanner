package com.example.hospitalplanner.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/createAccount")
public class CreateAccountController {

    @GetMapping
    public String showLoginPage() {
        System.out.println("S-a afisat pagina createAccountPage.hmtl!");
        return "createAccountPage";
    }
}
