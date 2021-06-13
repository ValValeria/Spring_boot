package com.example.webapp.repositories;

import com.example.webapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
    User findUserByUsernameOrEmail(String username, String email);
}
