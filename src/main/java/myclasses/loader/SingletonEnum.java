package myclasses.loader;

import javax.xml.crypto.Data;
import java.io.*;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.sql.Connection;

public class SingletonEnum {
    public static void main(String[] args) throws Exception{
        Object conn1 = DataSourceEnum.DATASOURCE.getConn();


        // // 枚举类 使用反射调用 以下方式不对 只能只用 getEnumConstants 不然会报错 NoSuchMethodException;
//        Constructor<? extends DataSourceEnum> constructor = DataSourceEnum.DATASOURCE.getClass().getDeclaredConstructor();
//        constructor.setAccessible(true);
//        DataSourceEnum dataSourceEnum = constructor.newInstance();
//        Object conn3 = dataSourceEnum.getConn();
        // 使用getEnumConstants 使用反射, 获取枚举类的所有实例
        DataSourceEnum[] enumConstants = DataSourceEnum.class.getEnumConstants();
        DataSourceEnum enumConstant = enumConstants[0];
        Object conn3 = enumConstant.getConn();

        System.out.println(conn1==conn3 );


        // 试图使用序列化 破坏枚举类的单例
        FileOutputStream fileOutputStream = new FileOutputStream("Singleton.txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(DataSourceEnum.DATASOURCE);

        objectOutputStream.close();
        fileOutputStream.close();


        File file = new File("Singleton.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(file.toPath()));
        DataSourceEnum o = (DataSourceEnum)objectInputStream.readObject();

        objectInputStream.close();


        Object conn4 = o.getConn();

        System.out.println("序列化方式创建的枚举类 是否可以被破坏单例");
        System.out.printf("conn1 = %s", conn1);
        System.out.println();
        System.out.printf("conn4 = %s", conn4);
        System.out.println();
        System.out.println(conn1==conn4);

    }



}


// 1. 枚举类单例 ---> 搞一个实例即可
// 2. 定义对象的成员变量 conn 也就是这个实例的变量
// 3. 构造方法中将需要的数据库连接new出来
// 4. 提供一个公共方法: 用来获取连接
enum DataSourceEnum{
    DATASOURCE;
    private Object conn = null;
    private DataSourceEnum(){
        conn = new Object();
    }
    public Object getConn(){
        return conn;
    }

}

