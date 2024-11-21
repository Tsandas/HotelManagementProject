package org.example.hotelmanagementproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class HelloApplication extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("LoginStage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("HotelHub");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }


}