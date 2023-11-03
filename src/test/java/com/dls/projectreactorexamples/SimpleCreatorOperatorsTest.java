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
}
