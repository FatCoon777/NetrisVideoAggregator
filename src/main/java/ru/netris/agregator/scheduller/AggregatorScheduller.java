package ru.netris.agregator.scheduller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.netris.agregator.components.AggregateData;
import ru.netris.agregator.components.VideoInfo;
import ru.netris.agregator.components.VideoSource;
import ru.netris.agregator.components.VideoToken;
import ru.netris.agregator.loaders.VideoInfoLoader;
import ru.netris.agregator.loaders.VideoSourceLoader;
import ru.netris.agregator.loaders.VideoTokenLoader;
import ru.netris.agregator.services.AggregateDataService;

import javax.annotation.PreDestroy;
import java.util.List;
import java.util.concurrent.*;

@Service
public class AggregatorScheduller {
    private final ExecutorService executor;
    private final ExecutorService loadersExecutor;

    private final VideoInfoLoader videoInfoLoader;
    private final VideoSourceLoader videoSourceLoader;
    private final VideoTokenLoader videoTokenLoader;

    private final AggregateDataService aggregateDataService;

    public AggregatorScheduller(@Value("${aggregator.poolCount}") Integer poolCount,
                                @Value("${aggregator.loaders.poolCount}") Integer loadersPoolCount,
                                VideoInfoLoader videoInfoLoader,
                                VideoSourceLoader videoSourceLoader,
                                VideoTokenLoader videoTokenLoader,
                                AggregateDataService aggregateDataService) {
        executor = Executors.newFixedThreadPool(poolCount);
        loadersExecutor = Executors.newFixedThreadPool(loadersPoolCount);
        this.videoInfoLoader = videoInfoLoader;
        this.videoSourceLoader = videoSourceLoader;
        this.videoTokenLoader = videoTokenLoader;
        this.aggregateDataService = aggregateDataService;
    }


    @Scheduled(fixedRateString = "${aggregator.delay.period.minutes}", timeUnit = TimeUnit.MINUTES)
    public void schedule() {
        try {
            List<VideoInfo> videoInfoList = videoInfoLoader.loadVideoCams();
            final int size = videoInfoList.size();
            aggregateDataService.startBuildingCashe(size);
            final CountDownLatch countDownLatch = new CountDownLatch(size);
            for (int i = 0; i < size; i++) {
                final int index = i;
                final VideoInfo videoInfo = videoInfoList.get(index);
                executor.submit(() -> {
                    try {
                        Future<VideoSource> videoSourceFuture = loadersExecutor.submit(() -> videoSourceLoader.load(videoInfo.getSourceDataUrl()));
                        Future<VideoToken> videoTokenFuture = loadersExecutor.submit(() -> videoTokenLoader.load(videoInfo.getTokenDataUrl()));

                        VideoSource videoSource = videoSourceFuture.get();
                        VideoToken videoToken = videoTokenFuture.get();

                        AggregateData aggregateData = new AggregateData();
                        aggregateData.setId(videoInfo.getId());
                        aggregateData.setUrlType(videoSource.getUrlType());
                        aggregateData.setVideoUrl(videoSource.getVideoUrl());
                        aggregateData.setValue(videoToken.getValue());
                        aggregateData.setTtl(videoToken.getTtl());

                        aggregateDataService.add(index, aggregateData);

                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        countDownLatch.countDown();
                    }
                });
            }

            countDownLatch.await();
            aggregateDataService.stopBuildingCashe();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void shutdown() {
        executor.shutdown();
        loadersExecutor.shutdown();
    }
}
