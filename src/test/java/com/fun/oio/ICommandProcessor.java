package com.fun.oio;

import java.io.OutputStream;

public interface ICommandProcessor {

	public void execute(OutputStream out, Request request) throws Exception;
}