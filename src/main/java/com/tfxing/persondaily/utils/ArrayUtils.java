package com.tfxing.persondaily.utils;

import cn.hutool.core.collection.CollectionUtil;
import org.apache.poi.ss.formula.functions.T;

import java.util.Iterator;
import java.util.List;

public class ArrayUtils {

    public static <T> List<T> merge(List<T> arr1, List<T> arr2) {
        java.util.ArrayList<T> list = new java.util.ArrayList<>();

        if(CollectionUtil.isEmpty(arr1) && CollectionUtil.isEmpty(arr2)) {
            return list;
        }

        if(CollectionUtil.isEmpty(arr1)) {
            return arr2;
        }

        if(CollectionUtil.isEmpty(arr2)) {
            return arr1;
        }

        List<T> tempArr1 = arr1.size() > arr2.size() ? arr1 : arr2;
        List<T> tempArr2 = arr1.size() > arr2.size() ? arr2 : arr1;

        List<T> duplicateItemList = new java.util.ArrayList<>();

        for (T t : tempArr2) {
            if(tempArr1.contains(t)) {
                duplicateItemList.add(t);
            }
        }

        if(CollectionUtil.isNotEmpty(duplicateItemList)) {
            Iterator<T> iterator1 = arr1.iterator();
            while (iterator1.hasNext()) {
                T next = iterator1.next();
                if (duplicateItemList.contains(next)) {
                    iterator1.remove();
                }
            }

            Iterator<T> iterator2 = arr2.iterator();
            while (iterator2.hasNext()) {
                T next = iterator2.next();
                if (duplicateItemList.contains(next)) {
                    iterator2.remove();
                }
            }
        }

        list.addAll(arr1);
        list.addAll(arr2);

        return list;
    }
}
