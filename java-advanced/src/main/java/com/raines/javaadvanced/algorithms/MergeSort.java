package com.raines.javaadvanced.algorithms;

import com.alibaba.fastjson.JSON;

/**
 * 归并排序算法
 * 归并排序算法是基于归并操作的一种有效排序算法，是采用分治法的典型应用。
 * 归并排序算法的原理是先将原始数组分解为多个子序列，然后对每个子序列进行排序，最后将排好序的子序列合并起来。若将两个有序表合并成一个有序表，则称之为二路归并。
 */
public class MergeSort {

    //归并排序方法的入口
    public static int[] mergeSort(int[] data){
        sort(data,0,data.length-1);
        return data;
    }

    //对左右两边的数据进行递归拆解和合并
    public static void sort(int[] data,int left,int right){
        if (left >= right) return;
        //找出中间索引
        int center = (left+right)/2;
        //对左边的数组进行递归
        sort(data,left,center);
        //对右边的数组进行递归
        sort(data,center+1,right);
        //将两个数组进行归并
        merge(data,left,center,right);
    }

    /**
     * 将两个数组进行归并：两个数组在归并前是有序数组，在归并后依然是有序数组
     * @param data 数组对象
     * @param left 左边数组第1个元素的索引
     * @param center 左边数组最后一个元素的索引；center+1是右边数组第1个元素的索引
     * @param right 右边数组最后一个元素的索引
     */
    public static void merge(int[] data,int left,int center,int right){
        //临时数组
        int[] tmpArr = new int[data.length];
        //右边数组第1个元素的索引
        int mid = center+1;
        //third记录临时数组的索引
        int third = left;
        //缓存左边数组的第1个元素的索引
        int tmp = left;
        while (left <= center && mid <= right){
            //从两个数组中取出最小的值放入临时数组中
            if (data[left] <= data[mid]){
                tmpArr[third++] = data[left++];
            }else {
                tmpArr[third++] = data[mid++];
            }
        }
        //将剩余部分依次放入临时数组（实际上两个while只会执行其中一个）中
        while (mid <= right){
            tmpArr[third++] = data[mid++];
        }
        while (left <= center){
            tmpArr[third++] = data[left++];
        }
        //将临时数组中的内容复制到原数组中
        //原left-right范围内的内容被复制到原数组中
        while (tmp <= right){
            data[tmp] = tmpArr[tmp++];
        }
    }

    public static void main(String[] args) {
        int[] array = new int[5];
        array[0] = 6;
        array[1] = 2;
        array[2] = 5;
        array[3] = 8;
        array[4] = 7;
        System.out.println("排序前" + JSON.toJSONString(array));
        System.out.println("排序后" + JSON.toJSONString(mergeSort(array)));
    }

}









