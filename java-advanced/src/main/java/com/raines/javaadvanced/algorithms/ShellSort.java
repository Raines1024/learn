package com.raines.javaadvanced.algorithms;

import com.alibaba.fastjson.JSON;

/**
 * 希尔排序算法
 */
public class ShellSort {

    public static int[] shellSort(int[] arrays) {
        //增量每次都/2
        for (int step = arrays.length / 2; step > 0; step /= 2) {
            //从增量那组开始进行插入排序，直至完毕
            for (int i = step; i < arrays.length; i++) {
                int j = i;
                int temp = arrays[j];
                // j - step 就是代表与它同组隔壁的元素
                while (j - step >= 0 && arrays[j - step] > temp) {
                    arrays[j] = arrays[j - step];
                    j = j - step;
                }
                arrays[j] = temp;
            }
        }
        return arrays;
    }


    public static void main(String[] args) {
        int[] array = new int[5];
        array[0] = 6;
        array[1] = 2;
        array[2] = 5;
        array[3] = 8;
        array[4] = 7;
        System.out.println("排序前" + JSON.toJSONString(array));
        System.out.println("排序后" + JSON.toJSONString(shellSort(array)));
    }
}
