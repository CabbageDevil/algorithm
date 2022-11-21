package com.example.demo;

/**
 * @ClassName 获得一个int整形的32位二进制表达
 * @Author CabbageDevil
 * @Version 1.0
 **/
public class bitwiseOperation {

    public static void print(int num){
        for(int i=31;i>=0;i--){
            System.out.print((num&(1<<i))==0?"0":"1");
        }
        System.out.println();
    }
    public static void main(String[] args) {
        int a=5;
        int b=-5;
        print(a);
        print(b);
    }
}
