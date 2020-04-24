package me.giraffetree.java.boomjava.concurrent.problem.thread_communication;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author GiraffeTree
 * @date 2020/4/22
 */
public class ActorSolution {

    //该Actor当收到消息message后，
    //会打印Hello message
    private static class HelloActor
            extends UntypedAbstractActor {
        @Override
        public void onReceive(Object message) {
            System.out.println("Hello " + message);
        }
    }

    //累加器
    private static class CounterActor extends UntypedAbstractActor {
        private int counter = 0;

        @Override
        public void onReceive(Object message) {
            //如果接收到的消息是数字类型，执行累加操作，
            //否则打印counter的值
            if (message instanceof Number) {
                counter += ((Number) message).intValue();
                System.out.println(counter);
            } else {
                System.out.println("final: " + counter);
            }
        }
    }

    public static void main(String[] args) {
        //创建Actor系统
        ActorSystem system = ActorSystem.create("HelloSystem");
        //创建HelloActor
        ActorRef helloActor = system.actorOf(Props.create(HelloActor.class));
        //发送消息给HelloActor
        helloActor.tell("Actor", ActorRef.noSender());

        //4个线程生产消息
        ExecutorService es = new ThreadPoolExecutor(4, 10,
                100L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(1024));
        //创建CounterActor
        ActorRef counterActor = system.actorOf(Props.create(CounterActor.class));
        //生产4*100000个消息
        for (int i = 0; i < 4; i++) {
            es.execute(() -> {
                for (int j = 0; j < 1000; j++) {
                    counterActor.tell(1, ActorRef.noSender());
                }
            });
        }
        //关闭线程池
        es.shutdown();
        try {
            es.awaitTermination(10000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //打印结果
        counterActor.tell("", ActorRef.noSender());
        //关闭Actor系统
        system.terminate();

    }

}
