package com.dls.projectreactorexamples;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.Properties;

public class BackPressureTest {

    @Test
    public void backPressureBuffer() throws InterruptedException {

        System.setProperty("reactor.bufferSize.small", "16");

        Flux.interval(Duration.ofMillis(1))
                .onBackpressureBuffer(20)
                .log("filter")
                .publishOn(Schedulers.single())
                .map(number -> {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(Thread.currentThread().getName()+ " Consuming number "+number);
                    return number;
                })
                .subscribe();

        Thread.sleep(50_0000);
    }

    @Test
    public void backPressureError() throws InterruptedException {

        System.setProperty("reactor.bufferSize.small", "16");

        Flux.interval(Duration.ofMillis(1))
                .onBackpressureError()
                .publishOn(Schedulers.single())
                .map(number -> {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(Thread.currentThread().getName()+ " Consuming number "+number);
                    return number;
                })
                .subscribe();

        Thread.sleep(50_0000);
    }

    @Test
    public void backPressureDrop() throws InterruptedException {

        System.setProperty("reactor.bufferSize.small", "16");

        Flux.interval(Duration.ofMillis(1))
                .onBackpressureDrop()
                .publishOn(Schedulers.single())
                .map(number -> {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(Thread.currentThread().getName()+ " Consuming number "+number);
                    return number;
                })
                .subscribe();

        Thread.sleep(50_0000);
    }

    @Test
    public void backPressureLatest() throws InterruptedException {

        System.setProperty("reactor.bufferSize.small", "16");

        Flux.interval(Duration.ofMillis(1))
                .onBackpressureLatest()
                .publishOn(Schedulers.single())
                .map(number -> {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(Thread.currentThread().getName()+ " Consuming number "+number);
                    return number;
                })
                .subscribe();

        Thread.sleep(50_0000);
    }

    @Test
    public void createOperatorBackPressureStrategies() throws InterruptedException {
        System.setProperty("reactor.bufferSize.small", "16");

        Flux<Object> fluxTest = Flux.create(emitter -> {
            for(int i = 0; i < 10_00000; i++){
                emitter.next(i);
            }
            emitter.complete();
        }, FluxSink.OverflowStrategy.LATEST);


        fluxTest
                .publishOn(Schedulers.boundedElastic())
                .map(number -> {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(Thread.currentThread().getName()+ " Consuming number "+number);
                    return number;
                })
                .subscribe();

        Thread.sleep(10_00000);
    }


}
