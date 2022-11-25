package com;

import java.util.ArrayList;
import java.util.List;

public class M {
        static List<String> perm = new ArrayList<>();

        public static void main(String[] args) {
            char[] chars = {'1', '2', '3', '4', '5'};
            comb(chars, new char[2], 2, 0, 0);
            System.out.println(perm);
            System.out.println(perm.get(0));
            System.out.println(perm.size());
        }
        /**
         * description: 用于交换数组的两个位置
         * @param arr 原数组
         * @param p
         * @param q

         */

        public static void swap(char[] arr, int p, int q) {
            char temp = arr[p];
            arr[p] = arr[q];
            arr[q] = temp;
        }

        public static void comb(char[] arr, char[] temp, int m, int p, int indexOfTemp) {
            if (indexOfTemp == m) {
                List<Character> chars = new ArrayList<>();
                for (int i = 0; i < indexOfTemp; i++) {
                    chars.add(temp[i]);
                }
                perm.add(chars.toString());
            } else {
                for (int i = p; i < arr.length; i++) {
                    temp[indexOfTemp] = arr[i];
                    swap(arr, i, p);
                    comb(arr, temp, m, p + 1, indexOfTemp + 1);
                    swap(arr, i, p);
                }
            }
        }
    }

