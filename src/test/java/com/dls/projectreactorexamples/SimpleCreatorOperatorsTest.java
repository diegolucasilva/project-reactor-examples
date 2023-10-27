package com.dls.projectreactorexamples;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.List;
import java.util.stream.Stream;

public class SimpleCreatorOperatorsTest {

    @Test
    public void fluxJust() {
        Flux simpleFlux = Flux.just(1, 2, 3, 4).delayElements(Duration.ofMillis(2000)).log();

        StepVerifier
                .create(simpleFlux)
                .expectNext(1, 2, 3, 4)
                .verifyComplete();
    }

    @Test
    public void fluxFromIterable(){
        Flux simpleFlux = Flux.fromIterable(List.of(1, 2, 3, 4)).log();

        StepVerifier
                .create(simpleFlux)
                .expectNext(1, 2, 3, 4)
                .verifyComplete();
    }

    @Test
    public void fluxFromArray(){
        Flux simpleFlux = Flux.fromArray(new Integer[]{1, 2, 3, 4}).log();

        StepVerifier
                .create(simpleFlux)
                .expectNext(1, 2, 3, 4)
                .verifyComplete();
    }

    @Test
    public void fluxFromStream() {
        Flux simpleFlux = Flux.fromStream(Stream.of(1, 2, 3, 4)).log();

        StepVerifier
                .create(simpleFlux)
                .expectNext(1, 2, 3, 4)
                .verifyComplete();
    }

    @Test
    public void fluxRange() {
        Flux simpleFlux = Flux.range(1,4).log();

        StepVerifier
                .create(simpleFlux)
                .expectNext(1, 2, 3, 4)
                .verifyComplete();
    }

    @Test
    public void monoJust(){
        Mono monoJust = Mono.just(1).log();

        StepVerifier
                .create(monoJust)
                .expectNext(1)
                .verifyComplete();

    }


    @Test
    public void test() throws InterruptedException {
        Flux<Object> fluxSync = Flux.create(emitter -> {

            // Publish 100 numbers
            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + " | Publishing = " + i);
                // Publish or emit a value with 10 ms delay
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                emitter.next(i);
            }
            // When all values or emitted, call complete.
            emitter.complete();
        }).subscribeOn(Schedulers.boundedElastic()).publishOn(Schedulers.boundedElastic());

        /*
         * Notice above - subscribeOn & publishOn puts subscriber & publisher/flux on
         * different threads.
         */

        fluxSync.subscribe(i -> {
            // Process received value.
            System.out.println(Thread.currentThread().getName() + " | Received = " + i);
            // 100 mills delay to simulate slow subscriber
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Since publisher & subscriber run on different thread than main thread, keep
        // main thread active for 5 seconds.
        Thread.sleep(5000);
    }










}
