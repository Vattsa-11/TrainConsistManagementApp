/**
 * ========================================================
 * TEST CLASS - TrainConsistManagementAppTest
 * ========================================================
 *
 * Use Case 9: Group Bogies by Type (Collectors.groupingBy)
 *
 * Description:
 * Tests stream-based grouping of bogies by type using
 * Collectors.groupingBy(). Verifies correct group keys,
 * group sizes, multiple bogies per group, empty list handling,
 * and original list integrity.
 *
 * @author Developer
 * @version 9.0
 */

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TrainConsistManagementAppTest {

    // --------------------------------------------------------
    // Helper: build standard bogie dataset
    // Passenger x4, Goods x2
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
    // TC1: Bogies are grouped correctly by type
    // All Passenger bogies → under "Passenger" key
    // --------------------------------------------------------
    @Test
    public void testGrouping_BogiesGroupedByType() {
        List<TrainConsistManagementApp.Bogie> bogies = buildStandardBogieList();
        Map<String, List<TrainConsistManagementApp.Bogie>> grouped =
                TrainConsistManagementApp.groupByType(bogies);

        assertTrue(grouped.containsKey("Passenger"));
        assertTrue(grouped.get("Passenger").stream()
                .allMatch(b -> b.type.equals("Passenger")));
    }

    // --------------------------------------------------------
    // TC2: Multiple bogies of same type land in same group
    // Passenger x4 → group size must be 4
    // --------------------------------------------------------
    @Test
    public void testGrouping_MultipleBogiesInSameGroup() {
        List<TrainConsistManagementApp.Bogie> bogies = buildStandardBogieList();
        Map<String, List<TrainConsistManagementApp.Bogie>> grouped =
                TrainConsistManagementApp.groupByType(bogies);

        assertEquals(4, grouped.get("Passenger").size());
        assertEquals(2, grouped.get("Goods").size());
    }

    // --------------------------------------------------------
    // TC3: Different bogie types appear under separate keys
    // Passenger and Goods → 2 distinct keys
    // --------------------------------------------------------
    @Test
    public void testGrouping_DifferentBogieTypes() {
        List<TrainConsistManagementApp.Bogie> bogies = buildStandardBogieList();
        Map<String, List<TrainConsistManagementApp.Bogie>> grouped =
                TrainConsistManagementApp.groupByType(bogies);

        assertTrue(grouped.containsKey("Passenger"));
        assertTrue(grouped.containsKey("Goods"));
    }

    // --------------------------------------------------------
    // TC4: Grouping an empty list returns empty Map, no crash
    // --------------------------------------------------------
    @Test
    public void testGrouping_EmptyBogieList() {
        List<TrainConsistManagementApp.Bogie> bogies = new ArrayList<>();
        Map<String, List<TrainConsistManagementApp.Bogie>> grouped =
                TrainConsistManagementApp.groupByType(bogies);

        assertNotNull(grouped);
        assertTrue(grouped.isEmpty());
    }

    // --------------------------------------------------------
    // TC5: Only one bogie type → Map contains exactly one key
    // --------------------------------------------------------
    @Test
    public void testGrouping_SingleBogieCategory() {
        List<TrainConsistManagementApp.Bogie> bogies = new ArrayList<>();
        bogies.add(new TrainConsistManagementApp.Bogie("Sleeper-1", "Passenger", 72));
        bogies.add(new TrainConsistManagementApp.Bogie("Sleeper-2", "Passenger", 72));

        Map<String, List<TrainConsistManagementApp.Bogie>> grouped =
                TrainConsistManagementApp.groupByType(bogies);

        assertEquals(1, grouped.size());
        assertTrue(grouped.containsKey("Passenger"));
    }

    // --------------------------------------------------------
    // TC6: Map contains exactly the expected keys
    // Only "Passenger" and "Goods" — no extra keys
    // --------------------------------------------------------
    @Test
    public void testGrouping_MapContainsCorrectKeys() {
        List<TrainConsistManagementApp.Bogie> bogies = buildStandardBogieList();
        Map<String, List<TrainConsistManagementApp.Bogie>> grouped =
                TrainConsistManagementApp.groupByType(bogies);

        assertEquals(2, grouped.size());
        assertTrue(grouped.keySet().contains("Passenger"));
        assertTrue(grouped.keySet().contains("Goods"));
    }

    // --------------------------------------------------------
    // TC7: Group size matches expected count per type
    // Passenger x4, Goods x2
    // --------------------------------------------------------
    @Test
    public void testGrouping_GroupSizeValidation() {
        List<TrainConsistManagementApp.Bogie> bogies = buildStandardBogieList();
        Map<String, List<TrainConsistManagementApp.Bogie>> grouped =
                TrainConsistManagementApp.groupByType(bogies);

        assertEquals(4, grouped.get("Passenger").size());
        assertEquals(2, grouped.get("Goods").size());
    }

    // --------------------------------------------------------
    // TC8: Original list is unchanged after grouping operation
    // --------------------------------------------------------
    @Test
    public void testGrouping_OriginalListUnchanged() {
        List<TrainConsistManagementApp.Bogie> bogies = buildStandardBogieList();
        int originalSize = bogies.size();

        TrainConsistManagementApp.groupByType(bogies);  // perform grouping

        assertEquals(originalSize, bogies.size());
        assertEquals("Sleeper-1", bogies.get(0).name);
        assertEquals("Passenger", bogies.get(0).type);
        assertEquals(72,          bogies.get(0).capacity);
    }
}