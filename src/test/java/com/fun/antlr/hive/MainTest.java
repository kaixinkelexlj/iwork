package com.fun.antlr.hive;

import org.apache.hadoop.hive.ql.parse.ASTNode;
import org.apache.hadoop.hive.ql.parse.ParseDriver;
import org.junit.Test;

/**
 * @author xulujun 2019/09/11
 */
public class MainTest {

  @Test
  public void testParseHive() throws Exception{
    String sql = "create table namex(id bigint)";
    ASTNode astNode = new ParseDriver().parse(sql);
    System.out.println(astNode.dump());
  }

}
