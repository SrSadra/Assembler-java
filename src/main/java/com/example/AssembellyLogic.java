package com.example;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import model.OrderType;

public class AssembellyLogic {
    // Data data = new Data();
    private Instruction ins = new Instruction();
    private Data data = Instruction.data;
    public HashMap<String , String> lastValue = data.lastValue;
    private ArrayList<Integer> breakPoints = new ArrayList<>();
    private int indBreak = 0;
    private String[] codeTostring;

    public void addBreakpoints(int breakp){
        breakPoints.add(breakp);
    }
    
    
    public void readCode(String codeData , int lineNum){
        if (codeData != null){
            codeTostring = codeData.split("\n");
        }
        if (lineNum == 0){
            for (int i = 0 ; i < codeTostring.length ; i++){
                codeDecoding(codeTostring[i]);
            }
        }
        else {
            codeDecoding(codeTostring[indBreak]);
        }
    }

    public boolean updateBreakPoints(){
        indBreak += 1;
        if (indBreak == breakPoints.size()){
            indBreak = 0;
            return false;
        }
        return true;
    }


    public void codeDecoding(String codeTmp){                
        String[] codeLine = new String[4];
        String regex = "\\s+";
        codeTmp = codeTmp.replaceAll(regex, " ");
        codeLine = codeTmp.split(" ");
        String orderSyn = codeLine[0].toLowerCase();
        if (orderSyn.equals("comment")){
            //comment func
        }
        else {
            String destination = codeLine[1].substring(0 , codeLine[1].length() - 1);
            System.out.println(destination + "-");
            String source = codeLine[2];
            System.out.println(source);
            syntaxReader(orderSyn , destination , source);
        }
    }

    // public void debugCode(String codeData){
    //     for (int i = 0 ; i < breakPoints.size() ; i++){
    //         int lineNum = breakPoints.get(i);
    //         readCode(codeData, lineNum);
    //     }
    // }

    public void syntaxReader(String syn , String destination , String source ){
        switch (syn){
            case "add":
            ins.addFunc(destination, source);
            break;
            case "mov":
            ins.movFunc(destination, source);
            break;
            case "sub":
            ins.subFunc(destination, source);
            break;
            case "and":
            ins.andFunc(destination, source);
            break;
            case "or":
            ins.orFunc(destination, source);
            break;
        }
    }

    

    public String regValue(String reg){
        return data.regValuetoString(reg);
    }

    public int flagValue(String flag){
        return data.flagValue(flag);
    }

    public static int[] baseConverter(String numb){
        int[] res = new int[8];
        String radix = numb.substring(numb.length() - 1);
        numb = numb.substring(0 , numb.length() - 1);
        if (radix.equals("h")){
            for (int k = 7, i = numb.length() - 1 ; i >= 0 ; i-- , k--){
                if (((int)numb.charAt(i)) > 57){
                    res[k] = ((int) numb.charAt(i)) - 55 ;
                }
                else {
                    res[k] = numb.charAt(i) - '0';
                }
                
            }
        }
        else {
            int radixToint = 10;
            switch (radix){
                case "q":
                radixToint = 8;
                break;
                case "o":
                radixToint = 8;
                break;
                case "b":
                radixToint = 2;
                break;
            }
            int numbInt = Integer.parseInt(numb, radixToint);
            int k = 7; 
            while (numbInt != 0){
                int rem = numbInt % 16;
                res[k] = rem;
                numbInt /= 16;
                k--;
            }
        }
        return res;
    }
}
