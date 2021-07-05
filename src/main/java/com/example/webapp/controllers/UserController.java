package com.example.webapp.controllers;

import com.example.webapp.annotations.RequireAuth;
import com.example.webapp.components.ObjectApiResponse;
import com.example.webapp.models.Ad;
import com.example.webapp.models.User;
import com.example.webapp.repositories.IAdRepository;
import com.example.webapp.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@RequestMapping(produces = "application/json", value = "/api")
public class UserController {
    private final ObjectApiResponse objectApiResponse;
    private final IUserRepository userRepository;
    private final ConversionService conversionService;
    private final IAdRepository adRepository;

    @Autowired
    UserController(ObjectApiResponse objectApiResponse,
                   IUserRepository userRepository,
                   ConversionService conversionService,
                   IAdRepository adRepository
                   ){
        this.objectApiResponse = objectApiResponse;
        this.userRepository = userRepository;
        this.conversionService = conversionService;
        this.adRepository = adRepository;
    }

    @GetMapping("/user/{id}")
    public String user(@PathVariable int id, HttpServletResponse response){
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

    @RequireAuth
    @PostMapping("/change-userdata")
    public String changeUserData(@Valid User user,
                                 BindingResult bindingResult,
                                 @RequestParam(required=false) MultipartFile avatar,
                                 Principal principal
                                 ) throws IOException {
        if(bindingResult.hasErrors()){
            this.objectApiResponse.setErrors(List.of("Invalid data"));
        } else if(user.getId() != null && this.userRepository.existsById(user.getId())){
            String authUsername = principal.getName();

            if(authUsername.equalsIgnoreCase(user.getUsername())){
                if(avatar != null){
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
                    String fileName = dateFormat.format(new Date()) + avatar.getOriginalFilename();
                    String path = String.format("/static/photos/%s", fileName );
                    File file = new File("src\\main\\resources\\public\\photos\\"+fileName);

                    if(file.createNewFile()) {
                        user.setImage(path);
                    }
                }

                this.userRepository.save(user);
                this.objectApiResponse.setStatus("ok");
            } else {
               this.objectApiResponse.setErrors(List.of("You can't update the data of other users"));
            }
        }

        return this.conversionService.convert(objectApiResponse, String.class);
    }

    @RequireAuth
    @GetMapping("/delete-ad/{id}")
    public String deleteAd(@PathVariable int id,
                           Authentication authentication
                           ){
        String authUsername = authentication.getName();
        Collection<?> grantedAuthorities = authentication.getAuthorities();

        if(this.adRepository.existsById(id)){
            Ad ad = this.adRepository.getById(id);
            String username = ad.getUser().getUsername();

            if(username.equalsIgnoreCase(authUsername) || grantedAuthorities.contains("admin")){
                this.adRepository.deleteById(id);
                this.objectApiResponse.setStatus("ok");
            }
        }

        return this.conversionService.convert(objectApiResponse, String.class);
    }

    @RequireAuth
    @PostMapping ("/update-ad/")
    public String updateAd(@Valid Ad ad,
                           BindingResult bindingResult,
                           Authentication authentication){
        String authUsername = authentication.getName();
        Collection<?> grantedAuthorities = authentication.getAuthorities();

        if(bindingResult.hasErrors()){
            this.objectApiResponse.setErrors(List.of("Invalid data"));
        } else if(!this.adRepository.existsById(ad.getId())){
            this.objectApiResponse.setErrors(List.of("The ad doesn't exist"));
        } else {
            Ad ad2 = this.adRepository.getById(ad.getId());
            String username = ad2.getUser().getUsername();

            if(username.equalsIgnoreCase(authUsername) || grantedAuthorities.contains("admin")){
                this.adRepository.save(ad);
                this.objectApiResponse.setStatus("ok");
            }
        }

        return this.conversionService.convert(objectApiResponse, String.class);
    }

    @GetMapping("/auth-user")
    public String getCurrentlyAuthenticatedUser(Authentication authentication){
        if(authentication != null && authentication.isAuthenticated()){
            User user = userRepository.findUserByUsername(authentication.getName());
            this.objectApiResponse.setData(Map.of("user", user));
        }

        return this.conversionService.convert(objectApiResponse, String.class);
    }
}
