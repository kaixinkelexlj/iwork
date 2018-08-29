package com.work.aop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-aop.xml")
public class HelloWorldServiceTest {

  @Autowired
  HelloWorldService helloWorldService;

  @Autowired
  FunServiceProvider funServiceProvider;

  @Test
  public void testSay() throws Exception {
    helloWorldService.say();
  }

  @Test
  public void testSay2() throws Exception {
    helloWorldService.say("xlj", "hhq");
  }

  @Test
  public void testLock() throws Exception {
    helloWorldService.sayOneByOne();
  }

  @Test
  public void testFun() throws Exception{
    funServiceProvider.hello();
    funServiceProvider.world();
  }
}
