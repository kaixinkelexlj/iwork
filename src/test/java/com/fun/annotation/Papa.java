package com.fun.annotation;

import org.springframework.stereotype.Component;


@Component
public class Papa {
    
    @Yuanbao("I love you, papa!")
    private String firstWords;
    
    public void say(){
        if(firstWords == null){
            firstWords = "sorry, papa, I can't say...";
        }
        System.out.println("yuanbao say:" + firstWords);
    }
}
