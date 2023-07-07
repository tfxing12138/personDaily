package org.framework.core.util;

import com.tfxing.persondaily.entity.po.User;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class AuthUtil {

    /*
       不同的角色拥有查看不同字段的权限
       角色：
        超级管理员：
            拥有所有字段的权限
        部门管理员：
            配置部门管理员的字段权限
        小组管理员：
            配置小组管理员的字段权限
        运营管理员：
            配置运营管理员的字段权限

        不同的用户拥有查看不同字段的权限
            link：
                配置各自的字段权限
            jerry：
                。。。
     */

    /**
     * 返回该用户拥有的字段权限
     */
    public static List<Field> TauthUtil(User user) {
        List<Field> fieldList = new ArrayList<>();

        // 获取该用户的字段权限列表


        return fieldList;
    }
}
