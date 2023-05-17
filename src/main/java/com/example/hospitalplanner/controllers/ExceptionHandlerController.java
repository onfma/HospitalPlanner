package com.example.hospitalplanner.controllers;

import com.example.hospitalplanner.exceptions.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(AuthenticationException.class)
    public ModelAndView handleAuthenticationException(AuthenticationException ex) {
        ModelAndView modelAndView = new ModelAndView("loginPage");
        modelAndView.addObject("error", ex.getMessage());
        return modelAndView;
    }
}