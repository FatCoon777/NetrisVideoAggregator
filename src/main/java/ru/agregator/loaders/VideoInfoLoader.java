package ru.agregator.loaders;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import ru.agregator.components.VideoInfo;

import java.util.List;

@Service
public class VideoInfoLoader {
    private final WebClient webClient;

    private final String url;

    public VideoInfoLoader(WebClient webClient, @Value("${videoCamUrl}") String url) {
        this.webClient = webClient;
        this.url = url;
    }

    public List<VideoInfo> loadVideoCams() {
        Flux<VideoInfo> videoCamFlux = webClient.get().uri(url).retrieve().bodyToFlux(VideoInfo.class);
        return videoCamFlux.collectList().block();
    }
}
