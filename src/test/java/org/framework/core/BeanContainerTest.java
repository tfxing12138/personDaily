package org.framework.core;

import com.tfxing.persondaily.entity.po.Answer;
import org.junit.Before;
import org.junit.Test;

public class BeanContainerTest {
    private static BeanContainer beanContainer;

    @Before
    public void init() {
        beanContainer = BeanContainer.getBeanContainer();
    }

    @Test
    public void testGetBean() {
        beanContainer.loadBeans("com");
        System.out.println(beanContainer.isLoaded());
    }
}
