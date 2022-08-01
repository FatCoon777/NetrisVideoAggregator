package ru.agregator.scheduller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.agregator.components.AggregateData;
import ru.agregator.components.VideoInfo;
import ru.agregator.components.VideoSource;
import ru.agregator.components.VideoToken;
import ru.agregator.loaders.VideoInfoLoader;
import ru.agregator.loaders.VideoSourceLoader;
import ru.agregator.loaders.VideoTokenLoader;
import ru.agregator.services.AggregateDataService;
import ru.metrics.services.MetricRegistry;

import javax.annotation.PreDestroy;
import java.util.concurrent.*;

@Service
public class AggregatorScheduller {
    private final ExecutorService executor;
    private final ExecutorService loadersExecutor;

    private final VideoInfoLoader videoInfoLoader;
    private final VideoSourceLoader videoSourceLoader;
    private final VideoTokenLoader videoTokenLoader;

    private final AggregateDataService aggregateDataService;
    private final MetricRegistry metricRegistry;

    public AggregatorScheduller(@Value("${aggregator.poolCount}") Integer poolCount,
                                @Value("${aggregator.loaders.poolCount}") Integer loadersPoolCount,
                                VideoInfoLoader videoInfoLoader,
                                VideoSourceLoader videoSourceLoader,
                                VideoTokenLoader videoTokenLoader,
                                AggregateDataService aggregateDataService,
                                MetricRegistry metricRegistry) {
        executor = Executors.newFixedThreadPool(poolCount);
        loadersExecutor = Executors.newFixedThreadPool(loadersPoolCount);
        this.videoInfoLoader = videoInfoLoader;
        this.videoSourceLoader = videoSourceLoader;
        this.videoTokenLoader = videoTokenLoader;
        this.aggregateDataService = aggregateDataService;
        this.metricRegistry = metricRegistry;
    }


    @Scheduled(fixedRateString = "${aggregator.delay.period.minutes}", timeUnit = TimeUnit.MINUTES)
    public void schedule() {
        try {
            VideoInfo[] videoInfoList = videoInfoLoader.load();
            final int size = videoInfoList.length;
            aggregateDataService.startBuildingCashe(size);
            final CountDownLatch countDownLatch = new CountDownLatch(size);
            for (int i = 0; i < size; i++) {
                final int index = i;
                final VideoInfo videoInfo = videoInfoList[index];
                executor.execute(() -> {
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
                        metricRegistry.applayErrorCount();
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
