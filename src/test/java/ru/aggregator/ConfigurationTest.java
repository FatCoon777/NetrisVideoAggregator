package ru.aggregator;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import ru.aggregator.mock.VideoInfoLoaderMock;
import ru.aggregator.mock.VideoSourceLoaderMock;
import ru.aggregator.mock.VideoTokenLoaderMock;
import ru.agregator.loaders.VideoInfoLoader;
import ru.agregator.loaders.VideoSourceLoader;
import ru.agregator.loaders.VideoTokenLoader;

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
