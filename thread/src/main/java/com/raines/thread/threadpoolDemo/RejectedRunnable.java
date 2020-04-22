package com.raines.thread.threadpoolDemo;

/**
 *拒绝策略集合
 */
public interface  RejectedRunnable extends Runnable {

    void rejected();
}
