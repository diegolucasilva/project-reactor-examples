package com.dls.projectreactorexamples;

import com.dls.projectreactorexamples.mock.MockVideo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

import java.time.Duration;

public class ErrorHandlerTest {

    @Test
    public void onErrorReturnMonetization() {
        YoutubeChannel youtubeChannel = new YoutubeChannel(MockVideo.generateVideos());

        MonetizationCalculator monetizationCalculator = new MonetizationCalculator();

        youtubeChannel.getAllVideos()
                .flatMap(video -> monetizationCalculator.calculate(video))
                .onErrorReturn(0.0)
                .subscribe(value -> System.out.println("$ " + value));
    }

    @Test
    public void onErrorResumeMonetization(){
        YoutubeChannel youtubeChannel = new YoutubeChannel(MockVideo.generateVideos());

        MonetizationCalculator monetizationCalculator = new MonetizationCalculator();

        youtubeChannel.getAllVideos()
                .flatMap(video -> monetizationCalculator.calculate(video))
                .onErrorResume(exception ->{
                    System.out.println("onErrorResume");
                    return Flux.just(0.0, 999.00);
                })
                .subscribe(value -> System.out.println(value));
    }

    @Test
    public void onErrorContinueMonetization(){
        YoutubeChannel youtubeChannel = new YoutubeChannel(MockVideo.generateVideos());

        MonetizationCalculator monetizationCalculator = new MonetizationCalculator();

        youtubeChannel.getAllVideos()
                .flatMap(video ->  monetizationCalculator.calculate(video))
                .onErrorContinue((throwable, object) -> {
                    Video video = (Video) object;
                    System.out.println("onErrorContinue "+ video.getName());
                })
                .subscribe(value -> System.out.println(value));
    }

    @Test
    public void onErrorMapMonetization(){
        YoutubeChannel youtubeChannel = new YoutubeChannel(MockVideo.generateVideos());

        MonetizationCalculator monetizationCalculator = new MonetizationCalculator();

        youtubeChannel.getAllVideos()
                .flatMap(video -> monetizationCalculator.calculate(video))
                .subscribe(value -> System.out.println(value));
    }

    @Test
    public void onErrorCompleteMonetization(){
        YoutubeChannel youtubeChannel = new YoutubeChannel(MockVideo.generateVideos());

        MonetizationCalculator monetizationCalculator = new MonetizationCalculator();

        youtubeChannel.getAllVideos()
                .flatMap(video -> monetizationCalculator.calculate(video))
                .onErrorComplete()
                .doFinally(signalType -> System.out.println("Sinal "+ signalType))
                .subscribe(value -> System.out.println(value));
    }

    @Test
    public void isEmptyMonetization(){
        YoutubeChannel youtubeChannel = new YoutubeChannel(MockVideo.generateVideos3());

        MonetizationCalculator monetizationCalculator = new MonetizationCalculator();

        youtubeChannel.getAllVideos()
                .flatMap(video ->  monetizationCalculator.calculate(video))
                .switchIfEmpty(Flux.just(0.0, 1.0))
                .subscribe(value -> System.out.println(value));
    }

    @Test
    public void retryMonetization() throws InterruptedException {
        YoutubeChannel youtubeChannel = new YoutubeChannel(MockVideo.generateVideos());

        VideoAnalyser videoAnalyser = new VideoAnalyser();

        youtubeChannel.getAllVideos()
                .log()
                .map(video -> videoAnalyser.analyse(video))
                .retryWhen(Retry.fixedDelay(2, Duration.ofSeconds(2)))
                .subscribe();

        Thread.sleep(10000);
    }


}
