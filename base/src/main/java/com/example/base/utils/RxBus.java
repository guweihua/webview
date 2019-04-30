package com.example.base.utils;


import java.io.Serializable;

import io.reactivex.Observable;
import io.reactivex.observers.SerializedObserver;
import io.reactivex.subjects.PublishSubject;

/**
 * RxBus
 * Created by YoKeyword on 2015/6/17.
 * <p>
 * Github:https://github.com/YoKeyword/RxBus
 */
public class RxBus {

    public static volatile RxBus instance;
    // 主题
    private final SerializedObserver<Object> objectSerializedObserver;

    // PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
    public RxBus() {
        objectSerializedObserver = new SerializedObserver<>(PublishSubject.create());

//        bus = new SerializedSubject<>(PublishSubject.create());
    }

    // 单例RxBus
    public static RxBus getDefault() {
        if (instance == null) {
            synchronized (RxBus.class) {
                if (instance == null) {
                    instance = new RxBus();
                }
            }
        }
        return instance;
    }

    // 提供了一个新的事件
    public void post(Object o) {
        objectSerializedObserver.onNext(o);
    }


}
