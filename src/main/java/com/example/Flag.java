package com.example;

public class Flag {
    private int set;
    private String name;

    Flag(String name){
        this.name = name;
        this.set = 0;
    }

    public int getSet(){
        return set;
    }

    public void setSet(int set) {
        this.set = set;
    }

    public String getName() {
        return name;
    }

    
}
