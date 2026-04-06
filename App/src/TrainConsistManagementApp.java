import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ========================================================
 * MAIN CLASS - UseCase11TrainConsistMgmnt
 * ========================================================
 *
 * Use Case 11: Validate Train ID and Cargo Code
 *
 * Description:
 * This class validates input formats using Regular Expressions.
 *
 * At this stage, the application:
 * - Accepts Train ID input
 * - Accepts Cargo Code input
 * - Applies regex validation
 * - Displays validation result
 *
 * This maps format validation logic using Pattern matching.
 *
 * @author Developer
 * @version 11.0
 */

public class TrainConsistManagementApp {

    // ---- DEFINE REGEX RULES ----
    // Train ID: TRN- followed by exactly 4 digits
    static final Pattern TRAIN_ID_PATTERN  = Pattern.compile("TRN-\\d{4}");

    // Cargo Code: PET- followed by exactly 2 uppercase letters
    static final Pattern CARGO_CODE_PATTERN = Pattern.compile("PET-[A-Z]{2}");

    // Reusable validation method — used by main() and tests
    public static boolean validateTrainId(String input) {
        Matcher matcher = TRAIN_ID_PATTERN.matcher(input);
        return matcher.matches();
    }

    public static boolean validateCargoCode(String input) {
        Matcher matcher = CARGO_CODE_PATTERN.matcher(input);
        return matcher.matches();
    }

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println(" UC11 - Validate Train ID and Cargo Code ");
        System.out.println("======================================\n");

        // ---- Accept input ----
        String trainId   = "TRN-6524";
        String cargoCode = "PET-FH";

        System.out.println("Enter Train ID (Format: TRN-1234): " + trainId);
        System.out.println("Enter Cargo Code (Format: PET-AB): " + cargoCode);

        // ---- Validate inputs ----
        boolean trainIdValid   = validateTrainId(trainId);
        boolean cargoCodeValid = validateCargoCode(cargoCode);

        // ---- Display results ----
        System.out.println("\nValidation Results:");
        System.out.println("Train ID Valid: "   + trainIdValid);
        System.out.println("Cargo Code Valid: " + cargoCodeValid);

        System.out.println("\nUC11 validation completed...");
    }
}