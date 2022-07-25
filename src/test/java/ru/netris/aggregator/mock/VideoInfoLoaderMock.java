package ru.netris.aggregator.mock;

import org.mockito.Mockito;
import ru.netris.agregator.components.VideoInfo;
import ru.netris.agregator.loaders.VideoInfoLoader;

import java.util.ArrayList;
import java.util.List;

public class VideoInfoLoaderMock {
    public static VideoInfoLoader create() {
        VideoInfoLoader mock = Mockito.mock(VideoInfoLoader.class);
        Mockito.when(mock.loadVideoCams()).thenReturn(getResultValue());
        return mock;
    }

    private static List<VideoInfo> getResultValue() {
        List<VideoInfo> list = new ArrayList<>(0);
        VideoInfo videoInfo = new VideoInfo();
        videoInfo.setId(112L);
        videoInfo.setSourceDataUrl("/some_source_data_url");
        videoInfo.setSourceDataUrl("/some_token_data_url");
        list.add(videoInfo);
        return list;
    }

}
