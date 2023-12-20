package com.fun.bigdata.calcite;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.calcite.avatica.util.Casing;
import org.apache.calcite.avatica.util.Quoting;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.parser.impl.SqlParserImpl;
import org.apache.calcite.sql.validate.SqlConformance;
import org.apache.calcite.sql.validate.SqlConformanceEnum;

public class CalciteSqlParser {
  /*//---SqlParser配置参数
  static final Quoting quoting = Quoting.BACK_TICK;
  static final Casing unquotedCasing = Casing.UNCHANGED;
  static final Casing quotedCasing = Casing.UNCHANGED;
  static final SqlConformance conformance = SqlConformanceEnum.ORACLE_10;
  static final SqlParser.Config config = SqlParser.configBuilder()
      .setParserFactory(SqlParserImpl.FACTORY)
      .setQuoting(quoting)
      .setUnquotedCasing(unquotedCasing)
      .setQuotedCasing(quotedCasing)
      .setConformance(conformance)
      .build();

  *//**
   * 获取SqlNode
   * @param sqlStatement Sql语句
   * @return             SqlNode
   *//*
  public static SqlNode getSqlNode(String sqlStatement) throws SqlParseException {
    SqlParser sqlParser = getSqlParser(sqlStatement);
    SqlNode sqlNode = null;
    sqlNode = sqlParser.parseQuery();
    return sqlNode;
  }

  *//**
   * 获取SqlNode
   * @param expression  expression语句
   * @return            SqlNode
   *//*
  public static SqlNode getSqlNodeOfExp(String expression) throws SqlParseException {
    SqlParser sqlParser = getSqlParser(expression);
    SqlNode sqlNode = null;
    sqlNode = sqlParser.parseExpression();
    return sqlNode;
  }

  *//**
   * 获取用户自定义SqlParser
   * @param sqlStatement  Sql语句
   * @return              用户自定义SqlParser
   *//*
  public static SqlParser getSqlParser(String sqlStatement) {
    return SqlParser.create(sqlStatement, config);
  }


  *//***
   * 关键字转置处理
   * 公司hive仓库分区字段year、month、day 和calcite关键字冲突，做转置处理
   * @param sqlStatement Sql语句
   * @return 转置处理后的Sql语句
   *//*
  public static String quotingKeyWords(String sqlStatement) {
    //前后项断言处理
    String patternYear = "(?<!\\w|_|`|')(year|YEAR)(?!\\w|_)";
    String patternMonth = "(?<!\\w|_|`|')(month|MONTH)(?!\\w|_)";
    String patternDay = "(?<!\\w|_|`|')(day|DAY)(?!\\w|_)";
    String patternHour = "(?<!\\w|_|`|')(hour|HOUR)(?!\\w|_)";
    String patternWeek = "(?<!\\w|_|`|')(week|WEEK)(?!\\w|_)";
    return sqlStatement.replaceAll(patternYear, "`year`")
      .replaceAll(patternMonth, "`month`")
      .replaceAll(patternDay, "`day`")
      .replaceAll(patternHour, "`hour`")
      .replaceAll(patternWeek, "`week`");
  }

  *//**
   * calcite（以及presto）不支持数字开头的标识符，需要添加转置
   * 如
   * SUM(table_1527044669654.7_before_reg_dri_num) as 7_before_reg_dri_num
   * 转置为
   * SUM(table_1527044669654.`7_before_reg_dri_num`) as `7_before_reg_dri_num`
   * @param sqlStatement Sql语句
   * @return 转置处理后的Sql语句
   *//*
  public static String quotingNumericIdentifer(String sqlStatement) {
    String regex = "(?<![\\w|'|`])(\\d+[_a-zA-Z]+[_a-zA-Z0-9]*)(?!\\w|_|`|')";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(sqlStatement);

    while (matcher.find()) {
      sqlStatement = matcher.replaceFirst("`" + matcher.group(1) + "`");
      matcher = pattern.matcher(sqlStatement);
    }
    return sqlStatement;
  }

  *//**
   * 转置非法标识符
   * @param sqlStatement Sql语句
   * @return 转置处理后的Sql语句
   *//*
  public static String quotingIllegalIdentifer(String sqlStatement) {
    return quotingKeyWords(sqlStatement);
  }*/
}