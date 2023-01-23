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
        lastValue.put("eax", "00000000h");
        lastValue.put("ebx", "00000000h");
        lastValue.put("ecx", "00000000h");
        lastValue.put("edx", "00000000h");
        lastValue.put("edi", "00000000h");
        lastValue.put("CY", "&");
        lastValue.put("PF", "&");
        lastValue.put("ZF", "&");
        lastValue.put("SF", "&");
        lastValue.put("OV", "&");
        setZeroRegFlg();
    }

    public void setZeroRegFlg(){
        String[] alph = {"a" , "b" , "c" , "d" };
        String[] flg = {"CY" , "PF" , "ZF" , "SF" , "OV"};
        for (int i = 0 ; i < 5 ; i++){
            int[] value = {0,0,0,0,0,0,0,0};
            String str;
            if (i == 4){
                str = "edi";
            }
            else {
                str = "e" + alph[i] + "x";
            }
            Register reg;
            if (register.get(str) == null){
                reg = new Register(str);
            }
            else {
                reg = register.get(str);
            }
            reg.setRegValue(value);
            register.put(str, reg );
        }
        for (int i = 0 ; i < 5; i++){
            Flag flags;
            if (flag.get(flg[i]) == null){
                flags = new Flag(flg[i]);
            }
            else {
                flags = flag.get(flg[i]);
                flags.setSet(0);
            }
            flag.put(flg[i], flags);
        }
    }


    // public String registerType(String reg){

    // }

    public void initilizeReg(int[] res, String regNum){
        Register value = register.get(regNum);
        System.out.println("register name " + regNum);
        value.setRegValue(res);
        register.put(regNum, value);
    }

    public void updateFlags(int[] reg , int desType , String carry , String overFlag , String parity , String zero){
        if (zero.equals("zero")){
            setFlag("ZF" , 1);
        }
        if (parity.equals("parity")){
            setFlag("PF", 1);
        }
        int desLast = reg[7 - (desType / 10) - (desType % 10)];
        if (desLast >= 8){
            setFlag("SF" , 1);
        }
        if (carry.equals("carry")){
            setFlag("CY" , 1);
        }
        if (overFlag.equals("overflow")){
            setFlag("OV" , 1);
        }
    }

    public void setFlag(String name , int set){
        Flag myFlag = flag.get(name);
        myFlag.setSet(set);
        flag.put(name, myFlag);
    }

    public int registerSize(String reg){
        if (regValue(reg) == null){
            return -1;
        }
        String lastChar = reg.substring(reg.length() - 1);
        if ((lastChar.equals("i")) || (lastChar.equals("x") && !(reg.charAt(0) == 'e'))){
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
        if ((reg.length() == 2) && (reg.charAt(1) == 'i')){
            return "e" + reg.substring(0, 1) + "i";
        }
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
        System.out.println("srctype is " + srcType + "destype is" + desType);
        int srcLast = 0;
        int desLast = 0;
        if (srcType == -1){
            srcLast = src[0];
        }
        else {
            srcLast = src[7 - (srcType / 10) - (srcType % 10)];
        }
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
