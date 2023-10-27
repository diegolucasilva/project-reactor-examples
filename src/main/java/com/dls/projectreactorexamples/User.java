package com.dls.projectreactorexamples;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class User implements Subscriber<Video> {
    private String name;


    public User(String name) {
        this.name = name;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        System.out.println("onSubscribe("+name+")");
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(Video item) {
        System.out.println("onNext(" +item.getName() +")");
    }

    @Override
    public void onComplete() {
        System.out.println("onComplete()");
    }

    @Override
    public void onError(Throwable throwable) {
        System.err.println("Error: " + throwable.getMessage());
    }
}
