package com.dls.projectreactorexamples;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Random;

public class VideoAnalyser {

    Double analyse(Video video){
        Double rate = new Random().doubles(1, 15).findFirst().getAsDouble();
        System.out.println(video.getName() +" rate "+ rate);
        if(rate > 10)
            throw  new RuntimeException("An unexpected error occurred");

        return rate;
    }

    Double analyseBlocking(Video video){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Double rate = new Random().doubles(1, 10).findFirst().getAsDouble();
        System.out.println(video.getName() +" rate "+ rate+ " Thread "+Thread.currentThread().getName());
        if(rate > 10)
            throw  new RuntimeException("An unexpected error occurred");

        return rate;
    }

    Mono<Double> analyseBlockingMono(Video video){
        return Mono.fromCallable(() -> analyseBlocking(video))
                .publishOn(Schedulers.boundedElastic());
    }







}
