package com;

import java.util.ArrayList;
import java.util.List;

//将一个数组按照最大值拆分成两个小数组
public class SplitArray {
    static int M = 3;
    static int[] a= new int[]{1,2,3,4,5};
    static int[] b = new int[M];
    static int N = a.length;
    static List<String> list=new ArrayList<>();

    public static void main(String[] args) {
        C(N, M);
        System.out.println(list);
        System.out.println(list.size());

        List<int[]> ints = stringToInt(list);
        List<int[]> ints2 =new ArrayList<>();

        for (int[] i:ints){
            int[] implement = implement(a, i);
            ints2.add(implement);
        }
        for (int[] ints1:ints){
            for (int i:ints1){
                System.out.print(i);
            }
            System.out.print(",");
        }
        System.out.println("");

        for (int[] inti:ints2){
            for (int ii:inti){
                System.out.print(ii);
            }
            System.out.print(",");
        }

    }

    static List<String> C(int m,int n){
        int i,j;
        for(i=n;i<=m;i++) {
            b[n-1] = i-1;
            if(n>1) {
                C(i - 1, n - 1);
            }else {
                List<String> op= new ArrayList<>();
                for(j=0;j<=M-1;j++){
                    op.add(a[b[j]]+"");
                    System.out.print(a[b[j]] + "  ");
                }
                String s = String.join(",", op);
                list.add(s);
                System.out.println();
            }
        }
        return list;
    }

    /**
     * 字符串集合转成int数组集合
     * @param list
     * @return
     */
    static List<int[]> stringToInt (List<String> list){
        List<int[]> arrayList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            String[] split = s.split(",");
            int[] ints=new int[split.length];
            for (int j = 0; j < split.length; j++) {
                ints[j]=Integer.parseInt(split[j]);
            }
            arrayList.add(ints);
        }
        return arrayList;
    }

    public static int[] implement(int[] arr1, int[] arr2) {
        ArrayList<Integer> arrayList1 = new ArrayList<>();
        ArrayList<Integer> arrayList2 = new ArrayList<>();
        for (Integer i : arr1) {
            arrayList1.add(i);
        }
        for (Integer i : arr2) {
            arrayList2.add(i);
        }
        for (Integer i : arrayList2) {
            arrayList1.remove(i);
        }
        int[] arr = new int[arrayList1.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = arrayList1.get(i);
        }
        return arr;
    }





}

