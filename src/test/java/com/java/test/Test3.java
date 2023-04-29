package com.java.test;
import myclasses.loader.ClassPathClassLoader;
import myclasses.loader.MyClassLoader;
import myclasses.loader.container.service.ModelService;
import myclasses.loader.container.service.MyApplicationContext;
import myclasses.loader.container.service.UserService;
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


    // 容器 控制反转 实现, 将对象创建的过程交给容器完成;
    @Test
    public void ClassUtil1() throws Exception {

        MyApplicationContext context = new MyApplicationContext("myclasses.loader.container.service");
        // 指定扫面的包 scanpackage 放入构造比较合适,
        // 因为 map的key只存放了简单类名字, 不同包的类会有冲突 所以最好一个包对应一个context
//        context.scanPackage("myclasses.loader.container.service");

        ModelService modelService = (ModelService)context.getInstance("ModelService");
        modelService.TestModel();


        Object object = context.getInstance("UserService");
        if(null != object){
            UserService m1 = (UserService) object;
            System.out.println(object + "==" +m1);
            m1.testUserService();

        }

    }

}
