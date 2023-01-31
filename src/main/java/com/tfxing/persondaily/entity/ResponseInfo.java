package com.tfxing.persondaily.entity;

import com.tfxing.persondaily.entity.enums.StatusCodeEnum;
import lombok.Data;

/**
 * @author :tanfuxing
 * @date :2023/1/31
 * @description :
 */
@Data
public class ResponseInfo<T> {
    
    private T data;
    
    private Integer code;
    
    private String message;
    
    public ResponseInfo(T data, Integer code, String message) {
        this.data = data;
        this.code = code;
        this.message =message;
    }
    
    public ResponseInfo(T data, Integer code) {
        this(data,code, StatusCodeEnum.SUCCESS.getMessage());
    }
    
    public ResponseInfo(T data) {
        this(data, StatusCodeEnum.SUCCESS.getCode(), StatusCodeEnum.SUCCESS.getMessage());
    }

    public ResponseInfo(String message) {
        this(null, null, message);
    }
    

    public static <T> ResponseInfo<T> success(T t) {
        return new ResponseInfo<>(t);
    }
    
    public static <T> ResponseInfo<T> success(T t, int code) {
        return new ResponseInfo<>(t, code);
    }

    public static <T> ResponseInfo<T> success(T t, int code, String message) {
        return new ResponseInfo<>(t, code, message);
    }

    public static <T> ResponseInfo<T> fail(String message) {
        return new ResponseInfo<>( message);
    }

    public static <T> ResponseInfo<T> fail(T t, String message) {
        return new ResponseInfo<>(t,null,message);
    }

    public static <T> ResponseInfo<T> fail(T t, Integer code, String message) {
        return new ResponseInfo<>(t,code,message);
    }
}
