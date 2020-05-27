package com.raines.jvm.OutOfMemoryErrorAction;

/**
 * 虚拟机栈和本地方法栈溢出
 * VM Args：-Xss1M
 *·使用-Xss参数减少栈内存容量。
 * 结果：抛出StackOverflowError异常，异常出现时输出的堆栈深度相应缩小。
 */
public class JavaVMStackSOF {
    private int stackLength = 1;
    public void stackLeak() {
        stackLength++;
        stackLeak();
    }
    public static void main(String[] args) throws Throwable {
        JavaVMStackSOF oom = new JavaVMStackSOF();
        try {
            oom.stackLeak();
        } catch (Throwable e) {
            System.out.println("stack length:" + oom.stackLength);
            throw e;
        }
    }
}