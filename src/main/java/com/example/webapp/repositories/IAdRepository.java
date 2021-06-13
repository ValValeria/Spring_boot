package com.example.webapp.repositories;

import com.example.webapp.models.Ad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAdRepository extends JpaRepository<Ad, Long> {
}
