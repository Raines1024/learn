package com.raines.thread.concurrentTest;

public interface ResultHandler<T> {
    public void handle(T result);
}