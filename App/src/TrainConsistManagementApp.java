/**
 * ========================================================
 * MAIN CLASS - UseCase12TrainConsistMgmnt
 * ========================================================
 *
 * Use Case 12: Safety Compliance Check for Goods Bogies
 *
 * Description:
 * This class validates goods bogie safety compliance using
 * Stream allMatch() and lambda expressions.
 *
 * At this stage, the application:
 * - Creates a collection of goods bogies with shape and cargo
 * - Converts collection to a stream using stream()
 * - Uses allMatch() to validate every bogie
 * - Applies rule: Cylindrical bogies may only carry Petroleum
 * - Stores result in a boolean variable
 * - Displays whether the train is safety compliant
 *
 * This maps business rule enforcement using Stream allMatch().
 *
 * @author Developer
 * @version 12.0
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TrainConsistManagementApp {

    // --------------------------------------------------------
    // Bogie model — used by UC9, UC10, UC11
    // --------------------------------------------------------
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

    // --------------------------------------------------------
    // GoodsBogie model — used by UC12
    // --------------------------------------------------------
    static class GoodsBogie {
        String shape;   // e.g. "Cylindrical", "Open", "Box"
        String cargo;   // e.g. "Petroleum", "Coal", "Grain"

        GoodsBogie(String shape, String cargo) {
            this.shape = shape;
            this.cargo = cargo;
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

    // UC11: Validate Train ID — format: TRN-\d{4}
    public static boolean validateTrainID(String trainID) {
        Pattern pattern = Pattern.compile("TRN-\\d{4}");
        Matcher matcher = pattern.matcher(trainID);
        return matcher.matches();
    }

    // UC11: Validate Cargo Code — format: PET-[A-Z]{2}
    public static boolean validateCargoCode(String cargoCode) {
        Pattern pattern = Pattern.compile("PET-[A-Z]{2}");
        Matcher matcher = pattern.matcher(cargoCode);
        return matcher.matches();
    }

    // UC12: Safety compliance check using allMatch()
    // Rule: Cylindrical bogies may only carry Petroleum
    public static boolean isSafetyCompliant(List<GoodsBogie> goodsBogies) {
        return goodsBogies.stream()
                .allMatch(b -> !b.shape.equals("Cylindrical")
                        || b.cargo.equals("Petroleum"));
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

        // ---- UC11: Validate Train ID & Cargo Codes ----
        System.out.println("\n======================================");
        System.out.println(" UC11 - Validate Train ID & Cargo Codes (Regex) ");
        System.out.println("======================================\n");

        System.out.println("Train ID: TRN-1234 -> "
                + (validateTrainID("TRN-1234") ? "Valid" : "Invalid"));
        System.out.println("Train ID: TRAIN12  -> "
                + (validateTrainID("TRAIN12")  ? "Valid" : "Invalid"));
        System.out.println("Cargo Code: PET-AB -> "
                + (validateCargoCode("PET-AB") ? "Valid" : "Invalid"));
        System.out.println("Cargo Code: PET-ab -> "
                + (validateCargoCode("PET-ab") ? "Valid" : "Invalid"));
        System.out.println("\nUC11 regex validation completed...");

        // ---- UC12: Safety Compliance Check for Goods Bogies ----
        System.out.println("\n======================================");
        System.out.println(" UC12 - Safety Compliance Check for Goods Bogies ");
        System.out.println("======================================\n");

        // Safe formation — Cylindrical carries Petroleum
        List<GoodsBogie> safeFormation = new ArrayList<>();
        safeFormation.add(new GoodsBogie("Cylindrical", "Petroleum"));
        safeFormation.add(new GoodsBogie("Open",        "Coal"));
        safeFormation.add(new GoodsBogie("Box",         "Grain"));

        boolean safeResult = isSafetyCompliant(safeFormation);
        System.out.println("Safe Formation Compliance: " + safeResult);

        // Unsafe formation — Cylindrical carries Coal
        List<GoodsBogie> unsafeFormation = new ArrayList<>();
        unsafeFormation.add(new GoodsBogie("Cylindrical", "Coal"));
        unsafeFormation.add(new GoodsBogie("Open",        "Grain"));

        boolean unsafeResult = isSafetyCompliant(unsafeFormation);
        System.out.println("Unsafe Formation Compliance: " + unsafeResult);

        System.out.println("\nUC12 safety compliance check completed...");
    }
}