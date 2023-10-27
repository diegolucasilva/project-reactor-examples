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
                .takeWhile(event -> !event.equals("Fim"))
                .publish().autoConnect();
    }

    public Flux<String> playN() {
        return Flux.interval(Duration.ofMillis(500))
                .map(value -> getLiveEvent(value))
                .takeWhile(event -> !event.equals("Fim"))
                .publish().autoConnect(2);
    }

    public Flux<String> playResubscription() {
        return Flux.interval(Duration.ofMillis(500))
                .map(value -> getLiveEvent(value))
                .takeWhile(event -> !event.equals("Fim"))
                .share();
    }


    private String getLiveEvent(Long sequence){
        switch (sequence.intValue()) {
            case 0:
                return "🟢 Início da live";
            case 1:
                return "⚡️ Novo recurso anunciado!...";
            case 2:
                return "💬 Bate-papo ao vivo...";
            case 3:
                return "🎉 Sorteio de brindes...";
            case 4:
                return "⏰ Próximo evento anunciado!...";
            case 7:
                return "⏰ Nossa Live chegou ao fim!";
            case 8:
                return "Fim";
            default:
                return "⌛️ Em andamento...";
        }
    }



}
