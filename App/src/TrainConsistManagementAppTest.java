/**
 * ========================================================
 * TEST CLASS - TrainConsistManagementAppTest
 * ========================================================
 *
 * Use Case 10: Count Total Seats in Train (reduce)
 *
 * Description:
 * Tests stream-based aggregation of bogie seating capacities
 * using map() and reduce(). Verifies total calculation,
 * single bogie, empty list, original list integrity,
 * and correct capacity extraction.
 *
 * @author Developer
 * @version 10.0
 */

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class TrainConsistManagementAppTest {

    // --------------------------------------------------------
    // Helper: build standard bogie dataset
    // Passenger x4, Goods x2
    // Total = 72 + 72 + 56 + 24 + 120 + 100 = 444
    // --------------------------------------------------------
    private List<TrainConsistManagementApp.Bogie> buildStandardBogieList() {
        List<TrainConsistManagementApp.Bogie> bogies = new ArrayList<>();
        bogies.add(new TrainConsistManagementApp.Bogie("Sleeper-1",     "Passenger", 72));
        bogies.add(new TrainConsistManagementApp.Bogie("Sleeper-2",     "Passenger", 72));
        bogies.add(new TrainConsistManagementApp.Bogie("AC Chair-1",    "Passenger", 56));
        bogies.add(new TrainConsistManagementApp.Bogie("First Class-1", "Passenger", 24));
        bogies.add(new TrainConsistManagementApp.Bogie("Rectangular-1", "Goods",    120));
        bogies.add(new TrainConsistManagementApp.Bogie("Cylindrical-1", "Goods",    100));
        return bogies;
    }

    // --------------------------------------------------------
    // TC1: Total seat calculation matches expected sum
    // 72 + 72 + 56 + 24 + 120 + 100 = 444
    // --------------------------------------------------------
    @Test
    public void testReduce_TotalSeatCalculation() {
        List<TrainConsistManagementApp.Bogie> bogies = buildStandardBogieList();
        int total = TrainConsistManagementApp.countTotalSeats(bogies);

        assertEquals(444, total);
    }

    // --------------------------------------------------------
    // TC2: All bogies contribute to the aggregated total
    // Adding an extra bogie changes the total correctly
    // --------------------------------------------------------
    @Test
    public void testReduce_MultipleBogiesAggregation() {
        List<TrainConsistManagementApp.Bogie> bogies = new ArrayList<>();
        bogies.add(new TrainConsistManagementApp.Bogie("Sleeper-1", "Passenger", 72));
        bogies.add(new TrainConsistManagementApp.Bogie("AC Chair-1","Passenger", 56));
        bogies.add(new TrainConsistManagementApp.Bogie("General-1", "Passenger", 90));

        int total = TrainConsistManagementApp.countTotalSeats(bogies);

        assertEquals(218, total);  // 72 + 56 + 90
    }

    // --------------------------------------------------------
    // TC3: Single bogie → total equals that bogie's capacity
    // --------------------------------------------------------
    @Test
    public void testReduce_SingleBogieCapacity() {
        List<TrainConsistManagementApp.Bogie> bogies = new ArrayList<>();
        bogies.add(new TrainConsistManagementApp.Bogie("Sleeper-1", "Passenger", 72));

        int total = TrainConsistManagementApp.countTotalSeats(bogies);

        assertEquals(72, total);
    }

    // --------------------------------------------------------
    // TC4: Empty list → reduce returns identity value 0
    // --------------------------------------------------------
    @Test
    public void testReduce_EmptyBogieList() {
        List<TrainConsistManagementApp.Bogie> bogies = new ArrayList<>();
        int total = TrainConsistManagementApp.countTotalSeats(bogies);

        assertEquals(0, total);
    }

    // --------------------------------------------------------
    // TC5: map() extracts correct capacity from each Bogie
    // Manually summed capacities must equal reduce result
    // --------------------------------------------------------
    @Test
    public void testReduce_CorrectCapacityExtraction() {
        List<TrainConsistManagementApp.Bogie> bogies = buildStandardBogieList();

        int manualSum = 0;
        for (TrainConsistManagementApp.Bogie b : bogies) {
            manualSum += b.capacity;
        }

        int streamTotal = TrainConsistManagementApp.countTotalSeats(bogies);

        assertEquals(manualSum, streamTotal);
    }

    // --------------------------------------------------------
    // TC6: All bogies are included — none skipped
    // Remove one bogie → total must decrease by that capacity
    // --------------------------------------------------------
    @Test
    public void testReduce_AllBogiesIncluded() {
        List<TrainConsistManagementApp.Bogie> bogies = buildStandardBogieList();
        int fullTotal = TrainConsistManagementApp.countTotalSeats(bogies);

        // Remove last bogie (Cylindrical-1, capacity 100)
        bogies.remove(bogies.size() - 1);
        int reducedTotal = TrainConsistManagementApp.countTotalSeats(bogies);

        assertEquals(fullTotal - 100, reducedTotal);
    }

    // --------------------------------------------------------
    // TC7: Original list is unchanged after reduce operation
    // --------------------------------------------------------
    @Test
    public void testReduce_OriginalListUnchanged() {
        List<TrainConsistManagementApp.Bogie> bogies = buildStandardBogieList();
        int originalSize = bogies.size();

        TrainConsistManagementApp.countTotalSeats(bogies);  // perform aggregation

        assertEquals(originalSize, bogies.size());
        assertEquals("Sleeper-1", bogies.get(0).name);
        assertEquals("Passenger", bogies.get(0).type);
        assertEquals(72,          bogies.get(0).capacity);
    }
}