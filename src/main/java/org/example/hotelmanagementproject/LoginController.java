package org.example.hotelmanagementproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;


public class LoginController {

    private final String adminUsername = "admin";
    private final String adminPassword = "admin";
    private final String userUsername = "user";
    private final String userPassword = "user";

    private boolean btnAdminPressed = false;
    private boolean btnUserPressed = false;

    @FXML
    private Button btnAdmin;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnUser;
    @FXML
    private TextField textFieldPassword;
    @FXML
    private TextField textFieldUsername;

    @FXML
    private void initialize() {
        // Set up button actions
        btnAdmin.setOnAction(event -> handleButtonActivation(btnAdmin, "admin"));
        btnUser.setOnAction(event -> handleButtonActivation(btnUser, "user"));
    }

    private String selectedRole = "";

    private void handleButtonActivation(Button button, String role) {
        // Reset button states without reassigning event handlers
        btnAdmin.getStyleClass().remove("active");
        btnUser.getStyleClass().remove("active");
        btnAdmin.setDisable(false);
        btnUser.setDisable(false);

        // Activate the clicked button
        button.getStyleClass().add("active");
        button.setDisable(true);
        selectedRole = role;
    }

    @FXML
    private void onClickLogin() throws IOException {
        if (textFieldUsername.getText().isEmpty() || textFieldPassword.getText().isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Login Warning");
            alert.setHeaderText("Incomplete Login Details");
            alert.setContentText("Please enter both username and password.");
            alert.showAndWait();
        } else {
            if ("admin".equals(selectedRole)) {
                if (Objects.equals(textFieldUsername.getText(), adminUsername) &&
                        Objects.equals(textFieldPassword.getText(), adminPassword)) {
                    Stage stage = (Stage) btnLogin.getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AdminHomePage.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    stage.setTitle("Admin Home Page");
                    stage.setResizable(true);
                    stage.setScene(scene);
                } else {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Login Warning");
                    alert.setHeaderText("Wrong Credentials");
                    alert.setContentText("Incorrect admin credentials. Please try again.");
                    alert.showAndWait();
                }
            } else if ("user".equals(selectedRole)) {
                if (Objects.equals(textFieldUsername.getText(), userUsername) &&
                        Objects.equals(textFieldPassword.getText(), userPassword)) {
                    Stage stage = (Stage) btnLogin.getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("UserHomePage.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    stage.setTitle("Admin Home Page");
                    stage.setResizable(true);
                    stage.setScene(scene);
                } else {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Login Warning");
                    alert.setHeaderText("Wrong Credentials");
                    alert.setContentText("Incorrect user credentials. Please try again.");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Login Information");
                alert.setHeaderText("Role Not Selected");
                alert.setContentText("Please select Admin or User before logging in.");
                alert.showAndWait();
            }
        }
    }

}