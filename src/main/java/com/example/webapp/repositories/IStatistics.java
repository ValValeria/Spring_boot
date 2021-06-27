package com.example.webapp.repositories;

import com.example.webapp.models.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface IStatistics extends JpaRepository<Statistics, Long> {
    Statistics findByDate(LocalDate localDate);
}
