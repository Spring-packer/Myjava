package com.java.test;
import myclasses.loader.ClassPathClassLoader;
import myclasses.loader.MyClassLoader;
import org.junit.jupiter.api.Test;

public class Test3{

    @Test
    public void testLoader()throws Exception{
        MyClassLoader myClassLoader = new MyClassLoader("D:\\Apple.class");
        Class clazz = myClassLoader.loadClass("myclass.loader.Apple");
        Object obj = clazz.newInstance();
        System.out.println(obj);
        System.out.println(obj.getClass().getClassLoader());
    }

    @Test
    public void classesLoader()throws Exception{

        ClassPathClassLoader classPathClassLoader = new ClassPathClassLoader("D:\\app\\javas");
        Class<?> aClass = classPathClassLoader.loadClass("myclass.loader.Apple");
        Object o = aClass.newInstance();
        System.out.println(o);


    }





    @Test
    public void test2(){
        for(int i =0; i<100; i++){
            System.out.print ((char) i  + " ");
        }
        System.out.println();
        System.out.println("hello Test" );
        f();
    }
    public void f(){
        Object o = new Object();
        synchronized (o){
            System.out.println(o);
        }
    }

}
