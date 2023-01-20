package com.example;

import java.util.HashMap;

public class Data {

    //registers with hexadecimal values
    // private int[] EAX;
    // private int[] EBX;
    private HashMap<String , int[]> variable;
    private HashMap<String , Register> register;
    private HashMap<String , Flag> flag;
    public HashMap<String , String> lastValue;

    Data(){
        variable = new HashMap<>();
        register = new HashMap<>();
        flag = new HashMap<>();
        lastValue = new HashMap<>();
        lastValue.put("eax", "&");
        lastValue.put("ebx", "&");
        lastValue.put("ecx", "&");
        lastValue.put("edx", "&");
        lastValue.put("CY", "&");
        lastValue.put("PF", "&");
        lastValue.put("ZF", "&");
        lastValue.put("SF", "&");
        lastValue.put("OV", "&");
        setZeroReg();
    }

    private void setZeroReg(){
        String[] alph = {"a" , "b" , "c" , "d"};
        String[] flg = {"carry" , "zero" , "sign" , "parity" , "overflow"};
        for (int i = 0 ; i < 4 ; i++){
            String str = "e" + alph[i] + "x";
            int[] value = {0,0,0,0,0,0,0,0};
            Register reg = new Register(str);
            reg.setRegValue(value);
            register.put(str, reg );
        }
        for (int i = 0 ; i < 5; i++){
            Flag flags = new Flag(flg[i]);
            flag.put(flg[i], flags);
        }
    }

    // public String registerType(String reg){

    // }

    public void initilizeReg(int[] res, String regNum){
        Register value = register.get(regNum);
        value.setRegValue(res);
        register.put(regNum, value);
    }

    public void updateFlags(int[] reg , String carry , String overFlag){
        int zeroCnt = 0;
        int parityCnt = 0;
        for (int i = 0 ; i < reg.length ; i++){
            if (reg[i] == 0){
                zeroCnt += 1;
            }
            if (reg[i] == 1){
                parityCnt += 1;
            }
        }
        if (zeroCnt == 8){
            setFlag("zero");
        }
        if (parityCnt % 2 == 1){
            setFlag("parity");
        }
        if (reg[0] >= 8){
            setFlag("sign");
        }
        if (carry.equals("carry")){
            setFlag(carry);
        }
        if (overFlag.equals("overflow")){
            setFlag(overFlag);
        }
    }

    public void setFlag(String name){
        Flag myFlag = flag.get(name);
        myFlag.setSet(1);
        flag.put(name, myFlag);
    }

    public int registerSize(String reg){
        if (regValue(reg) == null){
            return -1;
        }
        String lastChar = reg.substring(reg.length() - 1);
        if (lastChar.equals("x")){
            return 30;
        }
        else if (lastChar.equals("l")){
            return 10;
        }
        else if (lastChar.equals("h")){
            return 12;
        }
        else {
            return 70;
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
        else if (register.get(getRealName(reg)) != null){
            value = register.get(getRealName(reg)).getRegValue();
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

    public int flagValue(String flags){
        System.out.println(flags);
        return flag.get(flags).getSet();
    }

    public static int findLastDigit(int desType , int[] des , int srcType , int[] src){
        int srcLast = 0;
        int desLast = 0;
        srcLast = src[7 - (srcType / 10) - (srcType % 10)];
        desLast = des[7 - (desType / 10) - (desType % 10)];
        if (srcLast >= 8 && (desLast >= 8)){
            return -1;
        }
        else if (srcLast < 8 && desLast < 8){
            return 1;
        }
        else {
            return 0;
        }
    }
    
}
