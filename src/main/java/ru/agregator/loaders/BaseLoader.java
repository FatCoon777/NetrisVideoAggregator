package ru.agregator.loaders;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.reactive.function.client.WebClient;
import ru.metrics.services.MetricRegistry;

import java.io.IOException;

public abstract class BaseLoader<T> {
    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private final MetricRegistry metricRegistry;

    protected BaseLoader(WebClient webClient, ObjectMapper objectMapper, MetricRegistry metricRegistry) {
        this.webClient = webClient;
        this.objectMapper = objectMapper;
        this.metricRegistry = metricRegistry;
    }

    public T load(String url) throws IOException {
        metricRegistry.applayRequestCount();
        byte[] responeBytes = webClient.get().uri(url).retrieve().bodyToMono(byte[].class).block();
        metricRegistry.applayResponseSize(responeBytes != null ? responeBytes.length : 0);
        return objectMapper.readValue(responeBytes, responseType());
    }

    protected abstract Class<T> responseType();
}
