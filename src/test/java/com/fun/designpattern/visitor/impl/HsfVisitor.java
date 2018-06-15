package com.fun.designpattern.visitor.impl;

import java.util.List;

import com.fun.designpattern.visitor.Visitable;
import com.fun.designpattern.visitor.Visitor;

public class HsfVisitor implements Visitor{

    @Override
    public void visit(StringElement element) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void visit(FloatElement element) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void visit(List<? extends Visitable> elementList) {
        // TODO Auto-generated method stub
        for(Visitable element : elementList){
            element.accept(this);
        }
    }

    
    
}
