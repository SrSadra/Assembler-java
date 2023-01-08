package com.example;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

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
                    Parent root = null;
                    // try{
                    //     root = FXMLLoader.load(getClass().getResource("primary.fxml")); 
                    // }
                    // catch (IOException e){
                    //     e.printStackTrace();
                    // }
                    // Scene scene = new Scene(root);
                    // Stage stage = new Stage();
                    // stage.setScene(scene);
                    // stage.show();
                    try {
                        App.setRoot("primary");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // stackRoot.getScene().getWindow().hide();
                    
                }
            });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }

    }


}
