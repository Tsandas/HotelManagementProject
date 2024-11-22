package org.example.hotelmanagementproject.Utils;

import javafx.scene.control.Alert;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class YamlManager {

    private  YamlManager(){}

    public static Map<String, Integer> roomAvailability() throws IOException {
        String yamlFilePath = Paths.get("src/main/resources/Data/rooms.yaml").toAbsolutePath().toString();
        InputStream inputStream = Files.newInputStream(Paths.get(yamlFilePath));
        if (inputStream == null) {
            throw new RuntimeException("YAML file not found.");
        }
        Yaml yaml = new Yaml();
        Map<String, List<Map<String, Object>>> data = yaml.load(inputStream);

        List<Map<String, Object>> rooms = data.get("rooms");
        int totalRooms = rooms.size();
        long availableRooms = rooms.stream().filter(room -> Boolean.TRUE.equals(room.get("available"))).count();
        Map<String, Integer> result = new HashMap<>();
        result.put("total", totalRooms);
        result.put("available", (int) availableRooms);
        return result;
    }

    public static boolean roomAvailable(int targetRoom) throws IOException {
        String yamlFilePath = Paths.get("src/main/resources/Data/rooms.yaml").toAbsolutePath().toString();
        InputStream inputStream = Files.newInputStream(Paths.get(yamlFilePath));
        if (inputStream == null) {
            throw new RuntimeException("YAML file not found.");
        }
        Yaml yaml = new Yaml();
        Map<String, List<Map<String, Object>>> data = yaml.load(inputStream);

        List<Map<String, Object>> rooms = data.get("rooms");
        return rooms.stream().filter(room -> room.get("room_id").equals(targetRoom)).map(room -> (Boolean) room.get("available")).findFirst().orElse(false);
    }

    public static void removeRoomById(int targetRoom) throws IOException {
        String yamlFilePath = Paths.get("src/main/resources/Data/rooms.yaml").toAbsolutePath().toString();
        InputStream inputStream = Files.newInputStream(Paths.get(yamlFilePath));

        if (inputStream == null) {
            throw new RuntimeException("YAML file not found.");
        }
        Yaml yaml = new Yaml();
        Map<String, List<Map<String, Object>>> data = yaml.load(inputStream);

        List<Map<String, Object>> rooms = data.get("rooms");
        boolean removed = rooms.removeIf(room -> room.get("room_id").equals(targetRoom));

        if (removed) {
            DumperOptions options = new DumperOptions();
            options.setPrettyFlow(true);
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            Yaml yamlWriter = new Yaml(options);
            try (FileWriter writer = new FileWriter("src/main/resources/Data/rooms.yaml")) {
                yamlWriter.dump(data, writer);
                System.out.println("Room with ID " + targetRoom + " was successfully removed.");
            } catch (IOException e) {
                System.out.println("Error writing to the YAML file: " + e.getMessage());
            }
        } else {
            System.out.println("Room with ID " + targetRoom + " not found.");
        }
    }

    public static void addRoom(int roomId, boolean available, String roomType, List<String> amenities) throws IOException {
        String yamlFilePath = Paths.get("src/main/resources/Data/rooms.yaml").toAbsolutePath().toString();
        InputStream inputStream = Files.newInputStream(Paths.get(yamlFilePath));
        if (inputStream == null) {
            throw new RuntimeException("YAML file not found.");
        }

        Yaml yaml = new Yaml();
        Map<String, List<Map<String, Object>>> data = yaml.load(inputStream);

        List<Map<String, Object>> rooms = data.get("rooms");

        Map<String, Object> newRoom = new HashMap<>();
        newRoom.put("room_id", roomId);
        newRoom.put("available", available);
        newRoom.put("room_type", roomType);
        newRoom.put("amenities", amenities);

        rooms.add(newRoom);

        DumperOptions options = new DumperOptions();
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yamlWriter = new Yaml(options);

        try (FileWriter writer = new FileWriter("src/main/resources/Data/rooms.yaml")) {
            yamlWriter.dump(data, writer);
            System.out.println("Room with ID " + roomId + " was successfully added.");
        } catch (IOException e) {
            System.out.println("Error writing to the YAML file: " + e.getMessage());
        }
    }

    public static boolean roomExists(int roomId) throws IOException {
        String yamlFilePath = Paths.get("src/main/resources/Data/rooms.yaml").toAbsolutePath().toString();
        InputStream inputStream = Files.newInputStream(Paths.get(yamlFilePath));
        if (inputStream == null) {
            throw new RuntimeException("YAML file not found.");
        }
        Yaml yaml = new Yaml();
        Map<String, List<Map<String, Object>>> data = yaml.load(inputStream);
        List<Map<String, Object>> rooms = data.get("rooms");
        return rooms.stream().anyMatch(room -> room.get("room_id").equals(roomId));
    }

    public static void changeRoomAvailabilityToTrue(int roomId) throws IOException {
        String yamlFilePath = Paths.get("src/main/resources/Data/rooms.yaml").toAbsolutePath().toString();
        InputStream inputStream = Files.newInputStream(Paths.get(yamlFilePath));

        if (inputStream == null) {
            throw new RuntimeException("YAML file not found.");
        }

        Yaml yaml = new Yaml();
        Map<String, List<Map<String, Object>>> data = yaml.load(inputStream);

        List<Map<String, Object>> rooms = data.get("rooms");

        boolean found = false;
        boolean alreadyTrue = false;

        for (Map<String, Object> room : rooms) {
            if (room.get("room_id").equals(roomId)) {
                found = true;
                if (Boolean.TRUE.equals(room.get("available"))) {
                    alreadyTrue = true;
                } else {
                    room.put("available", true);
                }
                break;
            }
        }

        if (!found) {
            System.out.println("Room with ID " + roomId + " not found.");
        } else if (alreadyTrue) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Room Status");
            alert.setHeaderText("Room Status");
            alert.setContentText("This room is not on your bookings list");
            alert.showAndWait();
            System.out.println("Room with ID " + roomId + " is already available.");
        } else {
            DumperOptions options = new DumperOptions();
            options.setPrettyFlow(true);
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            Yaml yamlWriter = new Yaml(options);

            try (FileWriter writer = new FileWriter(yamlFilePath)) {
                yamlWriter.dump(data, writer);

                System.out.println("Room with ID " + roomId + " availability set to true.");
            } catch (IOException e) {
                System.out.println("Error writing to the YAML file: " + e.getMessage());
            }
        }
    }

    public static void changeRoomAvailabilityToFalse(int roomId) throws IOException {
        String yamlFilePath = Paths.get("src/main/resources/Data/rooms.yaml").toAbsolutePath().toString();
        InputStream inputStream = Files.newInputStream(Paths.get(yamlFilePath));

        if (inputStream == null) {
            throw new RuntimeException("YAML file not found.");
        }

        Yaml yaml = new Yaml();
        Map<String, List<Map<String, Object>>> data = yaml.load(inputStream);

        List<Map<String, Object>> rooms = data.get("rooms");

        boolean found = false;
        boolean alreadyFalse = false;

        for (Map<String, Object> room : rooms) {
            if (room.get("room_id").equals(roomId)) {
                found = true;
                if (Boolean.FALSE.equals(room.get("available"))) {
                    alreadyFalse = true;
                } else {
                    room.put("available", false);
                }
                break;
            }
        }

        if (!found) {
            System.out.println("Room with ID " + roomId + " not found.");
        } else if (alreadyFalse) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Room Status");
            alert.setHeaderText("Room Status");
            alert.setContentText("This room is already booked");
            alert.showAndWait();
            System.out.println("Room with ID " + roomId + " is not available.");
        } else {
            DumperOptions options = new DumperOptions();
            options.setPrettyFlow(true);
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            Yaml yamlWriter = new Yaml(options);

            try (FileWriter writer = new FileWriter(yamlFilePath)) {
                yamlWriter.dump(data, writer);

                System.out.println("Room with ID " + roomId + " availability set to false.");
            } catch (IOException e) {
                System.out.println("Error writing to the YAML file: " + e.getMessage());
            }
        }
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

    public static Double staffMonthlySalaryTotal() throws IOException {
        String yamlFilePath = Paths.get("src/main/resources/Data/staff.yaml").toAbsolutePath().toString();
        InputStream inputStream = Files.newInputStream(Paths.get(yamlFilePath));
        if (inputStream == null) {
            throw new RuntimeException("YAML file not found.");
        }
        Yaml yaml = new Yaml();
        Map<String, List<Map<String, Object>>> data = yaml.load(inputStream);

        List<Map<String, Object>> staffList = data.get("staff");
        double totalSalaries = 0;
        for (Map<String, Object> staff : staffList) {
            double pay = (Double) staff.get("pay");
            totalSalaries += pay;
        }
        return totalSalaries;
    }

    public static int getTotalStaff() throws IOException {
        String yamlFilePath = Paths.get("src/main/resources/Data/staff.yaml").toAbsolutePath().toString();
        InputStream inputStream = Files.newInputStream(Paths.get(yamlFilePath));
        if (inputStream == null) {
            throw new RuntimeException("YAML file not found.");
        }
        Yaml yaml = new Yaml();
        Map<String, List<Map<String, Object>>> data = yaml.load(inputStream);
        List<Map<String, Object>> staffList = data.get("staff");
        return staffList.size();
    }

    public static Map<String, Double> getExpensesMap() throws IOException {
        String yamlFilePath = Paths.get("src/main/resources/Data/expenses.yaml").toAbsolutePath().toString();
        InputStream inputStream = Files.newInputStream(Paths.get(yamlFilePath));
        if (inputStream == null) {
            throw new RuntimeException("YAML file not found.");
        }
        Yaml yaml = new Yaml();
        Map<String, List<Map<String, Object>>> data = yaml.load(inputStream);
        List<Map<String, Object>> expenses = data.get("expenses");

        return expenses.stream().collect(Collectors.toMap(exp -> (String) exp.get("expense"), exp -> (double) exp.get("value")));
    }

    public static void updateExpenseValue(String expenseName, double newValue) throws IOException {
        String yamlFilePath = Paths.get("src/main/resources/Data/expenses.yaml").toAbsolutePath().toString();
        InputStream inputStream = Files.newInputStream(Paths.get(yamlFilePath));

        if (inputStream == null) {
            throw new RuntimeException("YAML file not found.");
        }

        Yaml yaml = new Yaml();
        Map<String, List<Map<String, Object>>> data = yaml.load(inputStream);

        List<Map<String, Object>> expenses = data.get("expenses");

        boolean found = false;

        for (Map<String, Object> expense : expenses) {
            if (expense.get("expense").equals(expenseName)) {
                expense.put("value", newValue);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Expense with name \"" + expenseName + "\" not found.");
        } else {
            DumperOptions options = new DumperOptions();
            options.setPrettyFlow(true);
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            Yaml yamlWriter = new Yaml(options);

            try (FileWriter writer = new FileWriter(yamlFilePath)) {
                yamlWriter.dump(data, writer);
                System.out.println("Expense \"" + expenseName + "\" updated to value: " + newValue);
            } catch (IOException e) {
                System.out.println("Error writing to the YAML file: " + e.getMessage());
            }
        }
    }


    public static double getMonthlyExpenses() throws IOException {
        String yamlFilePath = Paths.get("src/main/resources/Data/expenses.yaml").toAbsolutePath().toString();
        InputStream inputStream = Files.newInputStream(Paths.get(yamlFilePath));
        if (inputStream == null) {
            throw new RuntimeException("YAML file not found.");
        }
        Yaml yaml = new Yaml();
        Map<String, List<Map<String, Object>>> data = yaml.load(inputStream);
        List<Map<String, Object>> expenses = data.get("expenses");
        return expenses.stream().mapToDouble(expense -> ((Number) expense.get("value")).doubleValue()).sum();
    }

    public static List<Staff> getStaffList() throws IOException {
        String yamlFilePath = Paths.get("src/main/resources/Data/staff.yaml").toAbsolutePath().toString();
        InputStream inputStream = Files.newInputStream(Paths.get(yamlFilePath));

        if (inputStream == null) {
            throw new RuntimeException("YAML file not found.");
        }

        Yaml yaml = new Yaml();
        Map<String, List<Map<String, Object>>> data = yaml.load(inputStream);
        List<Map<String, Object>> staffData = data.get("staff");
        List<Staff> staffList = new ArrayList<>();

        for (Map<String, Object> staff : staffData) {
            int id = (int) staff.get("id");
            String fullname = (String) staff.get("fullname");
            double pay = (double) staff.get("pay");
            String field = (String) staff.get("field");
            String phone = (String) staff.get("phone_number");

            Staff staffObj = new Staff(id, fullname, pay, field, phone);
            staffList.add(staffObj);
        }

        return staffList;
    }

    public static boolean staffExists(int targetId) throws IOException {
        String yamlFilePath = Paths.get("src/main/resources/Data/staff.yaml").toAbsolutePath().toString();
        InputStream inputStream = Files.newInputStream(Paths.get(yamlFilePath));

        if (inputStream == null) {
            throw new RuntimeException("YAML file not found.");
        }

        Yaml yaml = new Yaml();
        Map<String, List<Map<String, Object>>> data = yaml.load(inputStream);
        List<Map<String, Object>> staffData = data.get("staff");

        if (staffData == null) {
            throw new RuntimeException("Staff data not found in YAML file.");
        }

        return staffData.stream().anyMatch(staff -> Integer.valueOf(targetId).equals(staff.get("id")));

    }

    public static void removeStaffById(int targetId) throws IOException {
        String yamlFilePath = Paths.get("src/main/resources/Data/staff.yaml").toAbsolutePath().toString();
        InputStream inputStream = Files.newInputStream(Paths.get(yamlFilePath));

        if (inputStream == null) {
            throw new RuntimeException("YAML file not found.");
        }

        Yaml yaml = new Yaml();
        Map<String, List<Map<String, Object>>> data = yaml.load(inputStream);
        List<Map<String, Object>> staffData = data.get("staff");
        boolean removed = staffData.removeIf(staff -> staff.get("id").equals(targetId));

        if (removed) {
            DumperOptions options = new DumperOptions();
            options.setPrettyFlow(true);
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            Yaml yamlWriter = new Yaml(options);
            try (FileWriter writer = new FileWriter("src/main/resources/Data/staff.yaml")) {
                yamlWriter.dump(data, writer);
                System.out.println("Staff with ID " + targetId + " was successfully removed.");
            } catch (IOException e) {
                System.out.println("Error writing to the YAML file: " + e.getMessage());
            }
        } else {
            System.out.println("Staff with " + targetId + " was not found");
        }

    }

    public static void addStaff(int id, String fullname, double pay, String field, String phone) throws IOException {

        String yamlFilePath = Paths.get("src/main/resources/Data/staff.yaml").toAbsolutePath().toString();
        InputStream inputStream = Files.newInputStream(Paths.get(yamlFilePath));

        if (inputStream == null) {
            throw new RuntimeException("YAML file not found.");
        }

        Yaml yaml = new Yaml();
        Map<String, List<Map<String, Object>>> data = yaml.load(inputStream);
        List<Map<String, Object>> staffData = data.get("staff");

        Map<String, Object> newStaff = new HashMap<>();
        newStaff.put("id", id);
        newStaff.put("fullname", fullname);
        newStaff.put("pay", pay);
        newStaff.put("field", field);
        newStaff.put("phone_number", phone);

        staffData.add(newStaff);

        DumperOptions options = new DumperOptions();
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yamlWriter = new Yaml(options);

        try (FileWriter writer = new FileWriter("src/main/resources/Data/staff.yaml")) {
            yamlWriter.dump(data, writer);
            System.out.println("Staff with ID " + id + " was successfully added.");
        } catch (IOException e) {
            System.out.println("Error writing to the YAML file: " + e.getMessage());
        }

    }

}
