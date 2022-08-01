package ru.agregator.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.agregator.services.AggregateDataService;
import ru.agregator.exception.EmptyAggregateException;

@RestController
public class VideoRestController {
    private final AggregateDataService aggregateDataService;

    public VideoRestController(AggregateDataService aggregateDataService) {
        this.aggregateDataService = aggregateDataService;
    }

    @GetMapping(value = "${respApi.url}", produces = MediaType.APPLICATION_JSON_VALUE)
    public byte[] getVideo() throws EmptyAggregateException {
        return aggregateDataService.read();
    }
}
