/**
 * ========================================================
 * MAIN CLASS - UseCase10TrainConsistMgmnt
 * ========================================================
 *
 * Use Case 10: Count Total Seats in Train (reduce)
 *
 * Description:
 * This class aggregates seating capacities into a single
 * total value using Stream map() and reduce() operations.
 *
 * At this stage, the application:
 * - Reuses the list of Bogie objects from UC9
 * - Converts list into stream
 * - Applies map() to extract capacity values
 * - Uses reduce(0, Integer::sum) to calculate total
 * - Displays total seating capacity
 *
 * This maps functional aggregation using Stream reduction.
 *
 * @author Developer
 * @version 10.0
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    // UC10: Reusable reduce method — used by main() and tests
    public static int countTotalSeats(List<Bogie> bogies) {
        return bogies.stream()
                .map(b -> b.capacity)
                .reduce(0, Integer::sum);
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

        // ---- UC10: Count Total Seats in Train (reduce) ----
        System.out.println("\n======================================");
        System.out.println(" UC10 - Count Total Seats in Train (reduce) ");
        System.out.println("======================================\n");

        // stream() -> map(capacity) -> reduce(sum) -> total
        int totalSeats = countTotalSeats(bogies);

        System.out.println("Bogie Capacities:");
        for (Bogie b : bogies) {
            System.out.println("  " + b.name + " -> " + b.capacity);
        }

        System.out.println("\nTotal Seating Capacity: " + totalSeats);
        System.out.println("\nUC10 seat aggregation completed...");
    }
}