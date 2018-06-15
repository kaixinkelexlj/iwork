package com.fun.annotation;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-annotation-custom.xml")
public class AnnotationTest {
    @Resource
    private Papa papa;
    
    @Test
    public void testSay(){
        papa.say();
    }
}
