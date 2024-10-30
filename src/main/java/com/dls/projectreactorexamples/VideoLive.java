package com.dls.projectreactorexamples;

import reactor.core.publisher.Flux;

import java.time.Duration;

public class VideoLive {
    private String title;

    public VideoLive(String title){
        this.title = title;
    }

    public Flux<String> play(){
        return Flux.interval(Duration.ofMillis(500))
                .map(value -> getLiveEvent(value))
                .takeWhile(event -> !event.equals("End"))
                .publish().autoConnect();
    }

    public Flux<String> playN() {
        return Flux.interval(Duration.ofMillis(500))
                .map(value -> getLiveEvent(value))
                .takeWhile(event -> !event.equals("End"))
                .publish().autoConnect(2);
    }

    public Flux<String> playResubscription() {
        return Flux.interval(Duration.ofMillis(500))
                .map(value -> getLiveEvent(value))
                .takeWhile(event -> !event.equals("End"))
                .share();
    }


    private String getLiveEvent(Long sequence){
        switch (sequence.intValue()) {
            case 0:
                return "🟢 Live stream starts";
            case 1:
                return "⚡️ New feature announced!...";
            case 2:
                return "💬 Live chat...";
            case 3:
                return "🎉 Giveaways...";
            case 4:
                return "⏰ Next event announced!...";
            case 7:
                return "⏰ Our Live stream has come to an end!";
            case 8:
                return "End";
            default:
                return "⌛️ In progress...";
        }
    }
}
