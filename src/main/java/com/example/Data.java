package com.example;

import java.util.HashMap;

public class Data {

    //registers with hexadecimal values
    // private int[] EAX;
    // private int[] EBX;
    private HashMap<String , int[]> variable;
    private HashMap<String , Register> register;

    Data(){
        variable = new HashMap<>();
        register = new HashMap<>();
        setZeroReg();
    }

    private void setZeroReg(){
        String[] alph = {"a" , "b" , "c" , "d"};
        for (int i = 0 ; i < 4 ; i++){
            String str = "e" + alph[i] + "x";
            int[] value = {0,0,0,0,0,0,0,0};
            Register reg = new Register(str);
            reg.setRegValue(value);
            register.put(str, reg );
        }
    }

    // public String registerType(String reg){

    // }

    public void initilizeReg(int[] res, String regNum){
        Register value = register.get(regNum);
        value.setRegValue(res);
        register.put(regNum, value);
    }

    public int registerSize(String reg){
        String lastChar = reg.substring(reg.length() - 1);
        if (lastChar.equals("x")){
            return 0;
        }
        else if (lastChar.equals("l")){
            return 41;
        }
        else if (lastChar.equals("h")){
            return 42;
        }
        else {
            return 4;
        }
    }

    public String registerType(String reg){
        System.out.println(reg);
        if (register.get(reg) == null){
            return null;
        }
        return register.get(reg).getRegName();
    }

    public String regValue(String reg){
        int[] value = new int[8];
        if (register.get(reg) == null){
            value = variable.get(reg);
        }
        else {
            value = register.get(reg).getRegValue();
        }
        String str = "";
        for (int i = 0 ; i < 8 ; i++){
            str += value[i];
        }
        str += "h";
        return str;
    }
    
}
