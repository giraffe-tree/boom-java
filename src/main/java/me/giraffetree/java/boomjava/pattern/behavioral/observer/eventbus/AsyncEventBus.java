package me.giraffetree.java.boomjava.pattern.behavioral.observer.eventbus;

import java.util.concurrent.Executor;

public class AsyncEventBus extends EventBus {
    public AsyncEventBus(Executor executor) {
        super(executor);
    }
}