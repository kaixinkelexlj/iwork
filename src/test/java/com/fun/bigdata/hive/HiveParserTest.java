package com.fun.bigdata.hive;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.dialect.hive.visitor.HiveASTVisitorAdapter;
import com.alibaba.druid.util.JdbcConstants;
import com.work.AbstractTest;
import java.util.List;
import org.apache.hadoop.hive.ql.parse.ASTNode;
import org.apache.hadoop.hive.ql.parse.ParseDriver;
import org.junit.Test;

/**
 * @author xulujun
 * @date 2018/11/27
 */
public class HiveParserTest extends AbstractTest {

  @Test
  public void testSql() throws Exception {
    String sql = "select idx from (select ta.id as idx from hivedb.test ta join hivedb.userxx tb on (ta.id = tb.id) where ta.id = 100 order by id desc) a order by id limit 100";
    ParseDriver parseDriver = new ParseDriver();
    ASTNode node = parseDriver.parse(sql);
    System.out.println(node.dump());

    List<SQLStatement> list = SQLUtils.parseStatements(sql, JdbcConstants.HIVE);
    list.get(0).accept(new HiveASTVisitorAdapter() {
      @Override
      public boolean visit(SQLExprTableSource tableSource) {
        System.out.println(tableSource.getAlias() + "." + tableSource.getName());
        return true;
      }
    });


  }

  @Test
  public void testColParse() throws Exception {
    String sql = "select * from(select sum(a), o as c2, test c3 from tb)";
    List<SQLStatement> list = SQLUtils.parseStatements(sql, JdbcConstants.HIVE);
    SQLSelectStatement statement = (SQLSelectStatement)list.get(0);
    statement.getSelect().getQueryBlock().getSelectList()
        .stream().forEach(col -> {
          System.out.println(String.format("expr %s, alias %s", col.getExpr(), col.getAlias()));
        });
  }

}
