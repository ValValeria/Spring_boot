package com.example.webapp.services;

import com.example.webapp.auth.AuthUser;
import com.example.webapp.models.User;
import com.example.webapp.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException("User with such username is not found");
        }

        return new AuthUser(user);
    }
}
