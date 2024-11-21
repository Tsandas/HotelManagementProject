package org.example.hotelmanagementproject;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.hotelmanagementproject.Utils.Staff;
import org.example.hotelmanagementproject.Utils.YamlManager;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class StaffManager {

    private ObservableList<Staff> roomsObservableList = FXCollections.observableArrayList();
    @FXML
    private Button btnAddStaff;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnDeleteStaff;
    @FXML
    private Button btnMonthlyExpenses;
    @FXML
    private Button btnRoomAvailability;
    @FXML
    private TableColumn<Staff, String> fieldColumn;
    @FXML
    private TableColumn<Staff, String> fullnameColumn;
    @FXML
    private TableColumn<Staff, Double> payColumn;
    @FXML
    private TableColumn<Staff, String> phoneColumn;
    @FXML
    private TableColumn<Staff, Integer> idColumn;
    @FXML
    private TableView<Staff> staffTable;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtFullname;
    @FXML
    private TextField txtPay;
    @FXML
    private TextField txtPhone;
    @FXML
    private TextField txtField;

    private void refresh(Button btn) throws IOException {
        Stage stage = (Stage) btn.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("StaffManagerPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("HotelHub");
        stage.setResizable(false);
        stage.setScene(scene);
    }

    public void onButtonBack() throws IOException {
        Stage stage = (Stage) btnBack.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AdminHomePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("HotelHub");
        stage.setResizable(false);
        stage.setScene(scene);
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


    @FXML
    private void initialize() throws IOException {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        fullnameColumn.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        fieldColumn.setCellValueFactory(new PropertyValueFactory<>("field"));
        payColumn.setCellValueFactory(new PropertyValueFactory<>("pay"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        loadStaff();
    }

    private void loadStaff() throws IOException {
        List<Staff> staff = YamlManager.getStaffList();
        roomsObservableList = FXCollections.observableArrayList(staff);
        staffTable.setItems(roomsObservableList);
        staffTable.refresh();
    }

    public void onButtonRemoveStaff() throws IOException {
        if (!txtId.getText().isEmpty() && txtId.getText().matches("\\d+(\\.\\d+)?") && YamlManager.staffExists(Integer.parseInt(txtId.getText()))) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Staff Management");
            alert.setHeaderText("Staff employment");
            alert.setContentText("Are you sure you want to remove this staff?");
            ButtonType yesButton = new ButtonType("Yes");
            ButtonType noButton = new ButtonType("No");
            alert.getButtonTypes().setAll(yesButton, noButton);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == yesButton) {
                int targetId = Integer.parseInt(txtId.getText());
                YamlManager.removeStaffById(targetId);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Staff management");
            alert.setHeaderText("Input value");
            alert.setContentText("Please provide a valid value");
            alert.showAndWait();
        }
        refresh(btnDeleteStaff);
    }

    public void onButtonAddStaff() throws IOException {
        if (!txtId.getText().isEmpty() && !txtFullname.getText().isEmpty() && !txtField.getText().isEmpty() && !txtPay.getText().isEmpty() && !txtPhone.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Staff Management");
            alert.setHeaderText("Staff employment");
            alert.setContentText("Are you sure you want to add this staff?");
            ButtonType yesButton = new ButtonType("Yes");
            ButtonType noButton = new ButtonType("No");
            alert.getButtonTypes().setAll(yesButton, noButton);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == yesButton) {
                int id = Integer.parseInt(txtId.getText());
                String fullname = txtFullname.getText();
                double pay = Double.parseDouble(txtPay.getText());
                String field = txtField.getText();
                String phone = txtPhone.getText();
                YamlManager.addStaff(id, fullname, pay, field, phone);
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Staff management");
            alert.setHeaderText("Input value");
            alert.setContentText("Please provide a valid values");
            alert.showAndWait();
        }
        refresh(btnDeleteStaff);
    }


}
