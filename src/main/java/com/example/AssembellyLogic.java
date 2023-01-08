package com.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import model.OrderType;

public class AssembellyLogic {
    Data data = new Data();
    
    public void readCode(File file){
        try {
            System.out.println(file);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            String[] codeLine = new String[4];
            while ((line = reader.readLine()) != null){
                System.out.println(line);
                String regex = "\\s+";
                line = line.replaceAll(regex, " ");
                codeLine = line.split(" ");
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
            // System.out.println(reader.readLine());
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void syntaxReader(String syn , String destination , String source ){
        switch (syn){
            case "add":
            // add func
            break;
            case "mov":
            movFunc(destination, source);
            break;
        }
    }

    public void movFunc(String destination , String source){
        // String des = reg.registerType(destination);
        int desType = data.registerSize(destination);
        String desNum = data.registerType(destination);
        int[] res = new int[8];
        if (data.registerType(source) != null){
            source = data.regValue(source);
        }            
        source = source.substring(0, source.length() - 1);
        int i;
            if (desType != 42){
                i = 0;
            }
            else {
                i = 2;
            }  
            int ind = 0;
            for (; i < source.length() ; i++){
                res[7 - i] = Integer.parseInt(String.valueOf(source.charAt(source.length() - ind - 1)));
                ind++;
            }
        data.initilizeReg(res, desNum);
    }

    public String regValue(String reg){
        return data.regValue(reg);
    }
}
