package com.tfxing.persondaily.utils;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * @author :tanfuxing
 * @date :2023/1/31
 * @description :
 */
public class CommonUtils {
    
    public static <T> boolean isExist(T t) {
        return null != t;
    }

    public static <T> T getCollection(Supplier<T> supplier) {
        return supplier.get();
    }
}
