package me.giraffetree.java.boomjava.pattern.behavioral.observer.common;

public interface Subject {
    void registerObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers(Message message);
}







