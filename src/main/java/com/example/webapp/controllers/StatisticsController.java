package com.example.webapp.controllers;

import com.example.webapp.components.ObjectApiResponse;
import com.example.webapp.repositories.IStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Map;

@Controller
@RequestMapping(produces = "application/json", value = "/api")
public class StatisticsController {
    private final ConversionService conversionService;
    private final ObjectApiResponse objectApiResponse;
    private final IStatistics iStatistics;

    @Autowired
    public StatisticsController(ObjectApiResponse objectApiResponse,
                         ConversionService conversionService,
                         IStatistics statistics){
        this.objectApiResponse = objectApiResponse;
        this.conversionService = conversionService;
        this.iStatistics = statistics;
    }

    @GetMapping("/stat")
    @ResponseBody
    private String index(Pageable pageable){
        this.objectApiResponse.setData(Map.of("stat",iStatistics.findAll(pageable)));

        return this.conversionService.convert(objectApiResponse, String.class);
    }
}
