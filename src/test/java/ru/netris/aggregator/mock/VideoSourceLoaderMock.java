package ru.netris.aggregator.mock;

import org.mockito.Mockito;
import ru.netris.agregator.components.VideoSource;
import ru.netris.agregator.loaders.VideoSourceLoader;

public class VideoSourceLoaderMock {
    public static VideoSourceLoader create() {
        VideoSourceLoader mock = Mockito.mock(VideoSourceLoader.class);
        Mockito.when(mock.load(Mockito.any())).thenReturn(getResultValue());
        return mock;
    }

    private static VideoSource getResultValue() {
        VideoSource videoSource = new VideoSource();
        videoSource.setUrlType("ARCHIVE");
        videoSource.setVideoUrl("/some_url");
        return videoSource;
    }
}
