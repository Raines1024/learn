//package com.raines.webflux.netty;
//
//import io.netty.channel.ChannelPromise;
//import io.netty.util.concurrent.EventExecutor;
//
//public class Demo {
//
//
//    private void write(Object msg, boolean flush, ChannelPromise promise) {
//        //1.如果调用线程是IO线程
//        EventExecutor executor = next.executor();
//        if (executor.inEventLoop()) {
//            if (flush) {
//                next.invokeWriteAndFlush(m, promise);
//            } else {
//                next.invokeWrite(m, promise);
//            }
//        } else {//2.如果调用线程不是IO线程
//            AbstractWriteTask task;
//            if (flush) {
//                task = WriteAndFlushTask.newInstance(next, m, promise);
//            } else {
//                task = WriteTask.newInstance(next, m, promise);
//            }
//            safeExecute(executor, task, promise, m);
//        }
//    }
//}
