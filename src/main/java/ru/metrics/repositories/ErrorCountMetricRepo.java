package ru.metrics.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.metrics.components.ErrorCountMetric;

import java.time.LocalDateTime;

public interface ErrorCountMetricRepo extends JpaRepository<ErrorCountMetric, Integer> {
    Integer countAllByCreateDateAfter(LocalDateTime date);
}
