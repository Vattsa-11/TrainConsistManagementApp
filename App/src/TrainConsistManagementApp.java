/**
 * ========================================================
 * MAIN CLASS - UseCase10TrainConsistMgmnt
 * ========================================================
 *
 * Use Case 10: Count Total Seats in Train (reduce)
 *
 * Description:
 * This class aggregates seating capacity of all bogies
 * into a single total using Stream reduce().
 *
 * At this stage, the application:
 * - Creates bogie list
 * - Maps bogies to capacity
 * - Reduces values into total
 * - Displays total seat count
 *
 * This maps aggregation logic using reduce().
 *
 * @author Developer
 * @version 10.0
 */

import java.util.ArrayList;
import java.util.List;

public class TrainConsistManagementApp {

    // Reusing Bogie model
    static class Bogie {
        String name;
        int capacity;

        Bogie(String name, int capacity) {
            this.name = name;
            this.capacity = capacity;
        }
    }

    // Reusable reduce method — used by main() and tests
    public static int totalSeats(List<Bogie> bogies) {
        return bogies.stream()
                .map(b -> b.capacity)
                .reduce(0, Integer::sum);
    }

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println(" UC10 - Count Total Seats in Train ");
        System.out.println("======================================\n");

        // Create list of bogies
        List<Bogie> bogies = new ArrayList<>();

        bogies.add(new Bogie("Sleeper",     72));
        bogies.add(new Bogie("AC Chair",    56));
        bogies.add(new Bogie("First Class", 24));
        bogies.add(new Bogie("Sleeper",     70));

        // ---- Display bogies ----
        System.out.println("Bogies in Train:");
        for (Bogie b : bogies) {
            System.out.println(b.name + " -> " + b.capacity);
        }

        // ---- AGGREGATE USING REDUCE ----
        // map() extracts capacity field from Bogie object
        // reduce(0, Integer::sum) accumulates into total
        int total = totalSeats(bogies);

        System.out.println("\nTotal Seating Capacity of Train: " + total);

        System.out.println("\nUC10 aggregation completed...");
    }
}