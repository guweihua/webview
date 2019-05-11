package com.example.weihuagu.myprogram.inter;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.observers.DisposableObserver;

public abstract class  SB<T> extends DisposableObserver<T> {



    @Override
    public void onNext(Object o) {
        next((Res)o);
    }

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onComplete() {

    }

    public abstract void next(Res res);
}
