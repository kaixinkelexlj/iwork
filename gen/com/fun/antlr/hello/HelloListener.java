// Generated from /Users/didi/iworkspace/ifun/iwork/src/test/java/com/fun/antlr/hello/Hello.g4 by ANTLR 4.7
package com.fun.antlr.hello;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link HelloParser}.
 */
public interface HelloListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code helloIdentity}
	 * labeled alternative in {@link HelloParser#r}.
	 * @param ctx the parse tree
	 */
	void enterHelloIdentity(HelloParser.HelloIdentityContext ctx);
	/**
	 * Exit a parse tree produced by the {@code helloIdentity}
	 * labeled alternative in {@link HelloParser#r}.
	 * @param ctx the parse tree
	 */
	void exitHelloIdentity(HelloParser.HelloIdentityContext ctx);
}