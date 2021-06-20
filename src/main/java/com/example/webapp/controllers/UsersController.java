package com.example.webapp.controllers;

import com.example.webapp.components.ObjectApiResponse;
import com.example.webapp.models.User;
import com.example.webapp.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;

@Controller
@RequestMapping("/api")
public class UsersController {
    private IUserRepository userRepository;
    private ObjectApiResponse objectApiResponse;
    private final ConversionService conversionService;

    @Autowired
    UsersController(IUserRepository userRepository,
                    ObjectApiResponse objectApiResponse,
                    ConversionService conversionService
                    ){
        this.userRepository = userRepository;
        this.objectApiResponse = objectApiResponse;
        this.conversionService = conversionService;
    }

    @GetMapping("/users")
    @ResponseBody
    private String users(Pageable pageable){
        Page<User> userPage = userRepository.findAll(pageable);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("pagination", userPage);

        objectApiResponse.setData(hashMap);
        objectApiResponse.setStatus("ok");

        return this.conversionService.convert(objectApiResponse, String.class);
    }
}
