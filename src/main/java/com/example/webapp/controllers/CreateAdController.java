package com.example.webapp.controllers;

import com.example.webapp.components.ObjectApiResponse;
import com.example.webapp.models.Ad;
import com.example.webapp.models.User;
import com.example.webapp.repositories.IAdRepository;
import com.example.webapp.repositories.IUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.util.FileCopyUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.io.File;


@Controller
@RequestMapping("/admin/create-ad")
public class CreateAdController{
    private final ObjectApiResponse objectApiResponse;
    private IAdRepository adRepository;
    private IUserRepository userRepository;
    Logger logger = LoggerFactory.getLogger(SignupController.class);

    @Autowired
    CreateAdController(ObjectApiResponse objectApiResponse,
                       IAdRepository adRepository,
                       IUserRepository iUserRepository){
        this.objectApiResponse = objectApiResponse;
        this.adRepository = adRepository;
        this.userRepository = iUserRepository;
    }

    @GetMapping("")
    private String index(){
        return "add_ad";
    }

    @PostMapping("")
    private void handleRequest(@Valid Ad ad, BindingResult bindingResult,
                               @RequestParam MultipartFile photo,
                               Authentication authentication,
                               HttpServletResponse response
                               ) throws IOException {
        if(!bindingResult.hasErrors()){
            User user = userRepository.findUserByUsername(authentication.getName());

            if(user != null){
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
                String fileName = dateFormat.format(new Date()) + photo.getOriginalFilename();
                String path = String.format("/static/uploads/%s", fileName );
                File file = new File("src\\main\\resources\\static\\uploads\\"+fileName);

                if(file.createNewFile()){
                    ad.setImage(path);
                    ad.setUser(user);

                    adRepository.save(ad);

                    try(FileOutputStream fileOutputStream = new FileOutputStream(file);){
                        fileOutputStream.write(photo.getBytes());

                        logger.info("Success upload. File is uploaded in path " + file.getPath());

                        objectApiResponse.setStatus("ok");
                    }
                }
            }
        }

        PrintWriter printWriter = response.getWriter();
        printWriter.write(this.objectApiResponse.toJson());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private String errorHandle(MethodArgumentNotValidException exception){
        List<String> stringList = new ArrayList<>();
        stringList.add("Invalid data. Please check the values of fields");

        exception.getBindingResult().getGlobalErrors().forEach(v -> {
            stringList.add(String.format("Invalid %s", v.getObjectName()));
        });

        this.objectApiResponse.setErrors(stringList);

        return this.objectApiResponse.toJson();
    }
}
