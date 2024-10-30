package com.dls.projectreactorexamples;

import com.dls.projectreactorexamples.mock.MockVideo;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class HotColdPublisherTest {

    @Test
    public void testColdPublisher(){
        YoutubeChannel youtubeChannel = new YoutubeChannel(MockVideo.generateVideos());

        youtubeChannel.getAllVideosName()
                .subscribe(value -> System.out.println("Subscribe 1 "+value));
        System.out.println("----------");
        youtubeChannel.getAllVideosName()
                .subscribe(value -> System.out.println("Subscribe 2 "+value));
    }

    @Test
    public void testHotPublishAutoConnect() throws InterruptedException {
        VideoLive videoLive = new VideoLive("Meetup about java 21");

        Flux<String> java21Live = videoLive.play();

        java21Live.subscribe(value -> System.out.println("User 1 "+value));

        Thread.sleep(2000);

        java21Live.subscribe(value -> System.out.println("User 2 "+ value));

        Thread.sleep(4000);

        java21Live.subscribe(value -> System.out.println("User 3 "+ value));

        Thread.sleep(20_0000);
    }

    @Test
    public void testHotPublishAutoConnectN() throws InterruptedException {
        VideoLive videoLive = new VideoLive("Meetup about java 21");

        Flux<String> java21Live = videoLive.playN();

        Thread.sleep(2000);

        java21Live.subscribe(value -> System.out.println("User 1 "+value));

        Thread.sleep(2000);

        java21Live.subscribe(value -> System.out.println("User 2 "+ value));

        Thread.sleep(4000);

        java21Live.subscribe(value -> System.out.println("User 3 "+ value));

        Thread.sleep(20_0000);
    }


    @Test
    public void testHotPublishReSubscription() throws InterruptedException {
        VideoLive videoLive = new VideoLive("Meetup about java 21");

        Flux<String> java21Live = videoLive.playResubscription();

        Thread.sleep(2000);

        java21Live.subscribe(value -> System.out.println("User 1 "+value));

        Thread.sleep(2000);

        java21Live.subscribe(value -> System.out.println("User 2 "+ value));

        Thread.sleep(4000);

        java21Live.subscribe(value -> System.out.println("User 3 "+ value));

        Thread.sleep(20_0000);
    }



}
