package com.example.demo;

import java.util.Scanner;

/**
 * @ClassName demo
 * @Description TODO
 * @Author CabbageDevil
 * @Date 2021/9/20 18:48
 * @Version 1.0
 **/
public class demo {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);   //定义scanner输入循环体
        //获得循环体
        Integer a =Integer.parseInt(scanner.next());
        //控制变量
        int b=0;
        for(int i=2;i<a+2;i++){
            if(0!=i%2){
                System.out.printf(asciiToString(b+97+""));
                b=b+1;
            }else{
                System.out.printf(asciiToString(b+65+""));
            }
        }
    }
    //ascii的转变方法
    public static String asciiToString(String ascii){
        StringBuffer sb= new StringBuffer();
        sb.append((char)Integer.parseInt(ascii));
        return sb.toString();
    }
}
