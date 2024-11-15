package org.example.hotelmanagementproject.Utils;

import org.example.hotelmanagementproject.AdminHomePage;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YamlManager {

    public static Map<String, Integer> roomAvailability() {
        InputStream inputStream = AdminHomePage.class.getResourceAsStream("/Data/rooms.yaml");
        if (inputStream == null) {
            throw new RuntimeException("YAML file not found.");
        }
        Yaml yaml = new Yaml();
        Map<String, List<Map<String, Object>>> data = yaml.load(inputStream);

        List<Map<String, Object>> rooms = data.get("rooms");
        int totalRooms = rooms.size();
        long availableRooms = rooms.stream()
                .filter(room -> Boolean.TRUE.equals(room.get("available")))
                .count();
        Map<String, Integer> result = new HashMap<>();
        result.put("total", totalRooms);
        result.put("available", (int) availableRooms);
        return result;
    }

    public static boolean roomAvailable(int targetRoom) {
        InputStream inputStream = AdminHomePage.class.getResourceAsStream("/Data/rooms.yaml");
        if (inputStream == null) {
            throw new RuntimeException("YAML file not found.");
        }
        Yaml yaml = new Yaml();
        Map<String, List<Map<String, Object>>> data = yaml.load(inputStream);

        List<Map<String, Object>> rooms = data.get("rooms");
        return rooms.stream()
                .filter(room -> room.get("room_id").equals(targetRoom))
                .map(room -> (Boolean) room.get("available"))
                .findFirst()
                .orElse(false);
    }

    public static void removeRoomById(int targetRoom) {
        InputStream inputStream = AdminHomePage.class.getResourceAsStream("/Data/rooms.yaml");
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
            options.setPrettyFlow(true);
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

    public static void addRoom(int roomId, boolean available, String roomType, List<String> amenities) {
        InputStream inputStream = AdminHomePage.class.getResourceAsStream("/Data/rooms.yaml");
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


    public static Double staffMonthlySalaryTotal() {
        InputStream inputStream = AdminHomePage.class.getResourceAsStream("/Data/staff.yaml");
        if (inputStream == null) {
            throw new RuntimeException("YAML file not found.");
        }
        Yaml yaml = new Yaml();
        Map<String, List<Map<String, Object>>> data = yaml.load(inputStream);

        List<Map<String, Object>> staffList = data.get("staff");
        double totalSalaries = 0;
        for (Map<String, Object> staff : staffList) {
            int pay = (int) staff.get("pay");
            totalSalaries += pay;
        }
        return totalSalaries;
    }

    public static int getTotalStaff() {
        InputStream inputStream = AdminHomePage.class.getResourceAsStream("/Data/staff.yaml");
        if (inputStream == null) {
            throw new RuntimeException("YAML file not found.");
        }
        Yaml yaml = new Yaml();
        Map<String, List<Map<String, Object>>> data = yaml.load(inputStream);
        List<Map<String, Object>> staffList = data.get("staff");
        return staffList.size();
    }


    public static double getMonthlyExpenses() {
        InputStream inputStream = AdminHomePage.class.getResourceAsStream("/Data/expenses.yaml");
        if (inputStream == null) {
            throw new RuntimeException("YAML file not found.");
        }
        Yaml yaml = new Yaml();
        Map<String, List<Map<String, Object>>> data = yaml.load(inputStream);
        List<Map<String, Object>> expenses = data.get("expenses");
        int totalValue = expenses.stream()
                .mapToInt(expense -> (int) expense.get("value"))
                .sum();
        totalValue += staffMonthlySalaryTotal();
        return totalValue;
    }

}
