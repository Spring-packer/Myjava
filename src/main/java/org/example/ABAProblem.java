package org.example;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * ProjectName:    java_day01
 * Package:        concurrent
 * ClassName:      ABATest
 * Description:     类作用描述
 * Author:          作者：龙飞
 * CreateDate:     2023/4/15 17:12
 * Version:        1.0
 */
class myThread extends Thread{

}
public class ABAProblem {
    private static AtomicReference af = new AtomicReference(100);
    private static AtomicStampedReference asf = new AtomicStampedReference(100, 1);

    public static void main(String[] args) {

        final CountDownLatch countDownLatch = new CountDownLatch(2);

        new Thread(new Runnable() {
            @Override
            public void run() {
                af.compareAndSet(100,101);
                af.compareAndSet(101,100);
                countDownLatch.countDown();
//                try {
//                    Thread.currentThread().sleep(10 * 1009);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.currentThread().sleep(2 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                af.compareAndSet(100, 2019);
                countDownLatch.countDown();
            }
        }).start();

//        new Thread(() ->{
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        }, "t1").start();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("结束。。。");
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int i = (int)af.get();
        System.out.println(i + " af的值");


    }
}