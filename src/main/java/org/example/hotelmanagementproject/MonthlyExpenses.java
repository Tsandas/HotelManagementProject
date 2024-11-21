package org.example.hotelmanagementproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.hotelmanagementproject.Utils.YamlManager;

import java.io.IOException;


public class MonthlyExpenses {

    private void refresh(Button btn) throws IOException {
        Stage stage = (Stage) btn.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MonthlyExpenses.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Monthly Expenses");
        stage.setResizable(false);
        stage.setScene(scene);
    }

    @FXML
    private Button btnBack;
    @FXML
    private Button btnRoomAvailability;
    @FXML
    private Button btnStaffManager;
    @FXML
    private Button btnUpdateCS;
    @FXML
    private Button btnUpdateEB;
    @FXML
    private Button btnUpdateIS;
    @FXML
    private Button btnUpdateMS;
    @FXML
    private Button btnUpdateWB;
    @FXML
    private TextField txtCleaningValue;
    @FXML
    private TextField txtElectricityValue;
    @FXML
    private TextField txtInternetValue;
    @FXML
    private TextField txtMaintenanceValue;
    @FXML
    private TextField txtWaterValue;
    @FXML
    private Label lblCS;
    @FXML
    private Label lblEB;
    @FXML
    private Label lblIS;
    @FXML
    private Label lblMS;
    @FXML
    private Label lblWB;

    @FXML
    private void onButtonBack() throws IOException {
        Stage stage = (Stage) btnBack.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AdminHomePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Admin Dashboard");
        stage.setResizable(false);
        stage.setScene(scene);
    }

    public void onButtonRoomAvailability() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("RoomAvailability.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) btnRoomAvailability.getScene().getWindow();
        stage.setTitle("Room Availability");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void onButtonStaffManager() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("StaffManagerPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) btnStaffManager.getScene().getWindow();
        stage.setTitle("Saff Manager");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void initialize() throws IOException {
        System.out.println(YamlManager.getExpensesMap());
        lblEB.setText("$" + YamlManager.getExpensesMap().get("Electricity Bill"));
        lblWB.setText("$" + YamlManager.getExpensesMap().get("Water Bill"));
        lblIS.setText("$" + YamlManager.getExpensesMap().get("Internet Service"));
        lblMS.setText("$" + YamlManager.getExpensesMap().get("Maintenance Supplies"));
        lblCS.setText("$" + YamlManager.getExpensesMap().get("Cleaning Services"));
    }

    @FXML
    private void onButtonUpdateEL() throws IOException {
        if(!txtElectricityValue.getText().isEmpty() && txtElectricityValue.getText().matches("\\d+(\\.\\d+)?")) {
            String electricityBill = txtElectricityValue.getText();
            YamlManager.updateExpenseValue("Electricity Bill", Double.valueOf(electricityBill));
        }else{
            expenseValueAlert();
        }
        refresh(btnUpdateEB);
    }

    @FXML
    private void onButtonUpdateWB() throws IOException {
        if(!txtWaterValue.getText().isEmpty() && txtWaterValue.getText().matches("\\d+(\\.\\d+)?")) {
            String electricityBill = txtWaterValue.getText();
            YamlManager.updateExpenseValue("Water Bill", Double.valueOf(electricityBill));
        }else{
            expenseValueAlert();
        }
        refresh(btnUpdateWB);
    }

    @FXML
    private void onButtonUpdateIS() throws IOException {
        if(!txtInternetValue.getText().isEmpty() && txtInternetValue.getText().matches("\\d+(\\.\\d+)?")) {
            String electricityBill = txtInternetValue.getText();
            YamlManager.updateExpenseValue("Internet Service", Double.valueOf(electricityBill));
        }else{
            expenseValueAlert();
        }
        refresh(btnUpdateIS);
    }

    @FXML
    private void onButtonUpdateMS() throws IOException {
        if(!txtMaintenanceValue.getText().isEmpty() && txtMaintenanceValue.getText().matches("\\d+(\\.\\d+)?")) {
            String electricityBill = txtMaintenanceValue.getText();
            YamlManager.updateExpenseValue("Maintenance Supplies", Double.valueOf(electricityBill));
        }else{
            expenseValueAlert();
        }
        refresh(btnUpdateMS);
    }

    @FXML
    private void onButtonUpdateCS() throws IOException {
        if(!txtCleaningValue.getText().isEmpty() && txtCleaningValue.getText().matches("\\d+(\\.\\d+)?")) {
            String electricityBill = txtCleaningValue.getText();
            YamlManager.updateExpenseValue("Cleaning Services", Double.valueOf(electricityBill));
        }else{
            expenseValueAlert();
        }
        refresh(btnUpdateCS);
    }

    private void expenseValueAlert(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Updating Expenses");
        alert.setHeaderText("Expense value");
        alert.setContentText("Please provide a valid value");
        alert.showAndWait();
    }



}
