package ru.metrics.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.metrics.components.HealthInfo;
import ru.metrics.services.MetricDataService;

@RestController
public class HealthRestController {
    private final MetricDataService metricDataService;

    public HealthRestController(MetricDataService metricDataService) {
        this.metricDataService = metricDataService;
    }

    @GetMapping(value = "/health")
    public HealthInfo health() {
        HealthInfo healthInfo = new HealthInfo();
        healthInfo.setErrorCount(metricDataService.getErrorCount());
        healthInfo.setRequestCount(metricDataService.getRequestCount());
        healthInfo.setResponseSize(metricDataService.getResponseSize());
        return healthInfo;
    }
}
