package ru.netris.aggregator;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import ru.netris.aggregator.mock.VideoInfoLoaderMock;
import ru.netris.aggregator.mock.VideoSourceLoaderMock;
import ru.netris.aggregator.mock.VideoTokenLoaderMock;
import ru.netris.agregator.loaders.VideoInfoLoader;
import ru.netris.agregator.loaders.VideoSourceLoader;
import ru.netris.agregator.loaders.VideoTokenLoader;

@TestConfiguration
public class ConfigurationTest {
    @Bean
    @Primary
    public VideoInfoLoader videoInfoLoaderMock() {
        return VideoInfoLoaderMock.create();
    }

    @Bean
    @Primary
    public VideoSourceLoader videoSourceLoaderMock() {
        return VideoSourceLoaderMock.create();
    }

    @Bean
    @Primary
    public VideoTokenLoader videoTokenLoaderMock() {
        return VideoTokenLoaderMock.create();
    }
}
