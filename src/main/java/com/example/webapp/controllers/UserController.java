package com.example.webapp.controllers;

import com.example.webapp.components.ObjectApiResponse;
import com.example.webapp.models.User;
import com.example.webapp.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Optional;


@Controller
@RequestMapping(produces = "application/json", value = "/api")
public class UserController {
    private final ObjectApiResponse objectApiResponse;
    private final IUserRepository userRepository;
    private ConversionService conversionService;

    @Autowired
    UserController(ObjectApiResponse objectApiResponse,
                   IUserRepository userRepository,
                   ConversionService conversionService){
        this.objectApiResponse = objectApiResponse;
        this.userRepository = userRepository;
        this.conversionService = conversionService;
    }

    @GetMapping("/user/{id}")
    @ResponseBody
    private String user(@PathVariable int id, HttpServletResponse response){
        Optional<User> user = this.userRepository.findById((long)id);

        if(user.isEmpty()){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            HashMap<String, Object> hashMap = new HashMap<>();
            user.get().clearAds();
            hashMap.put("user", user.get());

            objectApiResponse.setData(hashMap);
            objectApiResponse.setStatus("ok");
        }

        return this.conversionService.convert(objectApiResponse, String.class);
    }
}
