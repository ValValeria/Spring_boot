package com.example.webapp.aop;

import com.example.webapp.components.ObjectApiResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletResponse;

@Component
@Aspect
public class AuthAspect {
    private final ObjectApiResponse objectApiResponse;
    private final HttpServletResponse response;
    private final ConversionService conversionService;

    @Autowired
    AuthAspect(HttpServletResponse response,
               ObjectApiResponse objectApiResponse,
               ConversionService conversionService
               ){
        this.response = response;
        this.objectApiResponse = objectApiResponse;
        this.conversionService = conversionService;
    }

    @Around("@annotation(com.example.webapp.annotations.RequireAuth)")
    private Object index(ProceedingJoinPoint joinPoint) throws Throwable {
       Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();

       if(authentication == null){
          response.setStatus(HttpServletResponse.SC_FORBIDDEN);
          return this.conversionService.convert(objectApiResponse, String.class);
       } else {
          return joinPoint.proceed();
       }
    }
}
