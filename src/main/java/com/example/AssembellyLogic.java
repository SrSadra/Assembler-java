package com.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import model.OrderType;

public class AssembellyLogic {
    // Data data = new Data();
    Instruction ins = new Instruction();
    Data data = Instruction.data;
    
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
            ins.addFunc(destination, source);
            break;
            case "mov":
            ins.movFunc(destination, source);
            break;
        }
    }

    

    public String regValue(String reg){
        return data.regValuetoString(reg);
    }

    public static int[] baseConverter(String numb){
        int[] res = new int[8];
        String radix = numb.substring(numb.length() - 1);
        numb = numb.substring(0 , numb.length() - 1);
        if (radix.equals("h")){
            for (int k = 7, i = numb.length() - 1 ; i >= 0 ; i--){
                if (((int)numb.charAt(i)) > 57){
                    res[k] = ((int) numb.charAt(i)) - 55 ;
                }
                else {
                    res[k] = numb.charAt(i) - '0';
                }
                System.out.println("-" + res[k]);
            }
        }
        else {
            System.out.println("chimigi");
            int numbInt = Integer.parseInt(numb);
            int i = 0 , k = 7;
            while (numbInt != 0){
                int rem = numbInt % 16;
                res[k] = rem;
                numbInt /= 16;
                i++;
                k--;
            }
        }
        return res;
    }
}
