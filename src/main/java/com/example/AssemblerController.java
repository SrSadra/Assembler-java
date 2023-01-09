package com.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class AssemblerController implements Initializable {

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
    

    
    public void readFile(){
        f = PrimaryController.f;
        // InputStream inputStream = new FileInputStream(f);
        // String data = ClassLoader.read
        try {
            System.out.println(f);
            BufferedReader reader = new BufferedReader(new FileReader(f));
            String line;
            while ((line = reader.readLine()) != null){
                codeArea.appendText(line);                
                codeArea.appendText("\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public void onDebugClick(){
        aLogic.readCode(f);
        eaxOut.setText("eax =" + " " + aLogic.regValue("eax"));

    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        readFile();
    }


}