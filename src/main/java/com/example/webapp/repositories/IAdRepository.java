package com.example.webapp.repositories;

import com.example.webapp.models.Ad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface IAdRepository extends JpaRepository<Ad, Integer> {
    @Query("select u from Ad u where u.user.id = :id")
    Page<Ad> findAdsRelatedToUser(@Param("id") Long id, Pageable pageable);
    @Query("select u.id from Ad u where u.user.id = :id")
    List<Integer> getIdAdsRelatedToUser(@Param("id") Long id);
    Ad findAdById(int id);
    Page<Ad> findAdsByIdNot(int id, Pageable pageable);
    Page<Ad> findAdsByTitleContaining(String title, Pageable pageable);
}
