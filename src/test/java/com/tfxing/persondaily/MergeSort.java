package com.tfxing.persondaily;

import java.util.Arrays;

public class MergeSort {
    
    public static void mergeSort(int[] arr, int start, int end) {
        if (start < end) {
            int mid = (start + end) / 2;
            mergeSort(arr, start, mid); // 对左半部分进行归并排序
            mergeSort(arr, mid + 1, end); // 对右半部分进行归并排序
            merge(arr, start, mid, end); // 合并左右两部分
        }
    }
    
    public static void merge(int[] arr, int start, int mid, int end) {
        int[] temp = new int[arr.length];
        int i = start, j = mid + 1, k = start;
        while (i <= mid && j <= end) {
            if (arr[i] <= arr[j]) {
                temp[k] = arr[i];
                i++;
            } else {
                temp[k] = arr[j];
                j++;
            }
            k++;
        }
        while (i <= mid) {
            temp[k] = arr[i];
            i++;
            k++;
        }
        while (j <= end) {
            temp[k] = arr[j];
            j++;
            k++;
        }
        for (int p = start; p <= end; p++) {
            arr[p] = temp[p];
        }
    }
    
    public static void main(String[] args) {
        int[] arr = {5, 2, 325, 6, 1, 3};
        mergeSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }
}