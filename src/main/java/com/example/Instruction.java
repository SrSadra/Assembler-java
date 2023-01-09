package com.example;

import java.util.ArrayList;

public class Instruction {

    static Data data; //problem static?

    Instruction(){
        data = new Data();
    }

    public void movFunc(String destination , String source){
        // String des = reg.registerType(destination);
        int desType = data.registerSize(destination); //duplicated code
        String desNum = data.registerType(destination);
        int[] src = new int[8];
        int[] res = new int[8];
        if (data.registerType(source) != null){
            System.out.println("hehe");
            src = data.regValue(source);
        }     
        else {
            System.out.println("hoho");
            src = AssembellyLogic.baseConverter(source);
            // ta inja okey
        }       
        int i;
            if (desType != 42){
                i = 0;
            }
            else {
                i = 2;
            }  
            int ind = 7;
            for (; i < src.length ; i++){
                res[7 - i] = src[ind];
                System.out.println("%" + src[ind]);
                ind--;
            }
        data.initilizeReg(res, desNum);
    }

    public void addFunc(String destination , String source){
        int desType = data.registerSize(destination); //duplicated code
        String desNum = data.registerType(destination);
        int[] src = new int[8];
        int[] res = new int[8];
        if (data.registerType(source) != null){
            src = data.regValue(source);
        }     
        else {
            src = AssembellyLogic.baseConverter(source);
        } 
        int[] des = data.regValue(destination);
        int carry = 0;
        int k = 0;
        if (desType == 42){
            k = 2;
        }
        for (int i = 7 ; i >= 0 ; i--){
            int value = (des[i - k]) + (src[i]) + carry;
            carry = 0;
            int diff = value - 16;
            int resTmp;
            if (diff > 0){
                carry = 1;
                resTmp = diff;
            }
            else{
                resTmp = value;
            }
            res[i] = resTmp;
            if ((i == 6) && ((desType / 10) == 4)){
                break;
            }
            if ((i == 4) && (desType == 0)){
                break;
            }
        }
        data.initilizeReg(res, desNum);
    }

    // public ArrayList<Object> initializing(string ){

    // }
}