package ru.metrics.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.metrics.components.RequestCountMetric;

import java.time.LocalDateTime;

public interface RequestCountMetricRepo extends JpaRepository<RequestCountMetric, Integer> {
    Integer countAllByCreateDateAfter(LocalDateTime date);
}
