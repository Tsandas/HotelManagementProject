package org.example.hotelmanagementproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.hotelmanagementproject.Utils.YamlManager;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class RoomAvailability {

    @FXML
    private Button btnBack;

    @FXML
    private TableView<Rooms> roomsTable;
    @FXML
    private TableColumn<Rooms, Integer> colRoomId;
    @FXML
    private TableColumn<Rooms, String> colRoomType;
    @FXML
    private TableColumn<Rooms, String> colAmenities;
    @FXML
    private TableColumn<Rooms, Boolean> colAvailability;

    @FXML
    private TextField txtAmenities;

    @FXML
    private TextField txtAvailability;

    @FXML
    private TextField txtRoomId;

    @FXML
    private TextField txtRoomType;

    @FXML
    public void initialize() {
        colRoomId.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        colRoomType.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        colAmenities.setCellValueFactory(new PropertyValueFactory<>("amenities"));
        colAvailability.setCellValueFactory(new PropertyValueFactory<>("available"));

        loadBookedRooms();
    }

    private void loadBookedRooms() {
        List<Rooms> rooms = getRoomList();
        List<Rooms> allRooms = rooms.stream()
                .collect(Collectors.toList());
        roomsTable.getItems().setAll(allRooms);
    }

    public static List<Rooms> getRoomList() {
        InputStream inputStream = RoomAvailability.class.getResourceAsStream("/Data/rooms.yaml");
        if (inputStream == null) {
            throw new RuntimeException("YAML file not found.");
        }

        Yaml yaml = new Yaml();
        Map<String, List<Map<String, Object>>> data = yaml.load(inputStream);
        List<Map<String, Object>> roomsData = data.get("rooms");
        List<Rooms> roomsList = new ArrayList<>();

        for (Map<String, Object> roomData : roomsData) {
            int roomId = (int) roomData.get("room_id");
            boolean available = (boolean) roomData.get("available");
            String roomType = (String) roomData.get("room_type");
            List<String> amenitiesList = (List<String>) roomData.get("amenities");
            String amenities = String.join(", ", amenitiesList);

            Rooms room = new Rooms(roomId, roomType, amenities, available);
            roomsList.add(room);
        }
        return roomsList;
    }

    public static class Rooms {

        private int roomId;
        public String roomType;
        private String amenities;
        private boolean available;

        public Rooms(int roomId, String roomType, String amenities, boolean available) {
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

        public boolean getAvailable() {
            return available;
        }

        public void setRoomId(int roomId) {
            this.roomId = roomId;
        }

        public void setAmenities(String amenities) {
            this.amenities = amenities;
        }

        public void setRoomType(String roomType) {
            this.roomType = roomType;
        }

        public void setAvailable(Boolean available) {
            this.available = available;
        }
    }

    @FXML
    private void onButtonBack() throws IOException {
        Stage stage = (Stage) btnBack.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AdminHomePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Admin Dashboard");
        stage.setResizable(false);
        stage.setScene(scene);
    }

    public void onButtonRemoveRoom() {

        if (txtRoomId.getText() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Remove room warning");
            alert.setHeaderText("Room Id");
            alert.setContentText("Provide valid room Id");
            alert.showAndWait();
        }

        try {
            int removedRoom = Integer.parseInt(txtRoomId.getText());
            if (YamlManager.roomAvailable(removedRoom)) {
                YamlManager.removeRoomById(removedRoom);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Remove room warning");
                alert.setHeaderText("Room Id");
                alert.setContentText("Provide valid room Id, where the room is not currently occupied");
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Remove room warning");
            alert.setHeaderText("Room Id");
            alert.setContentText("Provide valid room Id, where the room is not currently occupied");
            alert.showAndWait();
        }

    }

//    @FXML
//    private TextField txtAmenities;
//
//    @FXML
//    private TextField txtAvailability;
//
//    @FXML
//    private TextField txtRoomId;
//
//    @FXML
//    private TextField txtRoomType;

    public void onButtonAdd(){

        //add room
        //YamlManager.addRoom(120,true,"double", Arrays.asList("hoes", "TV", "Mini-bar"));
        boolean av = Boolean.parseBoolean(txtAvailability.getText());
        int id  = Integer.parseInt(txtRoomId.getText());
        String rt = String.valueOf(txtRoomType.getText());
        List<String> s = Collections.singletonList(txtAmenities.getText());
        System.out.println(id + " " + av + " " + rt + " " + s);
        YamlManager.addRoom(id,av,rt, s);

    }


}
