package org.example.hotelmanagementproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class About {

    @FXML
    Button btnBack;

    @FXML
    void onButtonBack() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("LoginStage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Path cssUrl = Paths.get("src/main/resources/css/LoginStage.css");
        scene.getStylesheets().add(cssUrl.toUri().toString());
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.setTitle("HotelHub");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }


}
