package com.example.webapp.controllers;

import com.example.webapp.components.ObjectApiResponse;
import com.example.webapp.models.Ad;
import com.example.webapp.models.User;
import com.example.webapp.repositories.IAdRepository;
import com.example.webapp.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


@Controller
@RequestMapping(produces = "application/json", value = "/api")
public class ViewAdsController {
    private final IAdRepository iAdRepository;
    private final ObjectApiResponse objectApiResponse;
    private final IUserRepository userRepository;
    private final ConversionService conversionService;

    @Autowired
    public ViewAdsController(IAdRepository iAdRepository,
                      ObjectApiResponse objectApiResponse,
                      IUserRepository userRepository,
                      ConversionService conversionService
                      ){
        this.iAdRepository = iAdRepository;
        this.objectApiResponse = objectApiResponse;
        this.userRepository = userRepository;
        this.conversionService = conversionService;
    }
    
    @GetMapping("/ads")
    @ResponseBody
    private String viewAds(Pageable pageable){
        this.objectApiResponse.setData(Map.of("pagination", this.iAdRepository.findAll(pageable)));

        return this.conversionService.convert(objectApiResponse, String.class);
    }

    @GetMapping("/ad/{id}")
    @ResponseBody
    private String viewAd(@PathVariable int id, HttpServletResponse servletResponse){
        Ad ad = this.iAdRepository.findAdById(id);

        if(ad == null){
            servletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            objectApiResponse.setData(Map.of("ad", ad));
        }

        return this.conversionService.convert(objectApiResponse, String.class);
    }

    @GetMapping("/ads/{author}")
    @ResponseBody
    private String viewAdsByAuthor(@PathVariable String author, Pageable pageable){
        User user = this.userRepository.findUserByUsername(author);

        if(user != null){
            objectApiResponse.setData(Map.of("pagination", user.getAds()));
        }

        return this.conversionService.convert(objectApiResponse, String.class);
    }
}
