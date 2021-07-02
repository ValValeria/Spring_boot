package com.example.webapp.controllers;

import com.example.webapp.components.ObjectApiResponse;
import com.example.webapp.models.Ad;
import com.example.webapp.models.User;
import com.example.webapp.repositories.IAdRepository;
import com.example.webapp.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;


@Controller
@CrossOrigin(origins = "http://localhost:5000")
@RequestMapping(value = "/api", produces = "application/json", method = RequestMethod.GET)
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
    
    @RequestMapping(value = "/ads")
    @ResponseBody
    private String viewAds(Pageable pageable, @RequestParam(required=false) Integer excludedId){
        HashMap<String, Object> map = new HashMap<>();
        List<Ad> adList;

        if(excludedId != null){
            adList = this.iAdRepository.findAdsByIdNot(excludedId, pageable);
        } else{
            adList = this.iAdRepository.findAll(pageable).getContent();
        }

        adList = adList.stream().map(v -> {
            User user = v.getUser();
            user.clearAds();

            return v;
        }).collect(Collectors.toList());

        map.put("pagination", adList);

        this.objectApiResponse.setData(map);

        return this.conversionService.convert(objectApiResponse, String.class);
    }

    @RequestMapping("/ad/{id}")
    @ResponseBody
    private String viewAd(@PathVariable int id, HttpServletResponse servletResponse){
        Ad ad = this.iAdRepository.findAdById(id);

        ad.getUser().clearAds();

        if(ad == null){
            servletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            objectApiResponse.setData(Map.of("ad", ad));
        }

        return this.conversionService.convert(objectApiResponse, String.class);
    }

    @RequestMapping("/ads/{id}")
    @ResponseBody
    private String viewAdsByAuthor(@PathVariable Long id){
        Optional<User> user = this.userRepository.findById(id);

        if(user.isPresent()){
            List<Ad> adList = user.get().getAds().stream().map(v -> {
                User user1 = v.getUser();
                user1.clearAds();

                return v;
            }).collect(Collectors.toList());

            objectApiResponse.setData(Map.of("pagination", adList));
        }

        return this.conversionService.convert(objectApiResponse, String.class);
    }
}
