package ru.metrics.services;

import org.springframework.stereotype.Service;
import ru.metrics.repositories.ErrorCountMetricRepo;
import ru.metrics.repositories.RequestCountMetricRepo;
import ru.metrics.repositories.ResponseSizeMetricRepo;

import java.time.LocalDateTime;

@Service
public class MetricDataService {
    private final ErrorCountMetricRepo errorCountMetricRepo;
    private final RequestCountMetricRepo requestCountMetricRepo;
    private final ResponseSizeMetricRepo responseSizeMetricRepo;

    public MetricDataService(ErrorCountMetricRepo errorCountMetricRepo,
                             RequestCountMetricRepo requestCountMetricRepo,
                             ResponseSizeMetricRepo responseSizeMetricRepo) {
        this.errorCountMetricRepo = errorCountMetricRepo;
        this.requestCountMetricRepo = requestCountMetricRepo;
        this.responseSizeMetricRepo = responseSizeMetricRepo;
    }

    public Integer getErrorCount() {
        return errorCountMetricRepo.countAllByCreateDateAfter(LocalDateTime.now().minusHours(1));
    }

    public Integer getRequestCount() {
        return requestCountMetricRepo.countAllByCreateDateAfter(LocalDateTime.now().minusHours(1));
    }

    public Integer getResponseSize() {
        return responseSizeMetricRepo.sumAllByCreateDateAfter(LocalDateTime.now().minusHours(1));
    }
}
