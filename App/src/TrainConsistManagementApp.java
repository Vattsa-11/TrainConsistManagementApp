/**
 * ========================================================
 * MAIN CLASS - UseCase4TrainConsistMgmnt
 * ========================================================
 *
 * Use Case 4: Maintain Ordered Bogie Consist
 *
 * Description:
 * This class models the physical chaining of train bogies
 * using LinkedList for ordered operations.
 *
 * At this stage, the application:
 * - Adds bogies in sequence
 * - Inserts bogies at specific positions
 * - Removes bogies from front and rear
 * - Displays updated train structure
 *
 * This maps positional operations using LinkedList.
 *
 * @author Developer
 * @version 4.0
 */

import java.util.LinkedList;
import java.util.List;

public class TrainConsistManagementApp {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println(" UC4 - Maintain Ordered Bogie Consist ");
        System.out.println("======================================\n");

        // Create a LinkedList
        // LinkedList maintains insertion order and allows fast inserts
        LinkedList<String> trainConsist = new LinkedList<>();

        // ---- Add bogies in sequence ----
        // addLast() appends each bogie to the end of the train
        trainConsist.addLast("Engine");
        trainConsist.addLast("Sleeper");
        trainConsist.addLast("AC");
        trainConsist.addLast("Cargo");
        trainConsist.addLast("Guard");

        System.out.println("Initial Train Consist:");
        System.out.println(trainConsist);

        // ---- Insert Pantry Car at position 2 ----
        // add(index, element) places bogie between Sleeper and AC
        trainConsist.add(2, "Pantry Car");

        System.out.println("\nAfter Inserting 'Pantry Car' at position 2:");
        System.out.println(trainConsist);

        // ---- Remove first and last bogies ----
        // removeFirst() detaches the locomotive (Engine)
        // removeLast() detaches the rear guard coach
        trainConsist.removeFirst();
        trainConsist.removeLast();

        System.out.println("\nAfter Removing First and Last Bogie:");
        System.out.println(trainConsist);

        System.out.println("\nUC4 ordered consist operations completed...");
    }
}