package com.example.webapp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
@ControllerAdvice
public class ErrorController {
    Logger logger = LoggerFactory.getLogger(SignupController.class);

    @ExceptionHandler(Throwable.class)
    private String index(Exception exception){
        logger.error("Type of exception is " + exception.getClass().getName());
        logger.error("Error message : "+ exception.getMessage());

        exception.printStackTrace();

        return "error";
    }
}
