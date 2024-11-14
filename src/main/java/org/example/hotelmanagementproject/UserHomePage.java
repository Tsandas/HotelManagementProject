package org.example.hotelmanagementproject;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.example.hotelmanagementproject.Utils.YamlManager;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UserHomePage {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnMyBookings;

    @FXML
    private Label roomsAvailableLabel;

    @FXML
    private Label roomsBookedLabel;

    public void initialize() {
        roomsAvailableLabel.setText(String.format("Rooms available for booking : %s", YamlManager.roomAvailability().get("total") - YamlManager.roomAvailability().get("available")));
        roomsBookedLabel.setText(String.format("Booked rooms : %s", YamlManager.roomAvailability().get("available")));
    }

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

    @FXML
    void onButtonMyBookings() throws IOException {

        Stage stage = (Stage) btnMyBookings.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MyBookings.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("My Bookings");
        stage.setResizable(false);
        stage.setScene(scene);

    }

}
