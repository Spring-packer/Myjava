package myclass.loader;

import java.lang.reflect.Method;

public class MainLoader {

    /**
     *
     *
     * @param msg
     * @param <T>
     */

    public static <T> void println(T ... msg){

        StringBuilder s = new StringBuilder();
        for(T t: msg){
            s.append(" ").append(t.toString());
        }
        System.out.println(s);

    }

    public static void main(String[] args) {
        try {
            AppleLoader loader = new AppleLoader("D:\\");
            Class<?> aClass = loader.loadClass("myclass.loader.Apple");

            Method[] declaredMethods = aClass.getDeclaredMethods();
            declaredMethods[0].invoke(aClass.newInstance());
        } catch(Exception e) {
            e.printStackTrace();


        }
    }
}
