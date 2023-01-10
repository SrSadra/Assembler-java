package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Timer;

/**
 * JavaFX Assembully Studio Code
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        // setRoot("startlogo");
        scene = new Scene(loadFXML("startlogo"), 900, 600);
        stage.setTitle("Assembully Studio 2023");
        stage.getIcons().add(new Image("file:D:\\assembler\\demo\\src\\main\\resources\\com\\pics\\icon.png"));
        // stage.getIcons().add(new Image(App.class.getResourceAsStream("demo\\src\\main\\resources\\com\\pics\\icon.png")));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }       
     

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
        
    }

    public static void main(String[] args) {
        launch();
    }

}