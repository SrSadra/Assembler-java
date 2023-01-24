package com.example;
import java.util.HashMap;

public class AssembellyLogic {
    private Instruction ins = new Instruction();
    private Breakpoint bp = new Breakpoint();
    private Data data = Instruction.data;
    public HashMap<String , String> lastValue = data.lastValue;
    private HashMap<String, Integer> labelJmp = new HashMap<>();
    private HashMap<String , Integer> labelBp = new HashMap<>();
    private int indCurr = -2; // breakpoints traverse index
    private String[] codeTostring;
    private String[] orders = {"mov" , "add" , "sub" , "and" , "or" , "jmp"};
    

    public void addBreakpoints(int breakp){
        bp.addBreakpoints(breakp); //breakp with index style
    }

    public void delBreakpoints(int breakp){
        bp.delBreakpoints(breakp);
    }

    public void setZeroRegFlg(){ //refreshing all registers and flags and current line pointer
        indCurr = -2;
        data.setZeroRegFlg();
    }
    
    
    public int readCode(String codeData , int lineNum){//traverse on code lines
        if (codeData != null){
            codeTostring = codeData.split("\n");
        }
            int i = 0;
            if (indCurr != -2){
                i = indCurr;
            }
            for (; i < codeTostring.length ; i++){
                int iPast = i;
                i = codeDecoding(codeTostring[i] , i);
                if (lineNum != 0 && bp.getBreakPoints() == iPast){ //debug line
                    indCurr = i + 1;
                    return iPast;
                }
                if (i != codeTostring.length - 1){
                    resetFlag();
                }
            }
            return -1;
    }

    public void setIndBreak(int num){ //set breakpoints index
        bp.setIndBreak(num);
    }

    public boolean updateBreakPoints(){
        return bp.updateBreakPoints();
    }



    public int codeDecoding(String codeTmp , int lineNum){//decoding line of code and pass every element to appropriate function            
        String[] codeLine = new String[4];
        String regex = "\\s+";
        codeTmp = codeTmp.replaceAll(regex, " ");
        codeLine = codeTmp.split(" ");
        String firstIn = codeLine[0].toLowerCase();
        String secondIn = codeLine[1].toLowerCase();
        String orderSyn = "";
        String labelSyn;
        String source = "";
        String destination = "";
        int flg = 0 , sourceInd = 0;
        for (int i = 0 ; i < orders.length ; i++){
            if (orders[i].equals(firstIn)){ //if we dont have label
                flg = 1; 
                orderSyn = orders[i];
                destination = secondIn;
                sourceInd = 2;
                break;
            }
        }
        if (flg == 0){ //if we have label
            labelSyn = firstIn;
            orderSyn = secondIn;
            destination = codeLine[2].toLowerCase();
            sourceInd = 3;
            String labelStr = labelSyn.substring(0 , labelSyn.length() - 1);
            labelJmp.put(labelStr , lineNum);
            labelBp.put(labelStr, bp.lastBp(lineNum)); //last breakpoint before label
        }
        if (orderSyn.equals("comment")){
            //comment func coming soon...
        }
        else if (orderSyn.equals("jmp")){
            bp.setIndBreak(labelBp.get(destination));
            return labelJmp.get(destination) - 1;
        }
        else {
            source = codeLine[sourceInd];
            destination = destination.substring(0 , destination.length() - 1);
            System.out.println(destination + "-");
            System.out.println(source);
            syntaxReader(orderSyn , destination , source);
        }
        return lineNum;
    }

    public void resetFlag(){
        data.setFlag("SF", 0);
        data.setFlag("ZF", 0);
        data.setFlag("PF", 0);
        data.setFlag("CY", 0);
        data.setFlag("OV", 0);
    }

    public void syntaxReader(String syn , String destination , String source ){ //passing source and destination to appropriate instruction
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

    

    public String regValue(String reg){ //get register value 
        return data.regValuetoString(reg);
    }

    public int flagValue(String flag){
        return data.flagValue(flag);
    }

    public static int[] baseConverter(String numb){ //convert numbers on every base to 8 size array (digits like A in hexadecimal will conver to 10 in array)
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
