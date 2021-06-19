package com.example.webapp.controllers;

import com.example.webapp.models.User;
import com.example.webapp.repositories.IUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@Controller
@RequestMapping("/signup")
public class SignupController {
    Logger logger = LoggerFactory.getLogger(SignupController.class);

    @Autowired
    private IUserRepository iUserRepository;

    @GetMapping("")
    private String index(){
        return "signup";
    }

    @PostMapping("")
    private void signup(@Valid User user,
                          BindingResult bindingResult,
                          HttpServletResponse httpServletResponse,
                          HttpServletRequest httpServletRequest
                          ) throws IOException {
        String baseUrl = String.format("%s://%s:%d",
                httpServletRequest.getScheme(),
                httpServletRequest.getServerName(),
                httpServletRequest.getServerPort());
        String email = user.getEmail();
        String username = user.getUsername();

        if(!bindingResult.hasErrors()){
            User user1 = iUserRepository.findUserByUsernameOrEmail(username, email);

            if(user1 != null){
                setUpRedirect(baseUrl, httpServletResponse, "error", "");
            } else {
                user.setRole("user");
                user.setPassword(user.getPassword());

                iUserRepository.save(user);

                SecurityContext context = SecurityContextHolder.createEmptyContext();
                Collection<GrantedAuthority> authorityCollection = new ArrayList<>();
                authorityCollection.add(new SimpleGrantedAuthority(user.getRole()));

                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), authorityCollection);
                context.setAuthentication(authentication);
                SecurityContextHolder.setContext(context);

                httpServletResponse.sendRedirect("/admin");

                logger.info("Authentication is successful");
            }
        } else {
            setUpRedirect(baseUrl, httpServletResponse, "inputError", "");
        }
    }

    private void setUpRedirect( String baseUrl, HttpServletResponse httpServletResponse, String queryParam, String queryParamValue) throws IOException {
        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(baseUrl);
        UriBuilder uriBuilder = uriBuilderFactory.uriString("/signup").queryParam(queryParam, queryParamValue);

        httpServletResponse.sendRedirect(uriBuilder.build().toString());
    }
}
