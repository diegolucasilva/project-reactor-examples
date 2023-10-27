package com.dls.projectreactorexamples;

import com.dls.projectreactorexamples.mock.MockVideo;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class SchedulersTest {

    @Test
    public void blockingOperation() throws InterruptedException {
        YoutubeChannel youtubeChannel = new YoutubeChannel(MockVideo.generateVideos());

        Flux<String> videos = youtubeChannel.getAllVideos()
                .filter(video -> {
                    System.out.println("Filter1 - Thread: " + Thread.currentThread().getName());
                    return video.getDescription().length() > 10;
                })
                .map(video -> {
                    System.out.println("Map1 - Thread" + Thread.currentThread().getName());
                    return video.getDescription();
                })
                .map(description -> {
                    try {
                        Thread.sleep(1000);
                        System.out.println("Map2 - Thread " + Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return description.toUpperCase();
                });

        for (int i = 0; i < 2; i++) {
            System.out.println("Execucao " + i);
            videos.subscribe(description -> System.out.println(description));
        }
        Thread.sleep(20_000);
    }

    @Test
    public void publishOnBlockingOperation() throws InterruptedException {
        YoutubeChannel youtubeChannel = new YoutubeChannel(MockVideo.generateVideos3());

        Flux<String> videos = youtubeChannel.getAllVideos()
                .filter(video -> {
                    System.out.println("Filter1 - Thread: " + Thread.currentThread().getName());
                    return video.getDescription().length() > 10;
                })
                .map(video -> {
                    System.out.println("Map1 - Thread" + Thread.currentThread().getName());
                    return video.getDescription();
                })
                .publishOn(Schedulers.boundedElastic())
                .map(description -> {
                    try {
                        Thread.sleep(1000);
                        System.out.println("Map2 - Thread " + Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return description.toUpperCase();
                });
    //    videos.subscribe(description -> System.out.println(description));
        for (int i = 0; i < 20; i++) {
            System.out.println("Execucao " + i);
            videos.subscribe(description -> System.out.println(description));
        }
        Thread.sleep(20_000);
    }

    @Test
    public void parallelBlockingOperation() throws InterruptedException {
        YoutubeChannel youtubeChannel = new YoutubeChannel(MockVideo.generateVideos());

        VideoAnalyser videoAnalyser = new VideoAnalyser();

        youtubeChannel.getAllVideos()
                .filter(video -> {
                    System.out.println("Filter 1 - Thread "+Thread.currentThread().getName());
                    return video.getDescription().length() > 10;
                })
                .parallel().runOn(Schedulers.boundedElastic())
           //     .publishOn(Schedulers.parallel())
                .map(video -> videoAnalyser.analyseBlocking(video))
                .subscribe();

        Thread.sleep(30000);
    }

    @Test
    public void parallelPublishOnBlockingOperation() throws InterruptedException {
        YoutubeChannel youtubeChannel = new YoutubeChannel(MockVideo.generateVideos());

        VideoAnalyser videoAnalyser = new VideoAnalyser();

        youtubeChannel.getAllVideos()
                .filter(video -> {
                    System.out.println("Filter 1 - Thread "+Thread.currentThread().getName());
                    return video.getDescription().length() > 10;
                })
                .flatMap(video -> videoAnalyser.analyseBlockingMono(video))
                .subscribe();

        Thread.sleep(30000);
    }
}
