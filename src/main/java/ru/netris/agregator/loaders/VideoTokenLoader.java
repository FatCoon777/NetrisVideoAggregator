package ru.netris.agregator.loaders;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.netris.agregator.components.VideoToken;

@Service
public class VideoTokenLoader {
    private final WebClient webClient;

    public VideoTokenLoader(WebClient webClient) {
        this.webClient = webClient;
    }

    public VideoToken load(String url) {
        return webClient.get().uri(url).retrieve().bodyToMono(VideoToken.class).block();
    }
}
