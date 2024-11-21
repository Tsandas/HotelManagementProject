package org.example.hotelmanagementproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.hotelmanagementproject.Utils.Rooms;
import org.example.hotelmanagementproject.Utils.Staff;
import org.example.hotelmanagementproject.Utils.YamlManager;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class RoomAvailability {

    private ObservableList<Rooms> roomsObservableList = FXCollections.observableArrayList();

    private void refresh(Button btn) throws IOException {
        Stage stage = (Stage) btn.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("RoomAvailability.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Room Availability");
        stage.setResizable(false);
        stage.setScene(scene);
    }

    @FXML
    private Button btnBack;
    @FXML
    private Button btnRemoveRoom;
    @FXML
    private Button btnAddRoom;
    @FXML
    private Button btnMonthlyExpenses;
    @FXML
    private Button btnStaffManager;
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
    private void onButtonBack() throws IOException {
        Stage stage = (Stage) btnBack.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AdminHomePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Admin Dashboard");
        stage.setResizable(false);
        stage.setScene(scene);
    }

    public void onButtonMonthlyExpenses() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MonthlyExpenses.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) btnMonthlyExpenses.getScene().getWindow();
        stage.setTitle("Monthly Expenses");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void onButtonStaffManager() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("StaffManagerPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) btnStaffManager.getScene().getWindow();
        stage.setTitle("Staff Manager");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void initialize() throws IOException {
        colRoomId.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        colRoomType.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        colAmenities.setCellValueFactory(new PropertyValueFactory<>("amenities"));
        colAvailability.setCellValueFactory(new PropertyValueFactory<>("available"));

        loadBookedRooms();
    }

    private void loadBookedRooms() throws IOException {
        List<Rooms> rooms = getRoomList();
        roomsObservableList = FXCollections.observableArrayList(rooms);
        roomsTable.setItems(roomsObservableList);
        roomsTable.refresh();
    }

    public static List<Rooms> getRoomList() throws IOException {
        String yamlFilePath = Paths.get("src/main/resources/Data/rooms.yaml").toAbsolutePath().toString();
        InputStream inputStream = Files.newInputStream(Paths.get(yamlFilePath));

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
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Room Status");
                alert.setHeaderText("Room Status");
                alert.setContentText("Are you sure you want to remove this room from bookings?");
                ButtonType yesButton = new ButtonType("Yes");
                ButtonType noButton = new ButtonType("No");
                alert.getButtonTypes().setAll(yesButton, noButton);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == yesButton) {
                    YamlManager.removeRoomById(removedRoom);
                    System.out.println("Room ID " + removedRoom + " has been removed from bookings.");
                    refresh(btnRemoveRoom);
                } else {
                    System.out.println("Room removal canceled.");
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Remove room warning");
                alert.setHeaderText("Room Id");
                alert.setContentText("Provide valid room Id, where the room is not currently occupied");
                alert.showAndWait();
            }
        } catch (NumberFormatException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Remove room warning");
            alert.setHeaderText("Room Id");
            alert.setContentText("Provide valid room Id, where the room is not currently occupied");
            alert.showAndWait();
        }
    }

    public void onButtonAdd() throws IOException {

        if (!txtAvailability.getText().isEmpty() && !txtRoomId.getText().isEmpty() && !txtRoomId.getText().isEmpty() && !txtAmenities.getText().isEmpty()) {
            boolean av = Boolean.parseBoolean(txtAvailability.getText());
            int roomId = Integer.parseInt(txtRoomId.getText());
            String rt = String.valueOf(txtRoomType.getText());
            List<String> s = Collections.singletonList(txtAmenities.getText());


            if (!YamlManager.roomExists(roomId)) {
                System.out.println(roomId + " " + av + " " + rt + " " + s);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Room Status");
                alert.setHeaderText("Room Status");
                alert.setContentText("Are you sure you want to add this room to your bookings?");
                ButtonType yesButton = new ButtonType("Yes");
                ButtonType noButton = new ButtonType("No");
                alert.getButtonTypes().setAll(yesButton, noButton);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == yesButton) {
                    YamlManager.addRoom(roomId, av, rt, s);
                    System.out.println("Room ID " + roomId + " has been added for booking.");
                } else {
                    System.out.println("Adding room canceled.");
                }
                refresh(btnAddRoom);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Room warning");
                alert.setHeaderText("Room Id");
                alert.setContentText("A room with such id already exists");
                alert.showAndWait();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Room warning");
            alert.setHeaderText("Room input");
            alert.setContentText("Please fill all fields correctly");
            alert.showAndWait();
        }

    }

}
