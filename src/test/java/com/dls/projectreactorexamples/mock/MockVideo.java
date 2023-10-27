package com.dls.projectreactorexamples.mock;

import com.dls.projectreactorexamples.Video;

import java.util.Arrays;
import java.util.List;

public class MockVideo {
    public static List<Video> generateVideos(){
        return Arrays.asList(
                new Video("Reactive Programming with Java - 0", "This video talk about reactive programming...", 200, 100000),
                new Video("Java vs Kotlin - 1", "This is a compare java vs kotlin...", 50, 20000),
                new Video("Java Spring Basics - 2", "This video give you a introduction about spring...", 9, 1000),
                new Video("Reactive System vs Reactive programing- 3", "This video shows the difference between Reactive System vs Reactive programing...", 110, 40000),
                new Video("What is the best IDE? -4", "This video talk about the IDEs availability...", 500, 320000),
                new Video("WebFlux under the hoods -5", "This video talk about reactive programming...", 10000, 320000),
                new Video("Everything you need to know about java 17 - 6", "This is a compare java vs kotlin...", 2, 100));
    }

    public static List<Video> generateVideos2(){
        return Arrays.asList(
                new Video("What I need kafka? - 7", "This video talk about kafka...", 23211, 1000000),
                new Video("WebFlux pitfalls you need to know  - 8", "Webflux can be very difficult...", 321, 32211),
                new Video("ChatGPT integration with java - 9", "This video you gonna learn more about...", 21211, 1111100));

    }

    public static List<Video> generateVideos3(){
        return Arrays.asList(
                new Video("Nosql vs Sql -What I need to know? -10", "This video you clarify your...", 23211, null),
                new Video("5 tips about AWS certified -11", "If you wanna this certification...", 321, null),
                new Video("10 ways to improve my soft skills -12", "Softs skills are very important...", 21211, null));
    }
}

