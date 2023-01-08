package com.example;

public class Register {
    private int[] regValue;
    private int type;
    private String regName;

    Register(String regName){
        this.regName = regName;
        System.out.println(regName);
    }

    public void setRegValue(int[] regValue) {
        this.regValue = regValue;
    }

    public String getRegName() {
        return regName;
    }

    public int[] getRegValue() {
        return regValue;
    }
}
