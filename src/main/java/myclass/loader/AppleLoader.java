package myclass.loader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class AppleLoader extends ClassLoader {
    private static String TAG="苹果类加载器";

    /**就是.class文件的路径 如果是放在D盘 则 D:/*/
    private String mDir;
    public AppleLoader(String dir){
        System.out.println("AppleLoader-构造");
//        MainLoader.println(TAG, dir);
        this.mDir = dir;

    }
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        //这个name是全限定名 什么意思？
        //其实就是包名加类名。如本类的全限定名就是 com.zh.loader.AppleLoader.class。
        //本质其实还是路径  因为你编译后的路径会变成 磁盘路径+com/zh/loader/AppleLoader.class
        byte[] data = new byte[1024];
        InputStream inputStream;
        ByteArrayOutputStream byteArrayOutputStream;
        try {
            File file = new File(mDir, getFileName(name));
            inputStream = new FileInputStream(file);
            byteArrayOutputStream = new ByteArrayOutputStream();
            int len = 0;
            while ((len = inputStream.read(data)) != -1) {
                //  苹果类加载器 589
                MainLoader.println(TAG, len);
                byteArrayOutputStream.write(data, 0, len);
            }
            //这部分就是把Apple.class文件从磁盘读取出来
            byte[] buff = byteArrayOutputStream.toByteArray();
            inputStream.close();
            byteArrayOutputStream.close();
//            MainLoader.println(TAG, buff.length);
            //defineClass是原生方法：可以把符合虚拟机规则的 byte[]数组 转化成为描述类文件的Class对象
            return defineClass(name, buff, 0, buff.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.findClass(name);
    }
    // 获取要加载 的class文件名
    private String getFileName(String name) {
        int index = name.lastIndexOf('.');
        if (index == -1) {
            return name + ".class";
        } else {
            return name.substring(index + 1) + ".class";
        }


    }

    public static <T> void println(T ... msg){
        StringBuilder s = new StringBuilder();
        for(T t: msg){
            s.append(" ").append(t.toString());
        }
        System.out.println(s);

    }

}
