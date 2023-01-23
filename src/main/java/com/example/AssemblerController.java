package com.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
import org.fxmisc.richtext.*;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;


public class AssemblerController extends Stage implements Initializable {

    // @FXML
    // public void openFile() throws IOException {
    //     FileChooser chooser = new FileChooser();
    //     ExtensionFilter extFilter = new ExtensionFilter("TEXT files mannnnn (*.txt)", "*.txt");
    //     chooser.getExtensionFilters().add(extFilter);
    //     File f =  chooser.showOpenDialog(null);
        

    // }
    private File f;
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
    // @FXML
    // private Button bpAddButt;
    // @FXML
    // private Button bpDelButt;
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

    // private static JTextArea textArea;
    // private static JTextArea lines;
    // private JScrollPane jsp;

    // public void setCodeView() {
    //    jsp = new JScrollPane();
    // //    textArea = new JTextArea();
    //    lines = new JTextArea("1");
    //    lines.setBackground(Color.LIGHT_GRAY);
    //    lines.setEditable(false);
    //    //  Code to implement line numbers inside the JTextArea
    //    codeArea.textProperty().addListener(new EventListener(){
    //       public String getText() {
    //          int caretPosition = textArea.getDocument().getLength();
    //          Element root = textArea.getDocument().getDefaultRootElement();
    //          String text = "1" + System.getProperty("line.separator");
    //             for(int i = 2; i < root.getElementIndex(caretPosition) + 2; i++) {
    //                text += i + System.getProperty("line.separator");
    //             }
    //          return text;
    //       }
    //       @Override
    //       public void changedUpdate(ActionEvent de) {
    //          lines.setText(getText());
    //       }
    //       @Override
    //       public void insertUpdate de) {
    //          lines.setText(getText());
    //       }
    //       @Override
    //       public void removeUpdate(ActionEvent de) {
    //          lines.setText(getText());
    //       }
    //     @Override
    //     public void handleEvent(Event evt) {
    //         // TODO Auto-generated method stub
            
    //     }
    //    });
    //    jsp.getViewport().add(textArea);
    //    jsp.setRowHeaderView(lines);
    //    add(jsp);
    //    setSize(400, 275);
    //    setLocationRelativeTo(null);
    //    setVisible(true);
    // }
    
    public void readFile(){
        f = PrimaryController.f;
        // InputStream inputStream = new FileInputStream(f);
        // String data = ClassLoader.read
        try {
            System.out.println(f);
            BufferedReader reader = new BufferedReader(new FileReader(f));
            String line;
            
            while ((line = reader.readLine()) != null){
                codeArea.appendText(lineNum + "- ");
                codeArea.appendText(line);                
                codeArea.appendText("\n");
                lineNum ++;
            }
            codeArea.appendText(lineNum + "- ");
            baseCode = codeArea.getText();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }



    @FXML
    public void onCodeTextCng(){
        // System.out.println("alo");
        // String text = codeArea.getText();
        // String[] tmp = text.split("\n");
        // System.out.println(tmp.length + "and" + lineNum);
        // int diff =  tmp.length - lineNum;
        // if (diff == 0){
        //     codeArea.appendText("\n");
        //     codeArea.appendText(lineNum + 1 + "- ");
        //     return;
        // }
        // codeArea.setText("");
        // System.out.println(tmp[0] + "andd" + tmp[1] +"-");
        // for (int i = 0 ; i < lineNum - 1; i++){
        //     codeArea.appendText(tmp[i]);
        // }
        // codeArea.appendText("\n");
        // for (int i = 0 ; i < diff ; i++){
        //     codeArea.appendText(lineNum + i - 1 + "- ");
        //     codeArea.appendText(tmp[i + lineNum - 1]);
        //     codeArea.appendText("\n");
        // }
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

    public void onRunClick(){
        setVisibleRegFlg(true);
        aLogic.readCode(codeArea.getText() , 0);
        setRegisters();
        setFlags();
        aLogic.resetFlag();
    }

    public void onDebugClick(){
        showDebugButt();
        aLogic.readCode(codeArea.getText(), 1);
        setRegisters();
        setFlags();
        setVisibleRegFlg(true);
        aLogic.resetFlag();
    }

    public void onStopClick(){
        aLogic.setIndBreak(0);
        setVisibleRegFlg(false);
        aLogic.setZeroRegFlg();
        invisibleDebugButt();
    }

    public void setVisibleRegFlg(boolean status){
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

    public void nextLineCode(){
        if (!aLogic.updateBreakPoints()){
            invisibleDebugButt();
            aLogic.setZeroRegFlg();
            setVisibleRegFlg(false);
            return;
        }
        aLogic.readCode(null, 1 );
        setRegisters();
        setFlags();
        setVisibleRegFlg(true);
        aLogic.resetFlag();
    }

    public void showDebugButt(){
        contBP.setVisible(true);
        StopBP.setVisible(true);
        retBP.setVisible(true);
    }

    public void invisibleDebugButt(){
        contBP.setVisible(false);
        StopBP.setVisible(false);
        retBP.setVisible(false);
    }

    public void setFlags(){
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
        contBPImage = new Image("file:D:\\assembler\\demo\\src\\main\\resources\\com\\pics\\nextarrow.png");
        contBP.setImage(contBPImage);
        StopBP.setText("◻");
        retBP.setText("⟲");
    }

    


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        readFile();
        paths();
        invisibleDebugButt();
    }


}