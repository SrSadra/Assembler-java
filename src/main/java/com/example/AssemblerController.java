package com.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;


public class AssemblerController extends Stage implements Initializable {

    private File f = PrimaryController.f;
    private AssembellyLogic aLogic = new AssembellyLogic();
    
    @FXML
    TextArea codeArea;

    @FXML
    private Text eaxOut;
    @FXML
    private Text ebxOut;
    @FXML
    private Text ecxOut;
    @FXML
    private Text edxOut;
    @FXML
    private Text ediOut;
    @FXML
    private Text cy;
    @FXML
    private Text ov;
    @FXML
    private Text sf;
    @FXML
    private Text zf;
    @FXML
    private Text pf;
    @FXML
    private TextField bpInput;
    @FXML
    private VBox bpVbox;
    @FXML
    private Text StopBP;
    @FXML
    private ImageView contBP;
    @FXML
    private Text retBP;

    Image contBPImage;


    public int lineNum = 1;
    public String baseCode = "";
    private HashMap<String , Label> breakPoints = new HashMap<>();
    private String[] codes;

    public void readFile(){ //read file and convert it to textarea
        try {
            System.out.println(f);
            BufferedReader reader = new BufferedReader(new FileReader(f));
            String line;
            
            while ((line = reader.readLine()) != null){
                codeArea.appendText(line);                
                codeArea.appendText("\n");
                lineNum ++;
            }
            baseCode = codeArea.getText();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public void linePointer(int lineNum){ //line pointer for debug mode
        codeArea.setText("");
        for (int i = 0 ; i < codes.length ; i++){
            if (i == lineNum){
                codeArea.appendText(codes[i] + "   <-");
            }
            else {
                codeArea.appendText(codes[i]);
                
            }
            codeArea.appendText("\n");
        }
    }


    public void addBreakPoint(){
        int bpNum = Integer.parseInt(bpInput.getText());
        Label bpStatus = new Label();
        String str = "● BP at " + bpNum;
        bpStatus.setText("● BP at " + bpNum);
        bpVbox.getChildren().add(bpStatus);
        aLogic.addBreakpoints(bpNum - 1);
        breakPoints.put(str, bpStatus);
    }

    public void delBreakPoint(){
        int bpNum = Integer.parseInt(bpInput.getText());
        Label stopBP = breakPoints.get("● BP at " + bpNum);
        bpVbox.getChildren().remove(stopBP);
        breakPoints.remove("● BP at " + bpNum);
        aLogic.delBreakpoints(bpNum);
    }

    public void onRunClick(){ //run code
        setVisibleRegFlg(true);
        aLogic.readCode(codeArea.getText() , 0);
        setRegisters();
        setFlags();
        aLogic.setZeroRegFlg();
    }

    public void onDebugClick(){ //debug code
        codes = codeArea.getText().split("\n");
        DebugButtVis(true);
        int lineNum = aLogic.readCode(codeArea.getText(), 1);
        linePointer(lineNum);
        setRegisters();
        setFlags();
        setVisibleRegFlg(true);
        aLogic.resetFlag();
    }

    public void onStopClick(){
        aLogic.setIndBreak(0);
        setVisibleRegFlg(false);
        aLogic.setZeroRegFlg();
        DebugButtVis(false);
        linePointer(-1);
    }

    public void setVisibleRegFlg(boolean status){ //set all flags and registers visibale/invisible
        eaxOut.setVisible(status);
        ebxOut.setVisible(status);
        ecxOut.setVisible(status);
        edxOut.setVisible(status);
        ediOut.setVisible(status);
        cy.setVisible(status);
        ov.setVisible(status);
        sf.setVisible(status);
        zf.setVisible(status);
        pf.setVisible(status);
    }

    public void nextLineCode(){ // next line of breakpoint
        if (!aLogic.updateBreakPoints()){
            DebugButtVis(false);
            aLogic.setZeroRegFlg();
            setVisibleRegFlg(false);
            linePointer(-1);
            return;
        }
        int lineNum = aLogic.readCode(null, 1 );
        linePointer(lineNum);
        setRegisters();
        setFlags();
        setVisibleRegFlg(true);
        aLogic.resetFlag();
    }

    public void DebugButtVis(boolean status){ //button used for debug mode (stop , continiue) visible/invisible
        contBP.setVisible(status);
        StopBP.setVisible(status);
        retBP.setVisible(status);
    }


    public void setFlags(){ //set result flags
        HashMap<String , String> oldValue = aLogic.lastValue;
        int carryUp = aLogic.flagValue("CY");
        int signUp = aLogic.flagValue("SF");
        int zeroUp = aLogic.flagValue("ZF");
        int parityUp = aLogic.flagValue("PF");
        int overflowUp = aLogic.flagValue("OV");
        changeUpdateColor(Integer.toString(carryUp), cy, oldValue, "CY");
        changeUpdateColor(Integer.toString(signUp), sf, oldValue, "SF");
        changeUpdateColor(Integer.toString(zeroUp), zf, oldValue, "ZF");
        changeUpdateColor(Integer.toString(parityUp), pf, oldValue, "PF");
        changeUpdateColor(Integer.toString(overflowUp), ov, oldValue, "OV");
        oldValue.put("CY", Integer.toString(carryUp));
        oldValue.put("SF", Integer.toString(signUp));
        oldValue.put("ZF", Integer.toString(zeroUp));
        oldValue.put("PF", Integer.toString(parityUp));
        oldValue.put("OV", Integer.toString(overflowUp));
    }

    public void setRegisters(){
        HashMap<String , String> oldValue = aLogic.lastValue;
        String eaxUpdate = aLogic.regValue("eax");
        String ebxUpdate = aLogic.regValue("ebx");
        String ecxUpdate = aLogic.regValue("ecx");
        String edxUpdate = aLogic.regValue("edx");
        String ediUpdate = aLogic.regValue("edi");
        changeUpdateColor(eaxUpdate, eaxOut, oldValue, "eax");
        changeUpdateColor(ebxUpdate, ebxOut, oldValue, "ebx");
        changeUpdateColor(ecxUpdate, ecxOut, oldValue, "ecx");
        changeUpdateColor(edxUpdate, edxOut, oldValue, "edx");
        changeUpdateColor(ediUpdate, ediOut, oldValue, "edi");
        oldValue.put("eax", eaxUpdate);
        oldValue.put("ebx", ebxUpdate);
        oldValue.put("ecx", ecxUpdate);
        oldValue.put("edx", edxUpdate);
        oldValue.put("edi", ediUpdate);
    }

    public void changeUpdateColor(String newValue , Text text , HashMap<String , String> Oldvalue , String name){
        System.out.println(name);
        text.setText(name + " = " + newValue);
        System.out.println(newValue + "new value");
        if (!Oldvalue.get(name).equals(newValue) && !Oldvalue.get(name).equals("&") && !Oldvalue.get(name).equals("00000000h")){
            text.setFill(Color.RED);
        }
    }

    public void paths(){ 
        contBPImage = new Image("file:src\\main\\resources\\com\\pics\\nextarrow.png");
        contBP.setImage(contBPImage);
        StopBP.setText("◻");
        retBP.setText("⟲");
    }

    @FXML
    public void saveProject(){ //save project on desired destination
        ExtensionFilter extFilter = new ExtensionFilter("TEXT files mannnnn (*.txt)", "*.txt");
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(extFilter);
        chooser.setTitle("Choose location To Save Report");
        File selectedFile = null;
        while(selectedFile== null){
            selectedFile = chooser.showSaveDialog(null);
        }
        PrintWriter outFile = null;
        try {
            outFile = new PrintWriter(selectedFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String[] table = codeArea.getText().split("\n");
        for(int i = 0; i<table.length; i++){
            outFile.println(table[i]);
        }
        outFile.close();
    }

    @FXML
    public void openProject(){
        FileChooser chooser = new FileChooser();
        ExtensionFilter extFilter = new ExtensionFilter("TEXT files mannnnn (*.txt)", "*.txt");
        chooser.getExtensionFilters().add(extFilter);
        f =  chooser.showOpenDialog(null);
        readFile();
        DebugButtVis(false);
    }

    


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) { //what happen after changing scene
        if (f != null){
            readFile();
        }
        paths();
        DebugButtVis(false);
    }


}