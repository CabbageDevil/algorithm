package com.example.demo;

import java.util.Scanner;

/**
 * @ClassName list
 * @Description TODO
 * @Author CabbageDevil
 * @Date 2021/11/3 10:29
 * @Version 1.0
 **/
public class sortList {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);   //定义scanner输入
        int[] ints = new int[5];          //长度为5的数组
        for (int i = 0; i < ints.length; i++) {
            System.out.println("请输入第" + i + "个整数");   //控制台提示输入
            int j = scanner.nextInt();   //将输入数字赋值给j
            ints[i] = j;   // 将j赋值给数组
        }
        int l = ints.length;
        for (int j = 0; j < l - 1; j++) {
            int min = ints[j];
            // 找到最小值后，交换数据
            for (int z = j + 1; z < l; z++) {
                if (ints[z] < ints[j]) {
                    ints[j] = ints[z];
                    ints[z] = min;
                    min = ints[j];
                }
            }
        }
        // 输出从小到大排序数字
        for (int k : ints) {
            System.out.print(k + " ");

        }


    }


}

