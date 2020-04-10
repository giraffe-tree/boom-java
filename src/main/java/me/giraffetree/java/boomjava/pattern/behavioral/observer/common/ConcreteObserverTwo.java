package me.giraffetree.java.boomjava.pattern.behavioral.observer.common;

public class ConcreteObserverTwo implements Observer {
    @Override
    public void update(Message message) {
        //TODO: 获取消息通知，执行自己的逻辑...
        System.out.println("ConcreteObserverTwo is notified.");
    }
}
