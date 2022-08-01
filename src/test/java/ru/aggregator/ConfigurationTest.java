package ru.aggregator;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import ru.aggregator.mock.MetricRegistryMock;
import ru.aggregator.mock.VideoInfoLoaderMock;
import ru.aggregator.mock.VideoSourceLoaderMock;
import ru.aggregator.mock.VideoTokenLoaderMock;
import ru.agregator.loaders.VideoInfoLoader;
import ru.agregator.loaders.VideoSourceLoader;
import ru.agregator.loaders.VideoTokenLoader;
import ru.metrics.services.MetricRegistry;

import java.io.IOException;

@TestConfiguration
public class ConfigurationTest {
    @Bean
    @Primary
    public VideoInfoLoader videoInfoLoaderMock() throws IOException {
        return VideoInfoLoaderMock.create();
    }

    @Bean
    @Primary
    public VideoSourceLoader videoSourceLoaderMock() throws IOException {
        return VideoSourceLoaderMock.create();
    }

    @Bean
    @Primary
    public VideoTokenLoader videoTokenLoaderMock() throws IOException {
        return VideoTokenLoaderMock.create();
    }

    @Bean
    @Primary
    public MetricRegistry metricRegistryMock() {
        return MetricRegistryMock.create();
    }
}
