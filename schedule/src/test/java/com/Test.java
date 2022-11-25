package com;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Test {

    static int N = 5;
    static int M = 3;
    static int[] a= new int[]{1,2,3,4,5};
    static int[] b = new int[M];

    public static void main(String[] args) throws IOException {
        int[] arr={1,2,3};
        String s="";
        for (int i = 0; i < arr.length; i++) {
            if (i<arr.length-1){
                s+=String.valueOf(arr[i])+",";
            }else {
                s+=String.valueOf(arr[i]);
            }

        }
        System.out.println(s);
    }

    static void C(int m,int n) throws IOException {
        BufferedWriter bw=new BufferedWriter(new FileWriter("C:\\Users\\Sasuke\\Desktop\\path_schedule\\schedule\\src\\main\\resources\\hello.txt"));

        int i,j;
        for(i=n;i<=m;i++) {
            b[n-1] = i-1;
            if(n>1){
                C(i-1,n-1);
            } else {
                for(j=0;j<=M-1;j++){
                    System.out.print(a[b[j]]);
                }
                System.out.println();
            }
        }

        for (int k = 0; k < 100; k++) {
            bw.write(String.valueOf(k));
            bw.newLine();
        }
        bw.close();

    }


    /**
     * 删除arr1中包含arr2的元素，即返回一个arr2关于arr1的互补数组
     * @param arr1
     * @param arr2
     * @return
     */
    public static int[] implement(int[] arr1,int[] arr2){
        ArrayList<Integer> arrayList1 = new ArrayList<>();
        ArrayList<Integer> arrayList2 = new ArrayList<>();
        for (Integer i:arr1){
            arrayList1.add(i);
        }
        for (Integer i:arr2){
            arrayList2.add(i);
        }
        for (Integer i:arrayList2){
            arrayList1.remove(i);
        }
        int[] arr=new int[arrayList1.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i]=arrayList1.get(i);
        }
        return arr;
    }

    /**
     * 输出数组
     * @param arr
     */
    public static void arrToString(int[] arr){
        for (Integer i: arr){
            System.out.print(i);
        }
        System.out.println();
    }
}
