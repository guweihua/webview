
## Rxjava1升级到Rxjava

      > 作者 顾卫化
    
  1. RxJava 2.0 VS RxJava 1.0
   
      2.1 RxJava 2.0 所有的函数接口(Function/Action/Consumer)均设计为可抛出Exception，解决编译异常需要转换问题；
      
      2.2 RxJava 2.0 不再支持 null 值，如果传入一个null会抛出 NullPointerException；
      
      2.3 RxJava 1.0 中Observable不能很好支持背压，在RxJava2.0 中将Oberservable彻底实现成不支持背压，而新增Flowable 来支持背压。
      
      2.4  Rxjava 2.0开始支持Reactive Streams。
   
       
  2. Observable
  
      2.1 RxJava 1.0
  
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

      2.2 ObservableEmitter
      
      然而，在2.0中我们熟悉的 Subscrber 居然没影了，取而代之的是 ObservableEmitter， 俗称发射器。
      使用步骤
      
      step1：初始化一个Observable
      
      ```
      Observable<Integer> observable=Observable.create(new ObservableOnSubscribe<Integer>() {
      
                  @Override
                  public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                      e.onNext(1);
                      e.onNext(2);
                      e.onComplete();
                  }
              });
      ```
      
      step2：初始化一个Observer
       
      ```
           Observer<Integer> observer= new Observer<Integer>() {
          
                      @Override
                      public void onSubscribe(Disposable d) {
          
                      }
          
                      @Override
                      public void onNext(Integer value) {
          
          
                      }
          
                      @Override
                      public void onError(Throwable e) {
          
                      }
          
                      @Override
                      public void onComplete() {
                      }
                  }
      ```
      
      step3：建立订阅关系
      
       ```
          observable.subscribe(observer); //建立订阅关系
      
       ```
       
       不难看出，与 RxJava1.0 还是存在着一些区别的。首先，创建Observable时，回调的是ObservableEmitter,字面意思即发射器，用于发射数据（onNext()）和通知(onError()/onComplete())。其次，创建的Observer中多了一个回调方法 onSubscribe()，传递参数为Disposable。
       
       ObservableEmitter： Emitter是发射器的意思，那就很好猜了，这个就是用来发出事件的，它可以发出三种类型的事件，通过调用emitter的 onNext(T value) 、onComplete()和onError(Throwable e)就可以分别发出next事件、complete事件和error事件
       
       Disposable：这个单词的字面意思是一次性用品,用完即可丢弃的。 那么在RxJava中怎么去理解它呢, 对应于上面的水管的例子, 我们可以把它理解成两根管道之间的一个机关, 当调用它的 dispose() 方法时, 它就会将两根管道切断, 从而导致下游收不到事件，即相当于 Subsciption。
       

       
       
      
  3. Flowable
  
      Flowable是RxJava 2.0中新增的类，专门用于应对背压（Backpressure）问题，但这并不是RxJava 2.0中新引入的概念。所谓背压，即生产者的速度大于消费者的速度带来的问题，比如在Android中常见的点击事件，点击过快则会造成点击两次的效果。 
      
      我们知道，在RxJava 1.0中背压控制是由Observable完成的，使用如下：
      
     ```
        Observable.range(1,10000)
                .onBackpressureDrop()
                .subscribe(integer -> Log.d("JG",integer.toString()));
     ```
    
     而在RxJava 2.0中将其独立了出来，取名为Flowable。因此，原先的Observable已经不具备背压处理能力。 
   
   4. 简化了订阅方法
   
      对于简化订阅的方式， RxJava 1 主要采用 ActionX接口 & FuncX接口，在 RxJava 2 中，主要是对这一系列接口的名字 按照Java8的命名规则 进行了修改，而使用方法不变
      
      4.1  ActionX 和 FuncX 改名对于 ActionX接口名的更改
      
      RxJava 1	RxJava 2
      Action0	Action
      Action1	Consumer（接收1个参数）
      Action2	BiConsumer （接收2个参数）
      ActionN	Consumer
      
      4.2 RxJava2的接口方法都允许抛出异常
      
      即，接口方法里加上了 throws Exception
      
   5. 操作符的改变
   
       对于操作符，RxJava 1.0与 RxJava 2.0 在命名 & 行为上大多数保持了一致
       
       需要强调的是first（）、subscribeWith（）和 compose()操作符

      5.1 first（）操作符
      
      RxJava 1.0	RxJava 2.0
      first（）	改名为：firstElement（）
      first(Func1)	弃用，改用为：filter(predicate).first()
      firstOrDefault(T)	改名为：first（T）
      firstOrDefault(Func1, T)	改名为：first（T）

      ```
      <-- RxJava 1.0 -->
      Observable
                .concat(Observable.from(list))
                .first(new Func1<Data, Boolean>() {
                      @Override
                      public Boolean call(Data data) {
                          return DataUtils.isAvailable(data);
                      }
                  }).publish();
      
      <-- RxJava 2.0 -->
      Observable
                .concat(Observable.fromIterable(list))
                .filter(new Predicate<Data>() {
      
                      @Override
                      public boolean test(@NonNull Data data) throws Exception {
                          return DataUtils.isAvailable(data);
                      }
                  }).firstElement().toObservable().publish();
      ```
      
      5.2   subscribeWith（）操作符
         
      背景： 在Rxjava2中，subscribeWith()返回的事Disposable而不是Subscription
      
      冲突：  保持向后的兼容（对于Rxjava1）
      
      解决方案：  Flowable 提供SubscribeWith()返回当前的观察者Subscriberd对象
      			同时提供DefaultSubsriber,ResourceSubscriber,DisPosableSubscriber接口(用于提供Disposable对象)从而管理生命周期
      			
      5.3 compose（）操作符
      
          RxJava 1.0实现的是：rx.Observable.Transformer接口继承自Func1<Observable<T>, Observable<R>>
          
          RxJava 2.0 实现的是io.reactivex.ObservableTansformer<Upstream, Downstream>一个独立的接口

   6. 额外
     
      6.1  新增Processor
     
        作用类似于 Subject & 继承自 Flowable = 支持背压控制而Subject则 不支持背压控制
     
      6.2  更改Single
     
        Single的作用类似于 Observable = 发送数据，但区别在于订阅后只能接受到1次
     
      6.3  更改Completable
     
        Completable的作用类似于 Observable = 发送数据，但区别在于订阅后只能接受 Complete 和 onError事件
     
      6.4 事件订阅RxBus
     
        事件总线方法发生变化    
 
         ```
        <-- rxjava1: -->
     	private final Subject<Object, Object> bus = new SerializedSubject<>(PublishSubject.create());
        <--  rxjava2: -->
     	private final FlowableProcessor<Object> bus = PublishProcessor.create().toSerialized();   	
   
       ```
     
      