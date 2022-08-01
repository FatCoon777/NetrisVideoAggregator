package ru.aggregator.mock;

import org.mockito.Mockito;
import ru.metrics.services.MetricRegistry;

public class MetricRegistryMock {
    public static MetricRegistry create() {
        return Mockito.mock(MetricRegistry.class);
    }
}
