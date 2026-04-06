/**
 * ========================================================
 * TEST CLASS - TrainConsistManagementAppTest
 * ========================================================
 *
 * Use Case 11: Validate Train ID & Cargo Codes (Regex)
 *
 * Description:
 * Tests regex-based validation of Train ID and Cargo Code
 * formats using Pattern and Matcher. Verifies valid formats,
 * invalid formats, digit length, uppercase enforcement,
 * empty input, and exact pattern matching.
 *
 * @author Developer
 * @version 11.0
 */

import org.junit.Test;
import static org.junit.Assert.*;

public class TrainConsistManagementAppTest {

    // --------------------------------------------------------
    // TC1: Valid Train ID — TRN-1234 must be accepted
    // --------------------------------------------------------
    @Test
    public void testRegex_ValidTrainID() {
        assertTrue(TrainConsistManagementApp.validateTrainID("TRN-1234"));
    }

    // --------------------------------------------------------
    // TC2: Invalid Train ID formats must be rejected
    // TRAIN12, TRN12A, 1234-TRN do not match TRN-\d{4}
    // --------------------------------------------------------
    @Test
    public void testRegex_InvalidTrainIDFormat() {
        assertFalse(TrainConsistManagementApp.validateTrainID("TRAIN12"));
        assertFalse(TrainConsistManagementApp.validateTrainID("TRN12A"));
        assertFalse(TrainConsistManagementApp.validateTrainID("1234-TRN"));
    }

    // --------------------------------------------------------
    // TC3: Valid Cargo Code — PET-AB must be accepted
    // --------------------------------------------------------
    @Test
    public void testRegex_ValidCargoCode() {
        assertTrue(TrainConsistManagementApp.validateCargoCode("PET-AB"));
    }

    // --------------------------------------------------------
    // TC4: Invalid Cargo Code formats must be rejected
    // PET-ab (lowercase), PET123 (no dash), AB-PET (wrong prefix)
    // --------------------------------------------------------
    @Test
    public void testRegex_InvalidCargoCodeFormat() {
        assertFalse(TrainConsistManagementApp.validateCargoCode("PET-ab"));
        assertFalse(TrainConsistManagementApp.validateCargoCode("PET123"));
        assertFalse(TrainConsistManagementApp.validateCargoCode("AB-PET"));
    }

    // --------------------------------------------------------
    // TC5: Train ID must contain exactly 4 digits after TRN-
    // TRN-123 (3 digits) and TRN-12345 (5 digits) must fail
    // --------------------------------------------------------
    @Test
    public void testRegex_TrainIDDigitLengthValidation() {
        assertFalse(TrainConsistManagementApp.validateTrainID("TRN-123"));
        assertFalse(TrainConsistManagementApp.validateTrainID("TRN-12345"));
        assertTrue(TrainConsistManagementApp.validateTrainID("TRN-0000"));
    }

    // --------------------------------------------------------
    // TC6: Cargo Code must contain exactly 2 uppercase letters
    // Lowercase letters must be rejected
    // --------------------------------------------------------
    @Test
    public void testRegex_CargoCodeUppercaseValidation() {
        assertFalse(TrainConsistManagementApp.validateCargoCode("PET-ab"));
        assertFalse(TrainConsistManagementApp.validateCargoCode("PET-Ab"));
        assertFalse(TrainConsistManagementApp.validateCargoCode("PET-aB"));
        assertTrue(TrainConsistManagementApp.validateCargoCode("PET-ZZ"));
    }

    // --------------------------------------------------------
    // TC7: Empty strings must return invalid for both formats
    // --------------------------------------------------------
    @Test
    public void testRegex_EmptyInputHandling() {
        assertFalse(TrainConsistManagementApp.validateTrainID(""));
        assertFalse(TrainConsistManagementApp.validateCargoCode(""));
    }

    // --------------------------------------------------------
    // TC8: Extra characters beyond pattern must be rejected
    // matches() checks the entire string — not partial
    // --------------------------------------------------------
    @Test
    public void testRegex_ExactPatternMatch() {
        assertFalse(TrainConsistManagementApp.validateTrainID("TRN-1234X"));
        assertFalse(TrainConsistManagementApp.validateTrainID("XTRN-1234"));
        assertFalse(TrainConsistManagementApp.validateCargoCode("PET-ABC"));
        assertFalse(TrainConsistManagementApp.validateCargoCode("XPET-AB"));
    }
}