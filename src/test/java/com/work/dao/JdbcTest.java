package com.work.dao;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.work.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-config.xml")
public class JdbcTest extends AbstractTest{

	@Autowired
	JdbcTemplate jt;
	@Test
	public void testQueryList(){
		String sql = "select 1  from dual where 1 = 2";
		List<Map<String, Object>> result = jt.queryForList(sql);
		Assert.assertEquals(result.size(), 0);
	}

}
