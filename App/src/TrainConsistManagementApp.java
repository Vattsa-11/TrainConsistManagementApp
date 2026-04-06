/**
 * ========================================================
 * MAIN CLASS - UseCase11TrainConsistMgmnt
 * ========================================================
 *
 * Use Case 11: Validate Train ID & Cargo Codes (Regex)
 *
 * Description:
 * This class validates Train ID and Cargo Code formats
 * using Regular Expressions (Pattern and Matcher).
 *
 * At this stage, the application:
 * - Defines regex pattern for Train ID: TRN-\d{4}
 * - Defines regex pattern for Cargo Code: PET-[A-Z]{2}
 * - Compiles patterns using Pattern class
 * - Creates Matcher objects for input strings
 * - Uses matches() to validate formats
 * - Displays whether input is valid or invalid
 *
 * This maps input format enforcement using Regex.
 *
 * @author Developer
 * @version 11.0
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TrainConsistManagementApp {

    // Bogie model with name, type and capacity
    static class Bogie {
        String name;
        String type;
        int    capacity;

        Bogie(String name, String type, int capacity) {
            this.name     = name;
            this.type     = type;
            this.capacity = capacity;
        }
    }

    // UC9: Reusable grouping method
    public static Map<String, List<Bogie>> groupByType(List<Bogie> bogies) {
        return bogies.stream()
                .collect(Collectors.groupingBy(b -> b.type));
    }

    // UC10: Reusable reduce method
    public static int countTotalSeats(List<Bogie> bogies) {
        return bogies.stream()
                .map(b -> b.capacity)
                .reduce(0, Integer::sum);
    }

    // UC11: Validate Train ID — format: TRN-1234 (TRN- + exactly 4 digits)
    public static boolean validateTrainID(String trainID) {
        Pattern pattern = Pattern.compile("TRN-\\d{4}");
        Matcher matcher = pattern.matcher(trainID);
        return matcher.matches();
    }

    // UC11: Validate Cargo Code — format: PET-AB (PET- + exactly 2 uppercase letters)
    public static boolean validateCargoCode(String cargoCode) {
        Pattern pattern = Pattern.compile("PET-[A-Z]{2}");
        Matcher matcher = pattern.matcher(cargoCode);
        return matcher.matches();
    }

    public static void main(String[] args) {

        // ---- UC9: Group Bogies by Type ----
        System.out.println("======================================");
        System.out.println(" UC9 - Group Bogies by Type (Collectors.groupingBy) ");
        System.out.println("======================================\n");

        List<Bogie> bogies = new ArrayList<>();
        bogies.add(new Bogie("Sleeper-1",     "Passenger", 72));
        bogies.add(new Bogie("Sleeper-2",     "Passenger", 72));
        bogies.add(new Bogie("AC Chair-1",    "Passenger", 56));
        bogies.add(new Bogie("First Class-1", "Passenger", 24));
        bogies.add(new Bogie("Rectangular-1", "Goods",    120));
        bogies.add(new Bogie("Cylindrical-1", "Goods",    100));

        Map<String, List<Bogie>> grouped = groupByType(bogies);

        System.out.println("Grouped Bogies by Type:");
        for (Map.Entry<String, List<Bogie>> entry : grouped.entrySet()) {
            System.out.println("\nType: " + entry.getKey());
            for (Bogie b : entry.getValue()) {
                System.out.println("  " + b.name + " -> Capacity: " + b.capacity);
            }
        }
        System.out.println("\nUC9 grouping completed...");

        // ---- UC10: Count Total Seats ----
        System.out.println("\n======================================");
        System.out.println(" UC10 - Count Total Seats in Train (reduce) ");
        System.out.println("======================================\n");

        int totalSeats = countTotalSeats(bogies);
        System.out.println("Total Seating Capacity: " + totalSeats);
        System.out.println("\nUC10 seat aggregation completed...");

        // ---- UC11: Validate Train ID & Cargo Codes (Regex) ----
        System.out.println("\n======================================");
        System.out.println(" UC11 - Validate Train ID & Cargo Codes (Regex) ");
        System.out.println("======================================\n");

        // Train ID validation
        String trainID1 = "TRN-1234";
        String trainID2 = "TRAIN12";

        System.out.println("Train ID: " + trainID1 + " -> "
                + (validateTrainID(trainID1) ? "Valid" : "Invalid"));
        System.out.println("Train ID: " + trainID2 + " -> "
                + (validateTrainID(trainID2) ? "Valid" : "Invalid"));

        // Cargo Code validation
        String cargoCode1 = "PET-AB";
        String cargoCode2 = "PET-ab";

        System.out.println("\nCargo Code: " + cargoCode1 + " -> "
                + (validateCargoCode(cargoCode1) ? "Valid" : "Invalid"));
        System.out.println("Cargo Code: " + cargoCode2 + " -> "
                + (validateCargoCode(cargoCode2) ? "Valid" : "Invalid"));

        System.out.println("\nUC11 regex validation completed...");
    }
}