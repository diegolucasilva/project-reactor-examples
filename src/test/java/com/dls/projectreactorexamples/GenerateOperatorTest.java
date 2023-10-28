package com.dls.projectreactorexamples;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.io.*;

public class GenerateOperatorTest {

    @Test
    public void testFileReadingGenerate(){
        String filePath = "/Users/diegolucas/Downloads/project-reactor-examples/src/test/resources/example.txt";

        Flux fileFlux = Flux.generate(
                () -> {
                    try {
                        System.out.println("creating bufferedReader");
                        return new BufferedReader(new FileReader(filePath));
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                },
                ((bufferedReader, synchronousSink) -> {
                    try {
                        String line = bufferedReader.readLine();
                        if(line !=null){
                            synchronousSink.next(line);
                        }else
                            synchronousSink.complete();
                    } catch (IOException exception) {
                        synchronousSink.error(exception);
                    }
                    return bufferedReader;
                }),
                bufferedReader -> {
                    try {
                        System.out.println("End - closing file");
                        bufferedReader.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        ).log();

        StepVerifier.create(fileFlux)
                .expectNext("Line 1")
                .expectNext("Line 2")
                .expectNext("Line 3")
                .expectComplete()
                .verify();

    }
}
