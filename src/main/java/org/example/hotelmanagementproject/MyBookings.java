package org.example.hotelmanagementproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.hotelmanagementproject.Utils.Rooms;
import org.example.hotelmanagementproject.Utils.YamlManager;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MyBookings {

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
    private TextField txtRoomIdToRemove;
    @FXML
    private Button btnRemoveBooking;
    @FXML
    private Button btnBookRooms;

    private void refresh() throws IOException {
        Stage stage = (Stage) btnRemoveBooking.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MyBookings.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("HotelHub");
        stage.setResizable(false);
        stage.setScene(scene);
    }

    @FXML
    private void onButtonBack() throws IOException {
        Stage stage = (Stage) btnBack.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("UserHomePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("HotelHub");
        stage.setResizable(false);
        stage.setScene(scene);
    }

    @FXML
    void onButtonBookRooms() throws IOException {

        Stage stage = (Stage) btnBookRooms.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("BookRoom.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("HotelHub");
        stage.setResizable(false);
        stage.setScene(scene);

    }

    @FXML
    private void initialize() throws IOException {
        colRoomId.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        colRoomType.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        colAmenities.setCellValueFactory(new PropertyValueFactory<>("amenities"));

        loadBookedRooms();
    }

    private void loadBookedRooms() throws IOException {
        List<Rooms> rooms = YamlManager.getRoomList();
        List<Rooms> bookedRooms = rooms.stream().filter(room -> !room.getAvailable()).collect(Collectors.toList());
        bookedRoomsTable.getItems().setAll(bookedRooms);
    }

    @FXML
    private void onButtonRemoveBooking() throws IOException {
        int roomId = Integer.parseInt(txtRoomIdToRemove.getText());

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Room Status");
        alert.setHeaderText("Room Status");
        alert.setContentText("Are you sure you want to remove this room from your bookings?");

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");
        alert.getButtonTypes().setAll(yesButton, noButton);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == yesButton) {
            YamlManager.changeRoomAvailabilityToTrue(roomId);
            System.out.println("Room ID " + roomId + " has been removed from bookings and availability set to true.");
        } else {
            System.out.println("Room removal canceled.");
        }
        refresh();
    }

}