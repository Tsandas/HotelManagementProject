package org.example.hotelmanagementproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.example.hotelmanagementproject.Utils.YamlManager;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


public class AdminHomePage {

    @FXML
    private Label totalStaffLabel;
    @FXML
    private PieChart roomAvailabilityChart;
    @FXML
    private Label monthlyExpensesLabel;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnRoomAvailability;


    public void initialize() {
        totalStaffLabel.setText(String.format("Total staff: %s  Total monthly salaries: $%s", YamlManager.getTotalStaff(), YamlManager.staffMonthlySalaryTotal()));
        monthlyExpensesLabel.setText(String.format("Total monthly expenses: $%s", YamlManager.getMonthlyExpenses()));


        int available = YamlManager.roomAvailability().get("available");
        int total = YamlManager.roomAvailability().get("total");
        PieChart.Data availableRooms = new PieChart.Data("Available", 1 - (double) available / total);
        PieChart.Data bookedRooms = new PieChart.Data("Booked", (double) available / total);
        roomAvailabilityChart.getData().addAll(availableRooms, bookedRooms);
    }

    public void onButtonBack() throws IOException {
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

    public void onButtonRoomAvailability() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("RoomAvailability.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Path cssUrl = Paths.get("src/main/resources/css/HomePage.css");
        scene.getStylesheets().add(cssUrl.toUri().toString());
        Stage stage = (Stage) btnRoomAvailability.getScene().getWindow();
        stage.setTitle("Room Availability");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }


}
