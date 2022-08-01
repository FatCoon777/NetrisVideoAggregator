package ru.metrics.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.metrics.components.ResponseSizeMetric;

import java.time.LocalDateTime;

public interface ResponseSizeMetricRepo extends JpaRepository<ResponseSizeMetric, Integer> {
    @Query(
            "select sum(requestSize) from ResponseSizeMetric where createDate >= :date"
    )
    Integer sumAllByCreateDateAfter(@Param("date") LocalDateTime date);
}
