package com.example.webapp.components;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ObjectApiResponse {
    private List<String> errors = new ArrayList<>();
    private String status;
    private Map<String, Object> data = new LinkedHashMap<>();

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String toJson(){
        try{
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            return gson.toJson(this);
        }catch(Throwable e){
            return null;
        }
    }
}
