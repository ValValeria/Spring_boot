package com.example.webapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name="spring_statistics")
@JsonIgnoreProperties(value={"date"})
public class Statistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "DATE")
    private LocalDate date;

    @Column(columnDefinition = "json")
    private String statistics;

    public Statistics(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @JsonIgnore
    public String getStatistics() {
        return statistics;
    }

    public void setStatistics(String statistics) {
        this.statistics = statistics;
    }

    public void addToStatistics(String ip, int times) throws JsonProcessingException {
        Map<String, Integer> map = this.getMapStatistics();
        map.put(ip, times);

        ObjectMapper objectMapper = new ObjectMapper();
        this.statistics = objectMapper.writeValueAsString(map);
    }

    public Map<String, Integer> getMapStatistics(){
        Map<String, Integer> ips = Map.of();

        try{
            ips = new ObjectMapper().readValue(this.statistics, HashMap.class);
        }catch (Throwable e){
            e.printStackTrace();
        }

        return ips;
    }

    public String getTime(){
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("d/MM/uuuu");
        String text = date.format(formatters);

        return text;
    }
}
