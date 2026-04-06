/**
 * ========================================================
 * MAIN CLASS - UseCase13TrainConsistMgmnt
 * ========================================================
 *
 * Use Case 13: Performance Comparison (Loops vs Streams)
 *
 * Description:
 * This class compares execution time of loop-based filtering
 * versus stream-based filtering using System.nanoTime().
 *
 * At this stage, the application:
 * - Creates bogie test dataset
 * - Measures loop execution time
 * - Measures stream execution time
 * - Calculates elapsed duration
 * - Displays performance results
 *
 * This maps performance benchmarking using high-resolution timing.
 *
 * @author Developer
 * @version 13.0
 */

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TrainConsistManagementApp {

    // Bogie model
    static class Bogie {
        String type;
        int capacity;

        Bogie(String type, int capacity) {
            this.type     = type;
            this.capacity = capacity;
        }
    }

    // Loop-based filter — used by main() and tests
    public static List<Bogie> filterByLoop(List<Bogie> bogies, int threshold) {
        List<Bogie> result = new ArrayList<>();
        for (Bogie b : bogies) {
            if (b.capacity > threshold) {
                result.add(b);
            }
        }
        return result;
    }

    // Stream-based filter — used by main() and tests
    public static List<Bogie> filterByStream(List<Bogie> bogies, int threshold) {
        return bogies.stream()
                .filter(b -> b.capacity > threshold)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println(" UC13 - Performance Comparison (Loops vs Streams) ");
        System.out.println("======================================\n");

        // Create large test dataset
        List<Bogie> bogies = new ArrayList<>();
        String[] types = {"Sleeper", "AC Chair", "First Class", "General", "Cargo"};
        int[]    caps  = {72, 56, 24, 90, 120};

        for (int i = 0; i < 100000; i++) {
            bogies.add(new Bogie(types[i % types.length], caps[i % caps.length]));
        }

        // ---- BENCHMARK: Loop-based filtering ----
        long loopStart = System.nanoTime();
        List<Bogie> loopResult = filterByLoop(bogies, 60);
        long loopEnd   = System.nanoTime();
        long loopTime  = loopEnd - loopStart;

        // ---- BENCHMARK: Stream-based filtering ----
        long streamStart = System.nanoTime();
        List<Bogie> streamResult = filterByStream(bogies, 60);
        long streamEnd   = System.nanoTime();
        long streamTime  = streamEnd - streamStart;

        // ---- Display results ----
        System.out.println("Loop Execution Time (ns): "   + loopTime);
        System.out.println("Stream Execution Time (ns): " + streamTime);

        System.out.println("\nUC13 performance benchmarking completed...");
    }
}