package com.example.webapp.aop;

import com.example.webapp.components.ObjectApiResponse;
import com.example.webapp.models.Statistics;
import com.example.webapp.repositories.IStatistics;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class StatisticsAspect {
    private final ObjectApiResponse objectApiResponse;
    private final Logger logger = LoggerFactory.getLogger(StatisticsAspect.class);
    private final IStatistics iStatistics;
    private final HttpServletRequest request;

    @Autowired
    StatisticsAspect(ObjectApiResponse objectApiResponse,
                     HttpServletRequest request,
                     IStatistics iStatistics
                     ){
        this.request = request;
        this.objectApiResponse = objectApiResponse;
        this.iStatistics = iStatistics;
    }

    @Pointcut("execution(public * com.example.webapp.controllers.*.*(..))")
    private void pointcut(){}

    @Before("pointcut()")
    private void beforeAdvice(){
        this.objectApiResponse.clearData();

        this.logger.info("Clearing the data in objectApiResponse");
    }

    @After("pointcut() && !execution( * com.example.webapp.controllers.StatisticsController.*(..))")
    private void afterAdvice() throws JsonProcessingException {
        Statistics statistics = iStatistics.findByDate(LocalDate.now());
        String ip = this.request.getRemoteAddr();

        if(statistics != null){
           Map<String, Integer> map = statistics.getMapStatistics();
           map.putIfAbsent(ip, 1);
           map.computeIfPresent(ip, (k,v) -> ++v);
        } else if(ip != null){
           Statistics statistics1 = new Statistics();

           HashMap<String, Integer> hashMap = new HashMap<>();
           hashMap.put(ip, 1);

           statistics1.setStatistics(new ObjectMapper().writeValueAsString(hashMap));
           statistics1.setDate(LocalDate.now());

           iStatistics.save(statistics1);
        }

        this.logger.info("Saving statistics");
    }
}
