package com.tfxing.persondaily.aop;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tfxing.persondaily.entity.annotation.HotKey;
import com.tfxing.persondaily.entity.annotation.ListenHotKey;
import com.tfxing.persondaily.utils.SpringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
public class HotKeyAspectj {

    @Pointcut("@annotation(com.tfxing.persondaily.entity.annotation.ListenHotKey)")
    public void pointcut() {}

    @Around("pointcut()")
    @Transactional(rollbackFor = Exception.class)
    public void conditionAspectj(ProceedingJoinPoint point) throws Throwable {
        Object arg = getArg(point);
        Class<?> argClass = arg.getClass();

        if(null == argClass.getAnnotation(HotKey.class)) {
            proceed(point);
            return;
        }

        Object beforeUpdate = getBtId(point,arg);

        proceed(point);

        Object afterUpdate = getBtId(point,arg);

        handleHotKeyScore(argClass,beforeUpdate,afterUpdate);

    }

    private void handleHotKeyScore(Class<?> argClass, Object beforeUpdate, Object afterUpdate) throws Exception {
        List<Field> hotKeyFieldList = listHotKeyField(argClass);
        if(CollectionUtil.isEmpty(hotKeyFieldList)) {
            return;
        }

        // 如果是新增，则直接hotKey的score+1
        if(null == beforeUpdate) {

            for (Field field : hotKeyFieldList) {
                field.setAccessible(true);

                HotKey hotKeyAnnotation = field.getAnnotation(HotKey.class);
                String fieldName = hotKeyAnnotation.fieldName();
                Class mapperClass = hotKeyAnnotation.mapperClass();

                Object fieldValue = field.get(afterUpdate);
                if(null == fieldValue) {
                    continue;
                }

                Object hotKeyObj = getHotKeyById(mapperClass,fieldName,fieldValue);
                Class<?> hotKeyClass = hotKeyObj.getClass();
                Field hotKeyScore = hotKeyClass.getDeclaredField("score");
                hotKeyScore.setAccessible(true);
                hotKeyScore.set(hotKeyObj,fieldValue);

                updateHotKeyScore(mapperClass,hotKeyObj);
            }

        // 否则需要对比是否有内容改变
        } else {

            for (Field field : hotKeyFieldList) {
                field.setAccessible(true);

                HotKey hotKeyAnnotation = field.getAnnotation(HotKey.class);
                String fieldName = hotKeyAnnotation.fieldName();
                Class mapperClass = hotKeyAnnotation.mapperClass();

                Object fieldValue = field.get(afterUpdate);
                if(null == fieldValue) {
                    continue;
                }

                if(fieldValue.equals(field.get(beforeUpdate))) {
                    continue;
                }

                Object hotKeyObj = getHotKeyById(mapperClass,fieldName,fieldValue);
                Class<?> hotKeyClass = hotKeyObj.getClass();

                Field keyField = hotKeyClass.getDeclaredField(fieldName);
                keyField.setAccessible(true);

                Field hotKeyScore = hotKeyClass.getDeclaredField("score");
                hotKeyScore.setAccessible(true);
                Long score = (Long) hotKeyScore.get(hotKeyObj);

                if(null == score) {
                    score = 0L;
                    hotKeyScore.set(hotKeyObj,score);
                } else {
                    score++;
                    hotKeyScore.set(hotKeyObj,score);
                }


                updateHotKeyScore(mapperClass,hotKeyObj);
            }
        }
    }

    /**
     * 更新hotKey的score
     * @param mapperClass
     * @param hotKeyObj
     */
    private void updateHotKeyScore(Class mapperClass, Object hotKeyObj) throws Exception {

        Object obj = SpringUtils.getBean(mapperClass);
        Method updateById = mapperClass.getDeclaredMethod("updateById");
        updateById.invoke(obj,hotKeyObj);
    }

    private Object getHotKeyById(Class mapperClass, String fieldName, Object fieldValue) throws Exception {

        Object obj = SpringUtils.getBean(mapperClass);

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(fieldName,fieldValue);

        Method selectOneMethod = mapperClass.getDeclaredMethod("selectOne", Serializable.class);
        Object invoke = selectOneMethod.invoke(obj, queryWrapper);

        return invoke;
    }

    private Object getBtId(ProceedingJoinPoint point, Object arg) throws Exception {
        Class<?> argClass = arg.getClass();
        Field idField = argClass.getDeclaredField("id");
        idField.setAccessible(true);

        Long id = (Long) idField.get(arg);

        if(null == id) {
            return null;
        }

        ListenHotKey pointAnnotation = getPointAnnotation(point);
        return selectById(pointAnnotation.mapperClass(),id);
    }

    private List<Field> listHotKeyField(Class<?> argClass) {
        List<Field> targetFieldList = new ArrayList<>();

        Field[] fieldList = argClass.getDeclaredFields();
        for (Field field : fieldList) {
            field.setAccessible(true);
            HotKey hotKeyAnnotation = field.getAnnotation(HotKey.class);
            if(null == hotKeyAnnotation) {
                continue;
            }

            targetFieldList.add(field);
        }

        return targetFieldList;
    }

    private void proceed(ProceedingJoinPoint point) throws Throwable {
        try {
            point.proceed();
        } catch (Throwable e) {
            throw e;
        }
    }

    private Object getArg(ProceedingJoinPoint point) {
        Object[] args = point.getArgs();
        if(null == args || args.length < 1) {
            throw new RuntimeException("参数异常");
        }
        return args[0];
    }

    private Object selectById(Class clazz, Long id) throws Exception {
        Object target = SpringUtils.getBean(clazz);
        Method method = clazz.getMethod("selectById", Serializable.class);

        Object value = method.invoke(target, id);
        return value;
    }

    private ListenHotKey getPointAnnotation(ProceedingJoinPoint point) {
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if(null == method) {
            throw new RuntimeException("系统异常，方法不存在");
        }

        ListenHotKey listenHotKeyAnnotation = method.getAnnotation(ListenHotKey.class);
        if(null == listenHotKeyAnnotation) {
            throw new RuntimeException("系统异常，ListenHotKey注解不能为空");
        }

        if(null == listenHotKeyAnnotation.mapperClass()) {
            throw new RuntimeException("系统异常，mapperClass不能为空");
        }

        return listenHotKeyAnnotation;
    }
}
