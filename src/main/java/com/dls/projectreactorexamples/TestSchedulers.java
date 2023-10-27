package com.dls.projectreactorexamples;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.Arrays;
import java.util.List;

public class TestSchedulers {

    public static void main(String... args) throws InterruptedException {
        YoutubeChannel youtubeChannel = new YoutubeChannel(generateVideos());

        Flux<String> videos =  youtubeChannel.getAllVideos()
                .filter(video ->{
                    System.out.println(" Filter1 - Thread: " + Thread.currentThread().getName());
                    return video.getDescription().length()>10;
                })
                .subscribeOn(Schedulers.boundedElastic())
                .map(video -> {
                    System.out.println("Map1 - Thread: " + Thread.currentThread().getName());
                    return video.getDescription();
                })
                .map(description -> {
                    try {
                        Thread.sleep(5000);
                        System.out.println("Map2 - Thread: " + Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return description.toUpperCase();
                });

        for(int i = 0; i < 2; i++){
            System.out.println("#### Execucao "+i);
            videos.subscribe(System.out::println);
        }

        Thread.sleep(20000);

    }
    public static List<Video> generateVideos(){
        return Arrays.asList(
                new Video("Reactive Programming with Java - 0", "This video talk about reactive programming...", 200, 100000),
                new Video("Java vs Kotlin - 1", "This is a compare java vs kotlin...", 50, 20000),
                new Video("Java Spring Basics - 2", "This video give you a introduction about spring...", 10, 1000), //takewhile,nao alterar esse
                new Video("Reactive System vs Reactive programing- 3", "This video shows the difference between Reactive System vs Reactive programing...", 110, 40000),
                new Video("What is the best IDE? -4", "This video talk about the IDEs availability...", 500, 320000),
                new Video("WebFlux under the hoods -5", "This video talk about reactive programming...", 10000, 320000),
                new Video("Everything you need to know about java 17 - 6", "This is a compare java vs kotlin...", 2, 100));
    }


}