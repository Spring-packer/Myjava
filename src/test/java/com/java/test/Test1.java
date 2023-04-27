package com.java.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class Test1 {
//    @TempDir
    String file = "ABCDEF";

    @Test
    public void test2(){

       for(int i =0; i<100; i++){
           System.out.print ((char) i  + " ");
       }

        System.out.println();
        System.out.println("hello Test");

    }
}
