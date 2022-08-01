package ru.aggregator.mock;

import org.mockito.Mockito;
import ru.agregator.components.VideoInfo;
import ru.agregator.loaders.VideoInfoLoader;

import java.io.IOException;

public class VideoInfoLoaderMock {
    public static VideoInfoLoader create() throws IOException {
        VideoInfoLoader mock = Mockito.mock(VideoInfoLoader.class);
        Mockito.when(mock.load()).thenReturn(getResultValue());
        return mock;
    }

    private static VideoInfo[] getResultValue() {
        VideoInfo videoInfo = new VideoInfo();
        videoInfo.setId(112L);
        videoInfo.setSourceDataUrl("/some_source_data_url");
        videoInfo.setSourceDataUrl("/some_token_data_url");

        return new VideoInfo[]{
                videoInfo
        };
    }

}
