package com;

import com.dao.TaskDao;
import com.domain.Task;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class haha {
    //其中 n 取 1,2,3,4,5 五个数， m 取 3
        static int N = 5;
        static int M = 3;
        static int[] a= new int[]{1,2,3,4,5};
        static int[] b = new int[M];

        public static void main(String[] args){
            C(N,M);
        }

        static void C(int m,int n){
            HashMap<Integer, Integer> map = new HashMap<>();
            ArrayList<Integer> list = new ArrayList<>();

            int i,j;
            for(i=n;i<=m;i++) {
                b[n-1] = i-1;
                if(n>1){
                    C(i-1,n-1);
                } else {
                    for(j=0;j<=M-1;j++){
                        System.out.print(a[b[j]] + "  ");
                    }
                    System.out.println();
                }
            }
        }
    }