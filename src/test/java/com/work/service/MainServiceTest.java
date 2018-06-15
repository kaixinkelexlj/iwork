package com.work.service;

import java.util.HashMap;
import java.util.Map;

import com.work.AbstractTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-config.xml")
public class MainServiceTest extends AbstractTest {

    /*@Autowired
    MainService mainService;

    @Test
    public void insertTest() {
        String sql = "insert into deviceMgr(mgrName) values(:name)";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", "测试MainService");
        Assert.assertEquals(mainService.insert(sql, params), 1);
    }

    @Test
    public void txTest() {
        try {
            String sql = "insert into deviceMgr(mgrName) values(:name)";
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("name", "测试MainService");
            mainService.tx(sql, params);
        } catch (Exception ex) {
            LoggerUtils.error(ex);
        }
    }*/
}

