package com.example;

import java.util.ArrayList;
import java.util.HashMap;

public class Instruction {

    static Data data; //problem static?

    Instruction(){
        data = new Data();
    }

    public HashMap<String , Object> init(String destination , String source){
        HashMap<String , Object> hm = new HashMap<>();
        int desType = data.registerSize(destination); 
        String desNum = data.registerType(destination);
        if (desNum == null){
            desNum = destination;
        }        
        int[] des = new int[8];
        des = data.regValue(desNum);
        int[] src = new int[8];
        int srcType = data.registerSize(source);
        src = data.regValue(source);
        if (src == null){
            src = AssembellyLogic.baseConverter(source);
        }
        hm.put("desType", desType);
        hm.put("desNum", desNum);
        hm.put("des", des);
        hm.put("srcType", srcType);
        hm.put("src", src);
        return hm;
    }

    public void movFunc(String destination , String source){
        HashMap<String , Object> hmData = init(destination, source);
        int desType = (int) hmData.get("desType");
        String desNum = (String) hmData.get("desNum");
        int[] src = (int[]) hmData.get("src");
        int srcType = (int) hmData.get("srcType");
        int[] des = (int[]) hmData.get("des");
        System.out.println("src" + srcType + "des" + desType);
        int i = desType % 10 , k = srcType != -1 ? srcType % 10 : 0;
        System.out.println(desType);
            // if (desType == 42){
            //     i = 2;
            // }
            // System.out.println(srcType);
            // if (srcType == 42){
            //     k = 2;
            // }
            for (int ind = 7 ; ind >= 0 ; ind--){
                System.out.println("i" + i + "ind " + ind + "k" + k);
                des[ind - i] = src[ind - k];
                System.out.println("%" + src[ind - k]);
                if ((ind == 6) && ((desType / 10) == 1)){
                    break;
                }
                if ((ind == 4) && (desType == 30)){
                    break;
                }
                
            }
        data.initilizeReg(des, desNum);
    }

    public void addFunc(String destination , String source){
        HashMap<String , Object> hmData = init(destination, source);
        int desType = (int) hmData.get("desType");
        String desNum = (String) hmData.get("desNum");
        int[] des = (int[]) hmData.get("des");
        int[] src = (int[]) hmData.get("src");
        int srcType = (int) hmData.get("srcType");
        int carry = 0;
        int indDes = desType % 10;
        int indSrc = srcType != -1 ? srcType % 10 : 0;
        int lastDigit = Data.findLastDigit(desType, des, srcType, src);
        for (int i = 7 ; i >= 0 ; i--){
            int value = (des[i - indDes]) + (src[i - indSrc]) + carry;
            carry = 0;
            int diff = value - 16;
            int resTmp;
            if (diff >= 0){
                carry = 1;
                resTmp = diff;
            }
            else{
                resTmp = value;
            }
            des[i - indDes] = resTmp;
            if ((i == 6) && ((desType / 10) == 1)){
                break;
            }
            if ((i == 4) && (desType == 30)){
                break;
            }
        }

        //flags analysing...
        String overFlag = "";
        if ((lastDigit > 0 && (des[7 - (desType / 10) - (desType % 10)] >= 8)) || (lastDigit < 0 && (des[7 - (desType / 10) - (desType % 10)] < 8))){
            overFlag = "overflow";
        }
        String carryFlg = "";
        if (carry == 1){
            carryFlg = "carry";
        }
        data.updateFlags(des , carryFlg , overFlag);
        //
        data.initilizeReg(des, desNum);
    }

    public void subFunc(String destination , String source){
        HashMap<String , Object> hmData = init(destination, source);
        int[] src = (int[]) hmData.get("src");
        int[] twosSrc = new int[8];
        for (int i = 0 ; i < src.length ; i++){
            twosSrc[i] = 15 - src[i];
            System.out.println("-" + twosSrc[i]);
        }
        int flg = 1;
        int i = 7;
        do{
            twosSrc[i] +=  flg;
            if (twosSrc[i] > 15){
                twosSrc[i] = twosSrc[i] - 16;
                flg = 1;
                i--;
            }
            else {
                flg = 0;
            }
        }
        while (flg != 0);
        String twoSrcStr = "";
        for (int k = 0 ; k < twosSrc.length ; k++){
            if (twosSrc[k] >= 10){
                twoSrcStr += String.valueOf((char) (twosSrc[k] + 55));
            }
            else {
                twoSrcStr += twosSrc[k];
            }
            System.out.println("$" + twoSrcStr);
        }
        addFunc(destination, twoSrcStr + "h");
    }

    public void andFunc(String destination , String source){
        System.out.println("alo");
        HashMap<String , Object> hmData = init(destination, source);
        int desType = (int) hmData.get("desType");
        String desNum = (String) hmData.get("desNum");
        int[] des = (int[]) hmData.get("des");
        int[] src = (int[]) hmData.get("src");
        int srcType = (int) hmData.get("srcType");
        int indSrc = srcType != -1 ? srcType % 10 : 0 , indDes = desType % 10;
        for (int i = 7 ; i >= 0 ; i--){
            des[i - indDes] = src[i - indSrc] & des[i - indDes];
            System.out.println("p" + des[i - indDes] + " - " + src[i - indSrc]);
            if ((i == 6) && ((desType / 10) == 1)){
                break;
            }
            if ((i == 4) && (desType == 30)){
                break;
            }
        }
        data.initilizeReg(des, desNum);
    }
    
    public void orFunc(String destination , String source){
        HashMap<String , Object> hmData = init(destination, source);
        int desType = (int) hmData.get("desType");
        String desNum = (String) hmData.get("desNum");
        int[] des = (int[]) hmData.get("des");
        int[] src = (int[]) hmData.get("src");
        int srcType = (int) hmData.get("srcType");
        int indSrc = srcType != -1 ? srcType % 10 : 0 , indDes = desType % 10;
        for (int i = 7 ; i >= 0 ; i--){
            des[i - indDes] = src[i - indSrc] | des[i - indDes];
            if ((i == 6) && ((desType / 10) == 1)){
                break;
            }
            if ((i == 4) && (desType == 30)){
                break;
            }
        }
        data.initilizeReg(des, desNum);
    }

    // public ArrayList<Object> initializing(string ){

    // }
}