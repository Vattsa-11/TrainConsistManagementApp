/**
 * ========================================================
 * MAIN CLASS - UseCase12TrainConsistMgmnt
 * ========================================================
 *
 * Use Case 12: Safety Compliance Check for Goods Bogies
 *
 * Description:
 * This class enforces domain safety rules on goods bogies.
 *
 * At this stage, the application:
 * - Creates goods bogie list
 * - Converts list into stream
 * - Applies safety validation rule
 * - Checks compliance using allMatch()
 * - Displays safety status
 *
 * This maps real-world cargo safety rules using Streams.
 *
 * @author Developer
 * @version 12.0
 */

import java.util.ArrayList;
import java.util.List;

public class TrainConsistManagementApp {

    // Goods Bogie model
    static class GoodsBogie {
        String type;
        String cargo;

        GoodsBogie(String type, String cargo) {
            this.type  = type;
            this.cargo = cargo;
        }
    }

    // Reusable safety check method — used by main() and tests
    // Rule: Cylindrical bogies must carry only Petroleum
    public static boolean isSafeFormation(List<GoodsBogie> goodsBogies) {
        return goodsBogies.stream()
                .allMatch(b -> !b.type.equals("Cylindrical") || b.cargo.equals("Petroleum"));
    }

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println(" UC12 - Safety Compliance Check for Goods Bogies ");
        System.out.println("======================================\n");

        // Create goods bogie list
        List<GoodsBogie> goodsBogies = new ArrayList<>();

        // ---- Add goods bogies with type and cargo ----
        goodsBogies.add(new GoodsBogie("Cylindrical", "Petroleum")); // valid
        goodsBogies.add(new GoodsBogie("Open",        "Coal"));      // valid
        goodsBogies.add(new GoodsBogie("Box",         "Grain"));     // valid
        goodsBogies.add(new GoodsBogie("Cylindrical", "Coal"));      // VIOLATION

        // ---- Display goods bogies ----
        System.out.println("Goods Bogies in Train:");
        for (GoodsBogie b : goodsBogies) {
            System.out.println(b.type + " -> " + b.cargo);
        }

        // ---- Apply allMatch() safety rule ----
        // allMatch() stops at first failure (short-circuit)
        boolean isSafe = isSafeFormation(goodsBogies);

        // ---- Display safety status ----
        System.out.println("\nSafety Compliance Status: " + isSafe);
        if (isSafe) {
            System.out.println("Train formation is SAFE.");
        } else {
            System.out.println("Train formation is NOT SAFE.");
        }

        System.out.println("\nUC12 safety validation completed...");
    }
}