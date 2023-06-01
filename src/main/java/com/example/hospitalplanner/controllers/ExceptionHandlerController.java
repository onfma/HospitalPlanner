package com.example.hospitalplanner.controllers;

import com.example.hospitalplanner.exceptions.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(AuthenticationException.class)
    public ModelAndView handleAuthenticationException(AuthenticationException ex) {
        ModelAndView modelAndView = new ModelAndView("loginPage");
        modelAndView.addObject("error", ex.getMessage());
        return modelAndView;
    }

    ////////////////////////////

    @ExceptionHandler(MakeAppointmentException.class)
    public ModelAndView handleMakeAppointmentException(MakeAppointmentException ex) {
        ModelAndView modelAndView = new ModelAndView("patient/patientMakeAppointmentCabinet");
        modelAndView.addObject("error", ex.getMessage());
        return modelAndView;
    }

    ////////////////////////////

    @ExceptionHandler(CreateAccountException.class)
    public ModelAndView handleCreateAccountException(CreateAccountException ex) {
        ModelAndView modelAndView = new ModelAndView("createAccountPage");
        modelAndView.addObject("error", ex.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(CreateAccountSuccess.class)
    public ModelAndView handleCreateAccountSuccess(CreateAccountSuccess ex) {
        ModelAndView modelAndView = new ModelAndView("createAccountPage");
        modelAndView.addObject("successMessage", ex.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(ChangeAccountException.class)
    public ModelAndView handleChangeAccountException(ChangeAccountException ex) {
        ModelAndView modelAndView = new ModelAndView("patientViewAccount");
        modelAndView.addObject("error", ex.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(DoctorChangeAccountException.class)
    public ModelAndView handleDoctorChangeAccountException(DoctorChangeAccountException ex) {
        ModelAndView modelAndView = new ModelAndView("doctorViewAccount");
        modelAndView.addObject("error", ex.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(ChangeAccountSuccess.class)
    public ModelAndView handleChangeAccountSuccess(ChangeAccountSuccess ex) {
        ModelAndView modelAndView = new ModelAndView("patientViewAccount");
        modelAndView.addObject("successMessage", ex.getMessage());
        return modelAndView;
    }
}
