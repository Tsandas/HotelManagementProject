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
    @FXML
    private Button btnMonthlyExpenses;
    @FXML
    private Button btnStaffManager;


    public void initialize() throws IOException {
        totalStaffLabel.setText(String.format("Total staff: %s  Total monthly salaries: $%s", YamlManager.getTotalStaff(), YamlManager.staffMonthlySalaryTotal()));
        monthlyExpensesLabel.setText(String.format("Total monthly utility expenses: $%s", YamlManager.getMonthlyExpenses()));


        int available = YamlManager.roomAvailability().get("available");
        int total = YamlManager.roomAvailability().get("total");
        PieChart.Data availableRooms = new PieChart.Data("Available", (double) available / total);
        PieChart.Data bookedRooms = new PieChart.Data("Booked", 1 - (double) available / total);
        roomAvailabilityChart.getData().addAll(availableRooms, bookedRooms);
        bookedRooms.getNode().setStyle("-fx-pie-color: #1F2D3A;");
        availableRooms.getNode().setStyle("-fx-pie-color: #34495E;");
    }

    public void onButtonBack() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("LoginStage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.setTitle("HotelHub");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void onButtonRoomAvailability() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("RoomAvailability.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) btnRoomAvailability.getScene().getWindow();
        stage.setTitle("HotelHub");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void onButtonMonthlyExpenses() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MonthlyExpenses.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) btnMonthlyExpenses.getScene().getWindow();
        stage.setTitle("HotelHub");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void onButtonStaffManger() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("StaffManagerPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) btnStaffManager.getScene().getWindow();
        stage.setTitle("HotelHub");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }


}
