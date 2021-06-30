package com.example.webapp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Level;

@Controller
@ControllerAdvice
public class ErrorController {
    Logger logger = LoggerFactory.getLogger(SignupController.class);

    @ExceptionHandler(Exception.class)
    private String index(Exception exception){
        logger.error("Type of exception is " + exception.getClass().getName());
        logger.error("Error message : "+ exception.getMessage());

        exception.printStackTrace();

        return "error";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handleError404(HttpServletRequest request, Exception e)   {
        return new ModelAndView("home");
    }
}
