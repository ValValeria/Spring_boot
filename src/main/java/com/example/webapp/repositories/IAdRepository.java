package com.example.webapp.repositories;

import com.example.webapp.models.Ad;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface IAdRepository extends JpaRepository<Ad, Integer> {
    @Query("select u from Ad u where u.user.username = :author")
    List<Ad> findAdsRelatedToUser(@Param("author") String author, Pageable pageable);
    Ad findAdById(int id);
}
