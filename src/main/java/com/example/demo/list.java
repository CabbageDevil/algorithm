package com.example.demo;

import java.util.Scanner;

/**
 * @ClassName list
 * @Description TODO
 * @Author CabbageDevil
 * @Date 2021/11/3 19:03
 * @Version 1.0
 **/
public class list {
    public static void main(String[] args) {

        int[] ints = {5,9,7,5};

        bucketSort(ints);
        for (int k : ints) {
            System.out.print(k + " ");

        }
    }
    static synchronized void bucketSort(int[] arr){

        int[] bk = new int[50000 * 2 + 1];
        for(int i = 0; i < arr.length; i++){
            bk[arr[i] + 50000] += 1;
        }
        int ar = 0;
        for(int i = 0; i < bk.length; i++){
            for(int j = bk[i]; j > 0; j--){
                arr[ar++] = i - 50000;
            }
        }
    }
}
