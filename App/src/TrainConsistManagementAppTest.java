/**
 * ========================================================
 * TEST CLASS - TrainConsistManagementAppTest
 * ========================================================
 *
 * Use Case 8: Filter Passenger Bogies Using Streams
 *
 * Description:
 * Tests stream-based filtering of passenger bogies by seating
 * capacity. Verifies correct inclusion/exclusion, original
 * list integrity, edge cases (empty list, no match, all match).
 *
 * @author Developer
 * @version 8.0
 */

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TrainConsistManagementAppTest {

    // --------------------------------------------------------
    // Helper: build the standard UC7/UC8 bogie dataset
    // Sleeper=72, AC Chair=56, First Class=24, General=90
    // --------------------------------------------------------
    private List<TrainConsistManagementApp.Bogie> buildStandardBogieList() {
        List<TrainConsistManagementApp.Bogie> bogies = new ArrayList<>();
        bogies.add(new TrainConsistManagementApp.Bogie("Sleeper",      72));
        bogies.add(new TrainConsistManagementApp.Bogie("AC Chair",     56));
        bogies.add(new TrainConsistManagementApp.Bogie("First Class",  24));
        bogies.add(new TrainConsistManagementApp.Bogie("General",      90));
        return bogies;
    }

    // Helper: filter utility (mirrors UC8 stream pipeline)
    private List<TrainConsistManagementApp.Bogie> filterByCapacity(
            List<TrainConsistManagementApp.Bogie> bogies, int threshold) {
        return bogies.stream()
                .filter(b -> b.capacity > threshold)
                .collect(Collectors.toList());
    }

    // --------------------------------------------------------
    // TC1: Bogies with capacity > threshold appear in result
    // Threshold = 70 → Sleeper(72) and General(90) qualify
    // --------------------------------------------------------
    @Test
    public void testFilter_CapacityGreaterThanThreshold() {
        List<TrainConsistManagementApp.Bogie> bogies = buildStandardBogieList();
        List<TrainConsistManagementApp.Bogie> result = filterByCapacity(bogies, 70);

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(b -> b.name.equals("Sleeper")));
        assertTrue(result.stream().anyMatch(b -> b.name.equals("General")));
    }

    // --------------------------------------------------------
    // TC2: Bogie with capacity == threshold is excluded
    // Threshold = 72 → Sleeper(72) must NOT appear
    // --------------------------------------------------------
    @Test
    public void testFilter_CapacityEqualToThreshold() {
        List<TrainConsistManagementApp.Bogie> bogies = buildStandardBogieList();
        List<TrainConsistManagementApp.Bogie> result = filterByCapacity(bogies, 72);

        assertFalse(result.stream().anyMatch(b -> b.name.equals("Sleeper")));
        // Only General(90) should be in the result
        assertEquals(1, result.size());
        assertEquals("General", result.get(0).name);
    }

    // --------------------------------------------------------
    // TC3: Bogies with capacity < threshold are excluded
    // Threshold = 70 → AC Chair(56) and First Class(24) excluded
    // --------------------------------------------------------
    @Test
    public void testFilter_CapacityLessThanThreshold() {
        List<TrainConsistManagementApp.Bogie> bogies = buildStandardBogieList();
        List<TrainConsistManagementApp.Bogie> result = filterByCapacity(bogies, 70);

        assertFalse(result.stream().anyMatch(b -> b.name.equals("AC Chair")));
        assertFalse(result.stream().anyMatch(b -> b.name.equals("First Class")));
    }

    // --------------------------------------------------------
    // TC4: All bogies matching threshold are returned
    // Threshold = 20 → all four bogies (24, 56, 72, 90) qualify
    // --------------------------------------------------------
    @Test
    public void testFilter_MultipleBogiesMatching() {
        List<TrainConsistManagementApp.Bogie> bogies = buildStandardBogieList();
        List<TrainConsistManagementApp.Bogie> result = filterByCapacity(bogies, 20);

        assertEquals(4, result.size());
    }

    // --------------------------------------------------------
    // TC5: No bogies satisfy condition → empty result
    // Threshold = 100 → no bogie exceeds 100
    // --------------------------------------------------------
    @Test
    public void testFilter_NoBogiesMatching() {
        List<TrainConsistManagementApp.Bogie> bogies = buildStandardBogieList();
        List<TrainConsistManagementApp.Bogie> result = filterByCapacity(bogies, 100);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // --------------------------------------------------------
    // TC6: All bogies satisfy condition
    // Threshold = 0 → all capacities > 0
    // --------------------------------------------------------
    @Test
    public void testFilter_AllBogiesMatching() {
        List<TrainConsistManagementApp.Bogie> bogies = buildStandardBogieList();
        List<TrainConsistManagementApp.Bogie> result = filterByCapacity(bogies, 0);

        assertEquals(bogies.size(), result.size());
    }

    // --------------------------------------------------------
    // TC7: Filtering an empty list returns empty list, no crash
    // --------------------------------------------------------
    @Test
    public void testFilter_EmptyBogieList() {
        List<TrainConsistManagementApp.Bogie> bogies = new ArrayList<>();
        List<TrainConsistManagementApp.Bogie> result = filterByCapacity(bogies, 60);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // --------------------------------------------------------
    // TC8: Original list is unchanged after stream filtering
    // --------------------------------------------------------
    @Test
    public void testFilter_OriginalListUnchanged() {
        List<TrainConsistManagementApp.Bogie> bogies = buildStandardBogieList();
        int originalSize = bogies.size();

        filterByCapacity(bogies, 60);  // perform filtering

        // Size must still be 4
        assertEquals(originalSize, bogies.size());

        // Names and capacities must be exactly as inserted
        assertEquals("Sleeper",     bogies.get(0).name);
        assertEquals(72,            bogies.get(0).capacity);
        assertEquals("AC Chair",    bogies.get(1).name);
        assertEquals(56,            bogies.get(1).capacity);
        assertEquals("First Class", bogies.get(2).name);
        assertEquals(24,            bogies.get(2).capacity);
        assertEquals("General",     bogies.get(3).name);
        assertEquals(90,            bogies.get(3).capacity);
    }
}