package ru.aggregator.mock;

import org.mockito.Mockito;
import ru.agregator.components.VideoToken;
import ru.agregator.loaders.VideoTokenLoader;

public class VideoTokenLoaderMock {
    public static VideoTokenLoader create() {
        VideoTokenLoader mock = Mockito.mock(VideoTokenLoader.class);
        Mockito.when(mock.load(Mockito.any())).thenReturn(getResultValue());
        return mock;
    }

    private static VideoToken getResultValue() {
        VideoToken videoToken = new VideoToken();
        videoToken.setValue("c5e7d26d-8d02-491b-b8d5-66aebc29abed");
        videoToken.setTtl(120);
        return videoToken;
    }
}
