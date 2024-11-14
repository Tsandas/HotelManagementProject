package org.example.hotelmanagementproject.Utils;

import org.example.hotelmanagementproject.AdminHomePage;
import org.yaml.snakeyaml.Yaml;

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
