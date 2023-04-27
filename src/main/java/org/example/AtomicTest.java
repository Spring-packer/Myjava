package org.example;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Package:        concurrent.test
 * ClassName:      AtomicTest
 * Author:          作者：龙飞
 * CreateDate:     2020/4/15 15:38
 * Version:        1.0
 * 线程安全 且无阻塞 无锁定的 类 Atomic
 */
public class AtomicTest {
    public static void main(String[] args) {

        final unsafeSeq seq = new unsafeSeq();
        final  safeSeq safeSeq = new safeSeq();

        final CountDownLatch L = new CountDownLatch(10); // 线程数量 等待几个线程完成之后执行当前线程
        for( int i = 0; i < 10; i++){
            final int index = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i=0; i<1000; i++){
                        seq.add();
                        safeSeq.add();
                    }
                    System.out.println("Thread" + index + "finished ");
                    L.countDown();
                }
            }).start();
        }

        try {
            L.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("所有线程结束。。。");
        System.out.println(seq.get() +  " 不安全的值");
        System.out.println(safeSeq.get() +  " 安全的值");


    }
}
class unsafeSeq{
    private int count = 0;
    public void add(){
        this.count++;
    }
    public int get(){
        return this.count;
    }
}
class safeSeq{
    private AtomicInteger ai = new AtomicInteger(0);
    public void add(){
        ai.incrementAndGet();
    }
    public int get(){
        return ai.intValue();
    }

}