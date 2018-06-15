package com.fun.designpattern.visitor;

import java.util.List;

import com.fun.designpattern.visitor.impl.FloatElement;
import com.fun.designpattern.visitor.impl.StringElement;


public interface Visitor {
    public void visit(StringElement element);
    public void visit(FloatElement element);
    public void visit(List<? extends Visitable> elementList);
}
