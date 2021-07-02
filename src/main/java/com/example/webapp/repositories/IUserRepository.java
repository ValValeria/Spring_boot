package com.example.webapp.repositories;

import com.example.webapp.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
    User findUserByUsernameOrEmail(String username, String email);
    List<User> findByUsernameContainingIgnoreCase(String username, Pageable pageable);
}
