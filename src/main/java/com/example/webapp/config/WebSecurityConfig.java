package com.example.webapp.config;

import com.example.webapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userService;

    @Autowired
    WebSecurityConfig(UserService userService){
        this.userService = userService;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
       httpSecurity.csrf().disable()
               .authorizeRequests()
               .antMatchers("/create-ad").authenticated()
               .antMatchers("/api/stat").hasRole("admin")
               .antMatchers("/signup", "/login").anonymous()
               .anyRequest().permitAll()
               .and()
               .exceptionHandling().accessDeniedPage("/login")
               .and()
               .formLogin()
               .loginPage("/login")
               .defaultSuccessUrl("/create-ad")
               .and()
               .rememberMe()
               .and()
               .logout()
               .invalidateHttpSession(true)
               .logoutSuccessUrl("/");
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }
}
