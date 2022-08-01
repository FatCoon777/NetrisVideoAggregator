package ru.agregator.loaders;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.agregator.components.VideoInfo;
import ru.metrics.services.MetricRegistry;

import java.io.IOException;

@Service
public class VideoInfoLoader extends BaseLoader<VideoInfo[]> {
    private final String url;

    protected VideoInfoLoader(WebClient webClient,
                              ObjectMapper objectMapper,
                              @Value("${videoCamUrl}") String url,
                              MetricRegistry metricRegistry) {
        super(webClient, objectMapper, metricRegistry);
        this.url = url;
    }

    @Override
    protected Class<VideoInfo[]> responseType() {
        return VideoInfo[].class;
    }

    public VideoInfo[] load() throws IOException {
        return load(url);
    }
}
