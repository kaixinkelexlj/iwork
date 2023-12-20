package com.fun.bigdata.calcite;

import com.fun.TestBase;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.calcite.sql.SqlCall;
import org.apache.calcite.sql.SqlDataTypeSpec;
import org.apache.calcite.sql.SqlDynamicParam;
import org.apache.calcite.sql.SqlIdentifier;
import org.apache.calcite.sql.SqlIntervalQualifier;
import org.apache.calcite.sql.SqlKind;
import org.apache.calcite.sql.SqlLiteral;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.SqlNodeList;
import org.apache.calcite.sql.util.SqlVisitor;
import org.junit.Test;

/**
 * @author xulujun 2020/03/24.
 */
public class CalciteSqlParserTest extends TestBase {

  @Test
  public void testBasic() throws Exception {
    //org.apache.calcite.sql.fun.SqlStdOperatorTable
    /*Map<String, String> map = getYmlMap("sqls/basic");
    String sql = map.get("case1");
    System.out.println(sql);
    SqlNode sqlNode = CalciteSqlParser.getSqlNode(sql);
    System.out.println(sqlNode.toString());
    sqlNode = CalciteSqlParser.getSqlNode(map.get("case2"));
    PrinterVisitor printerVisitor = new PrinterVisitor();
    sqlNode.accept(printerVisitor);
    System.out.println(sqlNode.toString());
    System.out.println(printerVisitor.toString());*/
  }

  class PrinterVisitor implements SqlVisitor<Void> {

    private StringBuilder sb = new StringBuilder();
    private int indent = 0;

    PrinterVisitor() {
    }

    private Void format(SqlNode parent) {
      return this.format(parent, parent.toString());
    }

    private Void format(SqlNode parent, String desc, SqlNode... nodes) {
      return this.format(parent, desc, Arrays.asList(nodes));
    }

    private String label(SqlKind kind, int i) {
      switch (kind) {
        case SELECT:
          switch (i) {
            case 0:
              return "keywords";
            case 1:
              return "select";
            case 2:
              return "from";
            case 3:
              return "where";
            case 4:
              return "groupBy";
            case 5:
              return "having";
            case 6:
              return "windowDecls";
            case 7:
              return "orderBy";
            case 8:
              return "offset";
            case 9:
              return "fetch";
          }
        case ORDER_BY:
          switch (i) {
            case 0:
              return "query";
            case 1:
              return "orderList";
            case 2:
              return "offset";
            case 3:
              return "fetch";
          }
        default:
          return String.valueOf(i);
      }
    }

    private Void format(SqlNode parent, String desc, List<SqlNode> children) {
      this.sb.append(parent.getKind()).append(": ").append(desc);
      if (children.size() > 0) {
        this.sb.append(" {\n");
        ++this.indent;
        int i = 0;

        for (Iterator var5 = children.iterator(); var5.hasNext(); ++i) {
          SqlNode sqlNode = (SqlNode) var5.next();
          this.indent();
          this.sb.append(this.label(parent.getKind(), i)).append(": ");
          if (sqlNode == null) {
            this.sb.append("null");
          } else {
            sqlNode.accept(this);
          }

          this.sb.append(",\n");
        }

        this.indent();
        this.sb.append("}");
        --this.indent;
      }

      return null;
    }

    private void indent() {
      for (int i = 0; i < this.indent; ++i) {
        this.sb.append("  ");
      }

    }

    public Void visit(SqlLiteral literal) {
      return this.format(literal);
    }

    public Void visit(SqlCall call) {
      return this.format(call, call.getOperator().toString(), (List) call.getOperandList());
    }

    public Void visit(SqlNodeList nodeList) {
      return this.format(nodeList, "list", (List) nodeList.getList());
    }

    public Void visit(SqlIdentifier id) {
      return this.format(id);
    }

    public Void visit(SqlDataTypeSpec type) {
      return this.format(type);
    }

    public Void visit(SqlDynamicParam param) {
      return this.format(param);
    }

    public Void visit(SqlIntervalQualifier intervalQualifier) {
      return this.format(intervalQualifier);
    }

    public String toString() {
      return this.sb.toString();
    }

  }
}
