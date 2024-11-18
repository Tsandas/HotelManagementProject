package org.example.hotelmanagementproject;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.hotelmanagementproject.Utils.YamlManager;
import org.yaml.snakeyaml.Yaml;
import org.example.hotelmanagementproject.Utils.Rooms;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookRoom {

    private void refresh() throws IOException {
        Stage stage = (Stage) btnAddBooking.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("BookRoom.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Book Room");
        stage.setResizable(false);
        stage.setScene(scene);
    }

    @FXML
    private TableView<Rooms> bookedRoomsTable;
    @FXML
    private TableColumn<Rooms, Integer> colRoomId;
    @FXML
    private TableColumn<Rooms, String> colRoomType;
    @FXML
    private TableColumn<Rooms, String> colAmenities;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnAddBooking;
    @FXML
    private TextField txtRoomIdToAdd;
    @FXML
    private Button btnMyBookings;


    @FXML
    private void initialize() throws IOException {
        colRoomId.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        colRoomType.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        colAmenities.setCellValueFactory(new PropertyValueFactory<>("amenities"));

        loadBookedRooms();
    }

    private void loadBookedRooms() throws IOException {
        List<Rooms> rooms = YamlManager.getRoomList();
        List<Rooms> bookedRooms = rooms.stream()
                .filter(room -> room.getAvailable())
                .collect(Collectors.toList());
        bookedRoomsTable.getItems().setAll(bookedRooms);
    }

    @FXML
    private void onButtonBack() throws IOException {
        Stage stage = (Stage) btnBack.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("UserHomePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("User Home Page");
        stage.setResizable(false);
        stage.setScene(scene);
    }

    @FXML
    private void onButtonMyBookings() throws IOException {

        Stage stage = (Stage) btnMyBookings.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MyBookings.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("My Bookings");
        stage.setResizable(false);
        stage.setScene(scene);

    }

    @FXML
    private void onButtonBookRoom() throws IOException {

        int roomId = Integer.parseInt(txtRoomIdToAdd.getText());

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Room Status");
        alert.setHeaderText("Room Status");
        alert.setContentText("Are you sure you want to book this room?");

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");
        alert.getButtonTypes().setAll(yesButton, noButton);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == yesButton) {
            YamlManager.changeRoomAvailabilityToFalse(roomId);
            System.out.println("Room ID " + roomId + " has been to your bookings.");
        } else {
            System.out.println("Room removal canceled.");
        }
        refresh();

    }

}
