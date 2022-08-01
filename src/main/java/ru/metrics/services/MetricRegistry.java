package ru.metrics.services;

import org.springframework.stereotype.Service;
import ru.metrics.components.ErrorCountMetric;
import ru.metrics.components.RequestCountMetric;
import ru.metrics.components.ResponseSizeMetric;
import ru.metrics.repositories.ErrorCountMetricRepo;
import ru.metrics.repositories.RequestCountMetricRepo;
import ru.metrics.repositories.ResponseSizeMetricRepo;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class MetricRegistry {
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final ErrorCountMetricRepo errorCountMetricRepo;
    private final RequestCountMetricRepo requestCountMetricRepo;
    private final ResponseSizeMetricRepo responseSizeMetricRepo;

    public MetricRegistry(ErrorCountMetricRepo errorCountMetricRepo,
                          RequestCountMetricRepo requestCountMetricRepo,
                          ResponseSizeMetricRepo responseSizeMetricRepo) {
        this.errorCountMetricRepo = errorCountMetricRepo;
        this.requestCountMetricRepo = requestCountMetricRepo;
        this.responseSizeMetricRepo = responseSizeMetricRepo;
    }

    public void applayErrorCount() {
        executor.execute(() -> {
            ErrorCountMetric metric = new ErrorCountMetric();
            metric.setCreateDate(LocalDateTime.now());
            errorCountMetricRepo.save(metric);
        });
    }

    public void applayRequestCount() {
        executor.execute(() -> {
            RequestCountMetric metric = new RequestCountMetric();
            metric.setCreateDate(LocalDateTime.now());
            requestCountMetricRepo.save(metric);
        });
    }

    public void applayResponseSize(int size) {
        executor.execute(() -> {
            ResponseSizeMetric metric = new ResponseSizeMetric();
            metric.setRequestSize(size);
            metric.setCreateDate(LocalDateTime.now());
            responseSizeMetricRepo.save(metric);
        });
    }
}
