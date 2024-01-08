package com.tfxing.persondaily;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName GlmGenVchVo
 * @Description TODO
 * @Author laisy
 * @Date 2023/7/26 12:40
 */
@Data
public class GlmGenVchVo {

    /**
     * 请求状态 是否成功 true：成功，false:失败
     **/
    private Boolean status = false;

    /**
     * 账套是否启用 true：启用，false：未启用
     **/
    private Boolean started = false;

    /**
     * 状态代码
     **/
    private String code;

    /**
     * 凭证id
     **/
    private Long vchid;

    /**
     * 异常key
     **/
    private String key;

    /**
     * 异常参数
     **/
    private List<String> params;

    public GlmGenVchVo() {}

    public GlmGenVchVo(String key) {
        if("SUCCESS".equals(key)) {
            this.status = true;
        }
        this.key = key;
    }

    public <T> GlmGenVchVo(String key, T ... params) {
        if("SUCCESS".equals(key)) {
            this.status = true;
        }

        this.key = key;

        List<String> paramList = new ArrayList<>();
        for (T param : params) {
            paramList.add(String.valueOf(param));
        }

        this.params = paramList;
    }
}
