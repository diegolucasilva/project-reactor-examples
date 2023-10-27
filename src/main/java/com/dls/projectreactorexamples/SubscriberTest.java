package com.dls.projectreactorexamples;

public class SubscriberTest {

    public static void main(String... args){
        YoutubeChannel publisher = new YoutubeChannel();
        publisher.addVideo(new Video("Reactive Programing with Java",
                "This video talk about reactive programing...",200, 10000));
        publisher.addVideo(new Video("Java vs Kotlin",
                "This video compare the difference about Java and Kotlin...",50, 20000));

/*        User subscriber = new User("Diego");
        publisher.getAllVideos().subscribeWith(subscriber);*/

//        publisher.getAllVideos().log().subscribe();

       // publisher.getAllVideos().log().subscribe(video -> {System.out.println(video.getName());});

        publisher.getAllVideos().log().subscribe(
                video -> System.out.println(video.getName()),
                throwable -> System.out.println(throwable),
                () -> System.out.println("Dados consumidos com sucesso")
                );


    }
}
