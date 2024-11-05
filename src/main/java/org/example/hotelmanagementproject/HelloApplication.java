package org.example.hotelmanagementproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("LoginStage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Path cssUrl = Paths.get("src/main/resources/css/LoginStage.css");
        scene.getStylesheets().add(cssUrl.toUri().toString());
        stage.setTitle("HotelHub");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}