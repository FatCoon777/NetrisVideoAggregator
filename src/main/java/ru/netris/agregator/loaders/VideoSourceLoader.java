package ru.netris.agregator.loaders;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.netris.agregator.components.VideoSource;

@Service
public class VideoSourceLoader {
    private final WebClient webClient;

    public VideoSourceLoader(WebClient webClient) {
        this.webClient = webClient;
    }

    public VideoSource load(String url) {
        return webClient.get().uri(url).retrieve().bodyToMono(VideoSource.class).block();
    }
}
