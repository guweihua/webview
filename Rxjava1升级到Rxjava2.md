
# Rxjava1升级到Rxjava

      > 作者 顾卫化
    
  1. RxJava 2.0 VS RxJava 1.0
   
      2.1 RxJava 2.0 所有的函数接口(Function/Action/Consumer)均设计为可抛出Exception，解决编译异常需要转换问题；
      
      2.2 RxJava 2.0 不再支持 null 值，如果传入一个null会抛出 NullPointerException；
      
      2.3 RxJava 1.0 中Observable不能很好支持背压，在RxJava2.0 中将Oberservable彻底实现成不支持背压，而新增Flowable 来支持背压。
      
      2.4  Rxjava 2.0开始支持Reactive Streams。
   
       
  2. Observable
  
      RxJava 1.0有四个基本概念：Observable(可观察者，即被观察者)、Observer(观察者)、subscribe(订阅)、事件。Observable和 Observer通过 subscribe()方法实现订阅关系，从而 Observable可以在需要的时候发出事件来通知 Observer。
      基于以上的概念， RxJava 1.0的基本实现主要有三点：
      
      step1: 创建 Observer
      
      Observer 即观察者，它决定事件触发的时候将有怎样的行为。
      
      ```
      Observer<String> observer = new Observer<String>() {
          @Override
          public void onNext(String s) {
              Log.d(tag, "Item: " + s);
          }
      
          @Override
          public void onCompleted() {
              Log.d(tag, "Completed!");
          }
      
          @Override
          public void onError(Throwable e) {
              Log.d(tag, "Error!");
          }
      };
      
       ```
       
       除了Observer接口之外，RxJava 还内置了一个实现了Observer的抽象类: Subscriber。Subscriber对 Observer接口进行了一些扩展，但他们的基本使用方式是完全一样的：
       
       ```
           Subscriber<String> subscriber = new Subscriber<String>() {
               @Override
               public void onNext(String s) {
                   Log.d(tag, "Item: " + s);
               }
           
               @Override
               public void onCompleted() {
                   Log.d(tag, "Completed!");
               }
           
               @Override
               public void onError(Throwable e) {
                   Log.d(tag, "Error!");
               }
           };
       ```
      
      step2：创建 Observable
      
      Observable 即被观察者，它决定什么时候触发事件以及触发怎样的事件
      
       ```
           Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
               @Override
               public void call(Subscriber<? super String> subscriber) {
                   subscriber.onNext("Hello");
                   subscriber.onNext("Hi");
                   subscriber.onNext("Aloha");
                   subscriber.onCompleted();
               }
           });
        ```
      
      step3：Subscribe (订阅)
      
      创建了 Observable和 Observer之后，再用 subscrbe() 方法将它们联结起来，整条链子就可以工作了。
      
      ```
          observable.subscribe(observer);
          // 或者：
          observable.subscribe(subscriber);
      ```

      
  3. ObservableEmitter
      
      然而，在2.0中我们熟悉的 Subscrber 居然没影了，取而代之的是 ObservableEmitter， 俗称发射器。
      使用步骤
      
      step1：初始化一个Observable
      
      step2：初始化一个Observer
      
      step3：建立订阅关系
      
  4. Flowable
  
      Flowable是RxJava 2.0中新增的类，专门用于应对背压（Backpressure）问题，但这并不是RxJava 2.0中新引入的概念。所谓背压，即生产者的速度大于消费者的速度带来的问题，比如在Android中常见的点击事件，点击过快则会造成点击两次的效果。 
      
      我们知道，在RxJava 1.0中背压控制是由Observable完成的，使用如下：
      
      
    ```js
    Observable.range(1,10000)
                .onBackpressureDrop()
                .subscribe(integer -> Log.d("JG",integer.toString()));
    ```
    
   而在RxJava 2.0中将其独立了出来，取名为Flowable。因此，原先的Observable已经不具备背压处理能力。 
   
   