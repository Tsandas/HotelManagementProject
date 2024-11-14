package org.example.hotelmanagementproject;


import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.List;
import java.util.stream.Collectors;


public class MyBookings {

    @FXML
    private TableView<Room> bookedRoomsTable;
    @FXML
    private TableColumn<Room, Integer> colRoomId;
    @FXML
    private TableColumn<Room, String> colRoomType;
    @FXML
    private TableColumn<Room, String> colAmenities;

    @FXML
    public void initialize() {
        // Initialize columns
        colRoomId.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        colRoomType.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        colAmenities.setCellValueFactory(new PropertyValueFactory<>("amenities"));

        // Populate the table with rooms that are booked (available = false)
        loadBookedRooms();
    }

    private void loadBookedRooms() {
        // Sample list of rooms. In real-world use, this would come from your model/data layer.
        List<Room> rooms = getRooms(); // Assuming you have a method to fetch rooms
        List<Room> bookedRooms = rooms.stream()
                .filter(room -> !room.isAvailable())
                .collect(Collectors.toList());

        bookedRoomsTable.getItems().setAll(bookedRooms);
    }

    // Sample method to fetch room data. Replace with actual data fetching logic.
    private List<Room> getRooms() {
        return List.of(
                new Room(101, "Single", "Wi-Fi, TV, Mini-bar", false),
                new Room(102, "Double", "Wi-Fi, TV", false),
                new Room(103, "Suite", "Wi-Fi, TV, Mini-bar, Jacuzzi", true),
                new Room(104, "Single", "Wi-Fi, TV", false)
        );
    }

    public static class Room {
        private final int roomId;
        private final String roomType;
        private final String amenities;
        private final boolean available;

        public Room(int roomId, String roomType, String amenities, boolean available) {
            this.roomId = roomId;
            this.roomType = roomType;
            this.amenities = amenities;
            this.available = available;
        }

        public int getRoomId() {
            return roomId;
        }

        public String getRoomType() {
            return roomType;
        }

        public String getAmenities() {
            return amenities;
        }

        public boolean isAvailable() {
            return available;
        }
    }

    @FXML
    private void onButtonBack(MouseEvent event) {
    }
}