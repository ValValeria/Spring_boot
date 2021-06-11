package com.example.webapp.controllers;

import com.example.webapp.components.ObjectApiResponse;
import com.example.webapp.models.User;
import com.example.webapp.repositories.IUserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Optional;


@Controller
public class UserController {
    private final ObjectApiResponse objectApiResponse;
    private final IUserRepository userRepository;

    UserController(ObjectApiResponse objectApiResponse, IUserRepository userRepository){
        this.objectApiResponse = objectApiResponse;
        this.userRepository = userRepository;
    }

    @GetMapping("/user/{id}")
    @ResponseBody
    private String user(@PathVariable int id, HttpServletResponse response){
        Optional<User> user = this.userRepository.findById((long)id);

        if(user.isEmpty()){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("user", user.get());

            objectApiResponse.setData(hashMap);
            objectApiResponse.setStatus("ok");
        }

        return objectApiResponse.toJson();
    }
}
