package com.example;

import java.util.ArrayList;

public class Breakpoint {
    private ArrayList<Integer> breakPoints = new ArrayList<>();
    private int indBreak = 0;

    public void addBreakpoints(int breakp){
        breakPoints.add(breakp); //breakp with index style
    }

    public void delBreakpoints(int breakp){
        breakPoints.remove((Integer) breakp);
    }

    public int getBreakPoints() {
        return breakPoints.get(indBreak);
    }

    public int lastBp(int lineNum){ //last breakpoint after this line
        int lastlineBp = 0;
        for (int i = 0 ; i < breakPoints.size() ; i++){
            if (lineNum < breakPoints.get(i)){
                break;
            }
            lastlineBp = breakPoints.get(i);
        }
        return lastlineBp;
    }

    public int breakPointsSize(){
        return breakPoints.size();
    }

    public boolean updateBreakPoints(){ //update breakpoint index (if breakpoints finished then return false)
        indBreak += 1;
        if (indBreak == breakPoints.size()){
            indBreak = 0;
            return false;
        }
        return true;
    }

    public void setIndBreak(int indBreak) {
        this.indBreak = indBreak;
    }

    public int getIndBreak() {
        return indBreak;
    }

}
