package com.example;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.print.attribute.standard.Media;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.media.MediaPlayer;  
import javafx.scene.media.MediaView;

public class SplashController implements Initializable {

    // private StackPane stackRoot = new StackPane();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        new Splashscreen().start();
    }

    class Splashscreen extends Thread{
        
        @Override
        public void run(){
            try {
                Thread.sleep(3000);                        
                Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        App.setRoot("primary");


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    
                }
            });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }

    }


}
