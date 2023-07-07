package org.framework.core;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.framework.core.annotation.Component;
import org.framework.core.annotation.Controller;
import org.framework.core.annotation.Repository;
import org.framework.core.annotation.Service;
import org.framework.core.util.ClassLoaderUtil;
import org.framework.core.util.ValidationUtil;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BeanContainer {

    private final Map<Class<?>,?> beanMap = new ConcurrentHashMap<>();

    private static final List<Class<? extends Annotation>> BEAN_ANNOTATION = Arrays.asList(Component.class, Controller.class, Repository.class, Service.class);

    private boolean loaded = false;

    public boolean isLoaded() {
        return loaded;
    }

    /**
     * 扫描加载所有的Bean
     * @param packageName
     */
    public synchronized void loadBeans(String packageName) {
        if(isLoaded()) {
            log.warn("BeanContainer has been loaded");
            return;
        }

        Set<Class<?>> classSet = ClassLoaderUtil.extractPackageClass(packageName);

        if(ValidationUtil.isEmpty(classSet)) {
            log.info("classSet isEmpty");
            return;
        }

        for (Class<?> clazz : classSet) {
            for (Class<? extends Annotation> annotation : BEAN_ANNOTATION) {
                if(clazz.isAnnotationPresent(annotation)) {
                    beanMap.put(clazz,ClassLoaderUtil.newInstance(clazz,true));
                }
            }
        }

        loaded = true;

    }

    public <T> T getBean(Class clazz) {
        return (T) beanMap.get(clazz);
    }

    /**
     * 获取BeanContainer容器对象
     * @return
     */
    public static BeanContainer getBeanContainer() {
        return ContainerHolder.HOLDER.instance;
    }

    private enum ContainerHolder {
        HOLDER;

        private BeanContainer instance;

        ContainerHolder() {
            instance = new BeanContainer();
        }
    }
}
