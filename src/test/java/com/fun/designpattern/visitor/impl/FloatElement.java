package com.fun.designpattern.visitor.impl;

import com.fun.designpattern.visitor.Visitable;
import com.fun.designpattern.visitor.Visitor;

public class FloatElement implements Visitable{

    @Override
    public void accept(Visitor visitor) {
        // TODO Auto-generated method stub
        visitor.visit(this);
    }

}
