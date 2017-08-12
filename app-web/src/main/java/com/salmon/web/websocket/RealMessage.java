package com.salmon.web.websocket;

import java.io.Serializable;

public class RealMessage implements Serializable{

    private String name;

    public RealMessage(){

    }
    public RealMessage(String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
