package com.dls.projectreactorexamples;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.test.StepVerifier;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class CreateOperatorTest {

    @Test
    public void testFileReadingCreate(){
        String filePath = "/Users/diegolucas/Downloads/project-reactor-examples/src/test/resources/example.txt";
        String filePath2 = "/Users/diegolucas/Downloads/project-reactor-examples/src/test/resources/example2.txt";

        Flux fileFlux = Flux.create(emitter ->{
            CompletableFuture<Void> task1 = CompletableFuture.runAsync(() -> readFileFluxSink(emitter, filePath));
            CompletableFuture<Void> task2 = CompletableFuture.runAsync(() -> readFileFluxSink(emitter, filePath2));
            CompletableFuture.allOf(task1, task2).join();

            emitter.complete();
        }).log();

        StepVerifier.create(fileFlux)
                .expectNextCount(6)
                .expectComplete()
                .verify();


    }

    private static void readFileFluxSink(FluxSink<Object> emitter, String filePath) {
        System.out.println("filePath "+filePath + " Thread "+Thread.currentThread().getName());
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;
            while((line= reader.readLine()) != null){
                emitter.next(line);
            }
        }
        catch (IOException exception){
            emitter.error(exception);
        }
    }


}
