package com.dls.projectreactorexamples;


import com.dls.projectreactorexamples.mock.MockVideo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.mockito.Mock;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class OperatorsTest {

    @Test
    public void printVideos() {
        YoutubeChannel youtubeChannel = new YoutubeChannel(MockVideo.generateVideos());

        youtubeChannel
                .getAllVideos().log()
                .subscribe(video -> System.out.println(video.getName()));
    }

    @Test
    public void printVidesTake() {
        YoutubeChannel youtubeChannel = new YoutubeChannel(MockVideo.generateVideos());

        youtubeChannel
                .getAllVideos(2)
                .log("second take")
                .subscribe(video -> System.out.println(video.getName()));
    }

    @Test
    public void printVideosTakeWhile() {
        YoutubeChannel youtubeChannel = new YoutubeChannel(MockVideo.generateVideos());

        youtubeChannel
                .getAllVideos().log()
                .takeWhile(video -> video.getLikes() > 10)
                .subscribe(video -> System.out.println(video.getName()));
    }

    @Test
    public void printDescriptionSize() {
        YoutubeChannel youtubeChannel = new YoutubeChannel(MockVideo.generateVideos());
        youtubeChannel
                .getAllVideosDescriptionSize().log()
                .subscribe(System.out::println);
    }

    @Test
    public void printVideoNames() {
        YoutubeChannel youtubeChannel = new YoutubeChannel(MockVideo.generateVideos());
        youtubeChannel
                .getAllVideosName().log()
                .subscribe();
    }


    @Test
    public void badFlatMap() {
        List<YoutubeChannel> channelList = Arrays.asList(
                new YoutubeChannel(MockVideo.generateVideos()),
                new YoutubeChannel(MockVideo.generateVideos2())
        );

        Flux<YoutubeChannel> channelFlux = Flux.fromIterable(channelList);

        channelFlux.map(channel -> channel.getAllVideosName()).log()
                .subscribe(item -> System.out.println(item));
    }

    @Test
    public void flatMapVideosName() {
        List<YoutubeChannel> channelList = Arrays.asList(
                new YoutubeChannel(MockVideo.generateVideos()),
                new YoutubeChannel(MockVideo.generateVideos2())
        );

        Flux<YoutubeChannel> channelFlux = Flux.fromIterable(channelList);

        channelFlux.flatMap(channel -> channel.getAllVideosName()).log()
                .subscribe();
    }

    @Test
    public void testFlatMapLike() {
        List<Video> videos = MockVideo.generateVideos3();
        YoutubeChannel youtubeChannel = new YoutubeChannel(videos);

        Flux<Integer> videoFlux = youtubeChannel
                .getAllVideos()
                .flatMap(video -> video.like())
                .map(video -> video.getLikes());

        StepVerifier
                .create(videoFlux)
                .expectNext(videos.get(0).getLikes() + 1,
                        videos.get(1).getLikes() + 1,
                        videos.get(2).getLikes() + 1)
                .verifyComplete();
    }

    @Test
    public void filterByRating() {
        YoutubeChannel youtubeChannel = new YoutubeChannel(MockVideo.generateVideos());

        youtubeChannel
                .getVideosByRating(100)
                .subscribe(video -> System.out.println(video.getName()));
    }

    @Test
    public void testPrintVideosWithDelay() {
        YoutubeChannel youtubeChannel = new YoutubeChannel(MockVideo.generateVideos2());

        Flux<String> channel = youtubeChannel
                .getAllVideosName()
                .log()
                .delayElements(Duration.ofSeconds(2));

        StepVerifier
                .create(channel)
                .expectNext("What I need kafka? - 7", "WebFlux pitfalls you need to know  - 8",
                        "ChatGPT integration with java - 9")
                .verifyComplete();
    }

    @Test
    public void printVideosWithDelay() throws InterruptedException {
        YoutubeChannel youtubeChannel = new YoutubeChannel(MockVideo.generateVideos2());

        youtubeChannel
                .getAllVideosName()
                .log()
                .delayElements(Duration.ofSeconds(2))
                .subscribe();

        Thread.sleep(5000);
    }

    @Test
    public void simpleTransform() {
        Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .filter(num -> num % 2 == 0)
                .transform(squareNumber())
                .subscribe(result -> System.out.println(" =" + result));

        System.out.println("");

        Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .filter(num -> num % 2 != 0)
                .transform(squareNumber())
                .subscribe(result -> System.out.println(" =" + result));

    }

    Function<Flux<Integer>, Flux<Integer>> squareNumber() {
        return flux -> flux
                .doOnNext(num -> System.out.print("Square of " + num))
                .map(num -> num * num);
    }

    @Test
    public void transformExample() {
        YoutubeChannel youtubeChannel = new YoutubeChannel(MockVideo.generateVideos());

        youtubeChannel
                .getAllVideos()
                .transform(transformMethod())
                .subscribe(videoName -> System.out.println(videoName));
    }

    Function<Flux<Video>, Flux<String>> transformMethod() {
        return flux -> flux.filter(video -> video.getLikes() > 100)
                .map(video -> video.getName())
                .map(videoName -> videoName.toUpperCase());

    }

    @Test
    public void sideEffects() {
        YoutubeChannel youtubeChannel = new YoutubeChannel(MockVideo.generateVideos());

        youtubeChannel.getAllVideosName().log()
                .doOnComplete(() -> System.out.println("doOnComplete "))
                .doFinally(signalType -> System.out.println("doFinally " + signalType))
                .doAfterTerminate(() -> System.out.println("doAfterTerminate"))
                .subscribe();

    }
}

