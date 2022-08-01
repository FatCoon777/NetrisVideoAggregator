package ru.agregator.loaders;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.agregator.components.VideoToken;
import ru.metrics.services.MetricRegistry;

@Service
public class VideoTokenLoader extends BaseLoader<VideoToken> {
    protected VideoTokenLoader(WebClient webClient, ObjectMapper objectMapper, MetricRegistry metricRegistry) {
        super(webClient, objectMapper, metricRegistry);
    }

    @Override
    protected Class<VideoToken> responseType() {
        return VideoToken.class;
    }
}
