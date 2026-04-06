/**
 * ========================================================
 * TEST CLASS - TrainConsistManagementAppTest
 * ========================================================
 *
 * Use Case 12: Safety Compliance Check for Goods Bogies
 *
 * Description:
 * Tests stream-based safety compliance validation of goods
 * bogies using allMatch(). Verifies cylindrical bogie rule,
 * valid/invalid formations, non-cylindrical flexibility,
 * mixed violations, and empty list handling.
 *
 * @author Developer
 * @version 12.0
 */

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class TrainConsistManagementAppTest {

    // --------------------------------------------------------
    // TC1: All cylindrical bogies carry Petroleum → safe
    // --------------------------------------------------------
    @Test
    public void testSafety_AllBogiesValid() {
        List<TrainConsistManagementApp.GoodsBogie> goodsBogies = new ArrayList<>();
        goodsBogies.add(new TrainConsistManagementApp.GoodsBogie("Cylindrical", "Petroleum"));
        goodsBogies.add(new TrainConsistManagementApp.GoodsBogie("Open",        "Coal"));
        goodsBogies.add(new TrainConsistManagementApp.GoodsBogie("Box",         "Grain"));

        assertTrue(TrainConsistManagementApp.isSafetyCompliant(goodsBogies));
    }

    // --------------------------------------------------------
    // TC2: Cylindrical bogie carrying Coal → unsafe
    // --------------------------------------------------------
    @Test
    public void testSafety_CylindricalWithInvalidCargo() {
        List<TrainConsistManagementApp.GoodsBogie> goodsBogies = new ArrayList<>();
        goodsBogies.add(new TrainConsistManagementApp.GoodsBogie("Cylindrical", "Coal"));
        goodsBogies.add(new TrainConsistManagementApp.GoodsBogie("Open",        "Grain"));

        assertFalse(TrainConsistManagementApp.isSafetyCompliant(goodsBogies));
    }

    // --------------------------------------------------------
    // TC3: Non-cylindrical bogies can carry any cargo → safe
    // Open and Box bogies with Coal or Grain must pass
    // --------------------------------------------------------
    @Test
    public void testSafety_NonCylindricalBogiesAllowed() {
        List<TrainConsistManagementApp.GoodsBogie> goodsBogies = new ArrayList<>();
        goodsBogies.add(new TrainConsistManagementApp.GoodsBogie("Open", "Coal"));
        goodsBogies.add(new TrainConsistManagementApp.GoodsBogie("Box",  "Grain"));
        goodsBogies.add(new TrainConsistManagementApp.GoodsBogie("Open", "Petroleum"));

        assertTrue(TrainConsistManagementApp.isSafetyCompliant(goodsBogies));
    }

    // --------------------------------------------------------
    // TC4: One cylindrical bogie violates rule → entire train unsafe
    // --------------------------------------------------------
    @Test
    public void testSafety_MixedBogiesWithViolation() {
        List<TrainConsistManagementApp.GoodsBogie> goodsBogies = new ArrayList<>();
        goodsBogies.add(new TrainConsistManagementApp.GoodsBogie("Cylindrical", "Petroleum")); // valid
        goodsBogies.add(new TrainConsistManagementApp.GoodsBogie("Cylindrical", "Coal"));      // violation
        goodsBogies.add(new TrainConsistManagementApp.GoodsBogie("Open",        "Grain"));

        assertFalse(TrainConsistManagementApp.isSafetyCompliant(goodsBogies));
    }

    // --------------------------------------------------------
    // TC5: Empty bogie list → allMatch returns true (no violations)
    // --------------------------------------------------------
    @Test
    public void testSafety_EmptyBogieList() {
        List<TrainConsistManagementApp.GoodsBogie> goodsBogies = new ArrayList<>();

        assertTrue(TrainConsistManagementApp.isSafetyCompliant(goodsBogies));
    }
}