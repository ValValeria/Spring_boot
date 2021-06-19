package com.example.webapp.controllers;

import com.example.webapp.components.ObjectApiResponse;
import com.example.webapp.models.Ad;
import com.example.webapp.models.User;
import com.example.webapp.repositories.IAdRepository;
import com.example.webapp.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;


@Controller
public class ViewAdsController {
    private final IAdRepository iAdRepository;
    private final ObjectApiResponse objectApiResponse;
    private final IUserRepository userRepository;

    @Autowired
    public ViewAdsController(IAdRepository iAdRepository,
                      ObjectApiResponse objectApiResponse,
                      IUserRepository userRepository
                      ){
        this.iAdRepository = iAdRepository;
        this.objectApiResponse = objectApiResponse;
        this.userRepository = userRepository;
    }
    
    @GetMapping("/api/ads")
    @ResponseBody
    private String viewAds(Pageable pageable){
        Page<Ad> page = this.iAdRepository.findAll(pageable);

        this.objectApiResponse.setData(Map.of("ads", page));

        return this.objectApiResponse.toJson();
    }

    @GetMapping("/api/ad/{id}")
    @ResponseBody
    private String viewAd(@PathVariable int id, HttpServletResponse servletResponse){
        Ad ad = this.iAdRepository.findAdById(id);

        if(ad == null){
            servletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            objectApiResponse.setData(Map.of("ad", ad));
        }

        return objectApiResponse.toJson();
    }

    @GetMapping("/api/ads/{author}")
    @ResponseBody
    private String viewAdsByAuthor(@PathVariable String author, Pageable pageable){
        User user = this.userRepository.findUserByUsername(author);

        if(user != null){
            List<Ad> ads = this.iAdRepository.findAdsRelatedToUser(user.getId().intValue(), pageable);
            objectApiResponse.setData(Map.of("ads", ads));
        }

        return objectApiResponse.toJson();
    }
}
