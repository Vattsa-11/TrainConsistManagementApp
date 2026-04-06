/**
 * ========================================================
 * MAIN CLASS - UseCase8TrainConsistMgmnt
 * ========================================================
 *
 * Use Case 8: Filter Passenger Bogies Using Streams
 *
 * Description:
 * This class filters passenger bogies based on seating
 * capacity using Java Stream API.
 *
 * At this stage, the application:
 * - Creates a list of bogies
 * - Converts list into stream
 * - Applies filter condition
 * - Collects filtered result
 * - Displays qualifying bogies
 *
 * This maps functional filtering using Streams.
 *
 * @author Developer
 * @version 8.0
 */

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TrainConsistManagementApp {

    // Reusing Bogie model from UC7
    static class Bogie {
        String name;
        int capacity;

        Bogie(String name, int capacity) {
            this.name = name;
            this.capacity = capacity;
        }
    }

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println(" UC8 - Filter Passenger Bogies Using Streams ");
        System.out.println("======================================\n");

        // Create list of passenger bogies (same style as UC7)
        List<Bogie> bogies = new ArrayList<>();

        // ---- Add bogies with name and capacity ----
        bogies.add(new Bogie("Sleeper", 72));
        bogies.add(new Bogie("AC Chair", 56));
        bogies.add(new Bogie("First Class", 24));
        bogies.add(new Bogie("General", 90));

        // ---- Display all bogies before filtering ----
        System.out.println("All Bogies:");
        for (Bogie b : bogies) {
            System.out.println(b.name + " -> " + b.capacity);
        }

        // ---- Apply Stream filter ----
        // stream() converts the list into a stream pipeline
        // filter() selects only bogies with capacity > 60
        // collect() gathers the results into a new list
        List<Bogie> filtered = bogies.stream()
                .filter(b -> b.capacity > 60)
                .collect(Collectors.toList());

        // ---- Display filtered bogies ----
        // Original list remains unchanged; filtered is a new list
        System.out.println("\nFiltered Bogies (Capacity > 60):");
        for (Bogie b : filtered) {
            System.out.println(b.name + " -> " + b.capacity);
        }

        System.out.println("\nUC8 filtering completed...");
    }
}