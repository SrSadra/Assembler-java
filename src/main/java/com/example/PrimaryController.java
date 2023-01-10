package com.example;

import java.io.File;
import java.io.IOException;

import javax.swing.DefaultDesktopManager;
import javax.swing.plaf.DesktopPaneUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import java.awt.*;

public class PrimaryController {

    public static File f;

    @FXML
    Hyperlink hyperLink;

    @FXML
    private void switchToAssembler() throws IOException {
        App.setRoot("Assembler");
        // ac.readFile();
    }

    @FXML
    public void openFile() throws IOException {
        FileChooser chooser = new FileChooser();
        ExtensionFilter extFilter = new ExtensionFilter("TEXT files mannnnn (*.txt)", "*.txt");
        chooser.getExtensionFilters().add(extFilter);
        f =  chooser.showOpenDialog(null);
        switchToAssembler();

    }

    @FXML
    public void openHyperlink() throws IOException{
        Desktop des = Desktop.getDesktop();
        des.browse(java.net.URI.create("https://github.com/SrSadra"));
    }





}
