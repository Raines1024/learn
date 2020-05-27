package com.raines.jvm.OutOfMemoryErrorAction;

import java.util.ArrayList;
import java.util.List;

/**
 * Java 堆内存溢出异常测试
 * VM Args：-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 *
 * 限制Java堆的大小为20MB，不可扩展（将堆的最小值-Xms参数与最大值-Xmx参数
 * 设置为一样即可避免堆自动扩展），通过参数-XX：+HeapDumpOnOutOf-MemoryError可以让虚拟机
 * 在出现内存溢出异常的时候Dump出当前的内存堆转储快照以便进行事后分析
 */
/*
Java堆用于储存对象实例，我们只要不断地创建对象，并且保证GC Roots到对象之间有可达路径
来避免垃圾回收机制清除这些对象，那么随着对象数量的增加，总容量触及最大堆的容量限制后就会
产生内存溢出异常。
 */
public class HeapOOM {
    static class OOMObject {
    }
    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<>();
        while (true) {
            list.add(new OOMObject());
        }
    }
}
