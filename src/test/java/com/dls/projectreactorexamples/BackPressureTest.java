package com.dls.projectreactorexamples;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

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
                    System.out.println(Thread.currentThread().getName()+ " Consumindo numero "+number);
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
                    System.out.println(Thread.currentThread().getName()+ " Consumindo numero "+number);
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
                    System.out.println(Thread.currentThread().getName()+ " Consumindo numero "+number);
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
                    System.out.println(Thread.currentThread().getName()+ " Consumindo numero "+number);
                    return number;
                })
                .subscribe();

        Thread.sleep(50_0000);
    }

    @Test
    public void testBackPressureFluxCreate() throws InterruptedException {

  /*      Properties properties = new Properties();
        properties.setProperty("reactor.bufferSize.small", "16");
        System.setProperties(properties);
        //nao funciona no default do create, que é buffer, esse exemplo nao sera mostrado, somente o seguir
        */

        Flux<Object> fluxTest = Flux.create(emitter -> {
            for (int i = 0; i < 50; i++) {
                System.out.println(Thread.currentThread().getName() + " | Publishing = " + i);
                emitter.next(i);
            }
            emitter.complete();
        });

        fluxTest
                .publishOn(Schedulers.boundedElastic())
                .doOnError(System.out::println)
                .subscribe(i -> {
                    try {
                        System.out.println(Thread.currentThread().getName() + " | Received = " + i);
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }});
        Thread.sleep(100000);
    }

}
