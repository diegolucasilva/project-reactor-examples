package com.dls.projectreactorexamples;

import reactor.core.publisher.Mono;

import java.util.Random;

public class MonetizationCalculator {

    public Mono<Double> calculate(Video video){
        System.out.println(video.getName()+ " views "+ video.getViews());

        if(video.getViews() == null){
            return Mono.empty();
        }
         if(video.getViews() < 10000){
            throw new RuntimeException();
        }
        return Mono.just(new Random().doubles(0, 2000).findFirst().getAsDouble());
    }
}
