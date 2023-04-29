package myclasses.loader.container.service;

import java.lang.reflect.Field;
import java.rmi.server.ExportException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class MyApplicationContext {
    private final  String packageName;
    public MyApplicationContext(String packageName)throws Exception{
        this.packageName = packageName;
        scanPackage();
    }
    private final ConcurrentHashMap<String, Object> beanMap = new ConcurrentHashMap<>();
    public Object getInstance(String className){
        if(null == className) return null;
        return beanMap.getOrDefault(className, null);

    }

    public void scanPackage() throws Exception{
        if (packageName == null || packageName.equals("")) {
            throw new Exception("package name err");
        }
        List<Class<?>> classes = ClassUtil.getClasses(packageName);
        for(Class<?> clazz: classes){
            UDFService annotation = clazz.getAnnotation(UDFService.class);
            if(null != annotation){
                String simpleName = clazz.getSimpleName();
                Object o = clazz.newInstance();
                System.out.println(simpleName + ", " + o.toString());
                beanMap.put(simpleName, o);
            }
        }
        // 上面已经完成package的扫描并将所有包含注解UDFService的类 放入 Map中了；
        // 下面:完成每个对象的属性赋值 AttriAssign
        AttriAssign();

    }


    private void AttriAssign()throws Exception{
        Collection<Object> values = beanMap.values();
        if(values.size()<=0)  throw new ExportException("BeanMap中没有实例");
        for(Object o: values){
            Field[] declaredFields = o.getClass().getDeclaredFields();
            for(Field field: declaredFields){
                UDFAutowired annotation = field.getAnnotation(UDFAutowired.class);
                if(annotation==null) continue;
                else {
                    String attrName = field.getName();
                    // 首字母转大写 因为 beanMap的 key 存放的事 类名字
                    char c = attrName.charAt(0);
                    c = (char) (c - 32);
                    String key = c + attrName.substring(1); // (获取从第二个元素开始的子字符串)到最后一个元素的话没有必要写出第二个参数长度

                    System.out.println("attrName: " + attrName);
                    System.out.println("key:" + key);
                    Object o1 = beanMap.get(key);
                    // set 第一个参数是对象 第二是要设置的值
                    field.set(o, o1);
                }
            }
        }
    }


}
