package com.example.webapp.models;

import javax.persistence.*;
import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="spring_users",schema="1oASotOvGd")
@JsonIgnoreProperties(value = { "password", "email"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String username;

    @Column
    private String role;

    @OneToMany(mappedBy="user", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL, targetEntity=Ad.class)
    private List<Ad> ads = new ArrayList<>();

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role){
        String[] allowedRoles = {"user", "admin"};

        if(Arrays.binarySearch(allowedRoles, role) == -1){
            throw new IllegalArgumentException();
        } else {
            this.role = role;
        }
    }

    public String getRole(){
        return this.role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Ad> getAds(){
        return ads;
    }

    public void clearAds(){
        this.ads = new ArrayList<>();
    }
}
