package com.dls.projectreactorexamples;

import com.dls.projectreactorexamples.mock.MockVideo;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class CombineResults {

    @Test
    public void concatVideoNames() throws InterruptedException {
        Flux<String> videoNames = new YoutubeChannel(MockVideo.generateVideos())
                .getAllVideosName().delayElements(Duration.ofSeconds(1));
        Flux<String> videoNames2 = new YoutubeChannel(MockVideo.generateVideos2()).getAllVideosName();

        //videoNames.concatWith(videoNames2).log().subscribe();
        Flux.concat(videoNames, videoNames2).log().subscribe();

        Thread.sleep(10000);
    }


    //merge
    @Test
    public void mergeVideosName() throws InterruptedException {
        Flux<String> videosNames = new YoutubeChannel(MockVideo.generateVideos()).getAllVideosName()
                .delayElements(Duration.ofMillis(500));
        Flux<String> videosNames2 = new YoutubeChannel(MockVideo.generateVideos2()).getAllVideosName();

     //   videosNames.mergeWith(videosNames2).subscribe(System.out::println);

        Flux.concat(videosNames, videosNames2).subscribe(System.out::println);

        Thread.sleep(5000);
    }


    @Test
    public void zipVideoWithMoney() throws InterruptedException {
        Flux<String> videosName = new YoutubeChannel(MockVideo.generateVideos()).getAllVideosName()
                .delayElements(Duration.ofMillis(100)).log();
        Flux<Double> monetization = Flux.just(100.0, 500.0, 400.0, 600.0)
                .delayElements(Duration.ofSeconds(1)).log();

/*        videosName.zipWith(monetization)
                .map(tuple -> tuple.getT1() + ", $ "+ tuple.getT2())
                .log()
                .subscribe();*/
        Flux.zip(videosName, monetization, videosName)
                .map(tuple -> tuple.getT1() + ", $ "+ tuple.getT2())
                .log()
                .subscribe();
        Thread.sleep(100000);

    }

}
