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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;


@RestController
public class SearchController {
    private final IAdRepository adRepository;
    private final IUserRepository userRepository;
    private final ObjectApiResponse objectApiResponse;
    private final ConversionService conversionService;

    @Autowired
    SearchController(ObjectApiResponse objectApiResponse,
                     ConversionService conversionService,
                     IAdRepository adRepository,
                     IUserRepository userRepository
                     ){
        this.objectApiResponse = objectApiResponse;
        this.conversionService = conversionService;
        this.adRepository = adRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/api/search/user")
    public String searchForUser(@RequestParam() String search,
                                Pageable pageable){
        Page<User> page = this.userRepository.findByUsernameContainingIgnoreCase(search, pageable);

        page.getContent().forEach(User::clearAds);

        this.objectApiResponse.setData(Map.of("pagination", page));

        return this.conversionService.convert(objectApiResponse, String.class);
    }

    @GetMapping("/api/search/ads")
    public String searchForAds(@RequestParam() String search,
                                Pageable pageable){
        Page<Ad> page = this.adRepository.findAdsByTitleContaining(search, pageable);

        page.getContent().forEach(v -> v.getUser().clearAds());

        this.objectApiResponse.setData(Map.of("pagination", page));

        return this.conversionService.convert(objectApiResponse, String.class);
    }
}
