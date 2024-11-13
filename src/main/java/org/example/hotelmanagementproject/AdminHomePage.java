package org.example.hotelmanagementproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import org.example.hotelmanagementproject.Utils.YamlManager;
import org.yaml.snakeyaml.Yaml;
import java.io.InputStream;
import java.util.List;
import java.util.Map;



public class AdminHomePage {

    @FXML private Label totalStaffLabel;
    @FXML private PieChart roomAvailabilityChart;
    @FXML private Label monthlyExpensesLabel;


    public void initialize() {
        totalStaffLabel.setText(String.format("Total staff: %s  Total monthly salaries: $%s",YamlManager.getTotalStaff(),YamlManager.staffMonthlySalaryTotal()));
        monthlyExpensesLabel.setText(String.format("Total monthly expenses: $%s",YamlManager.getMonthlyExpenses()));


        int available = YamlManager.roomAvailability().get("available");
        int total = YamlManager.roomAvailability().get("total");
        PieChart.Data availableRooms = new PieChart.Data("Available", (double) available /total);
        PieChart.Data bookedRooms = new PieChart.Data("Booked", 1 - (double) available /total);
        roomAvailabilityChart.getData().addAll(availableRooms, bookedRooms);

    }





}
