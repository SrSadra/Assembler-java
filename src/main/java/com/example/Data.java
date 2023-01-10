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
        if (register.get(reg) != null){
            return register.get(reg).getRegName();
        }
        Register regis = register.get(getRealName(reg));
        if (regis == null){
            return null;
        }
        return regis.getRegName();
    }

    public String getRealName(String reg){
        if ((reg.length() == 2) && ((reg.charAt(1) == 'x') || (reg.charAt(1) == 'l') || (reg.charAt(1) == 'h'))){
            return "e" + reg.substring(0 , 1) + "x";
        }
        else {
            return reg;
        }
    }

    public int[] regValue(String reg){
        int[] value = new int[8];
        if (register.get(reg) != null){
            value = register.get(reg).getRegValue();
        }
        else {
            value = variable.get(reg);
            
        }
        System.out.println(value);
        return value;
    }

    public String regValuetoString(String reg){
        int[] value = regValue(reg);
        String str = "";
        String digit;
        for (int i = 0 ; i < 8 ; i++){
            if (value[i] >= 10){
                digit = Character.toString(((char)(value[i] + 55)));
            }
            else {
                digit = Integer.toString(value[i]);
            }
            str += digit;
        }
        str += "h";
        return str;
    }
    
}
