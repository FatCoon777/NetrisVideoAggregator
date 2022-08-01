package ru.agregator.loaders;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.agregator.components.VideoSource;
import ru.metrics.services.MetricRegistry;

@Service
public class VideoSourceLoader extends BaseLoader<VideoSource> {
    protected VideoSourceLoader(WebClient webClient, ObjectMapper objectMapper, MetricRegistry metricRegistry) {
        super(webClient, objectMapper, metricRegistry);
    }

    @Override
    protected Class<VideoSource> responseType() {
        return VideoSource.class;
    }
}
