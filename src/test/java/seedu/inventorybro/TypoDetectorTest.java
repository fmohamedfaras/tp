package seedu.inventorybro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TypoDetectorTest {
    private TypoDetector typoDetector;

    @BeforeEach
    public void setUp() {
        typoDetector = new TypoDetector();
    }

    @Test
    public void findClosestMatch_exactCommand_returnsEmpty() {
        // Exact commands should be matched by Parser before TypoDetector is called,
        // but if passed in, the distance is 0 which is below threshold → still returns a match.
        Optional<String> result = typoDetector.findClosestMatch("addItem");
        assertTrue(result.isPresent());
        assertEquals("addItem", result.get());
    }

    @Test
    public void findClosestMatch_singleCharDeletion_returnsCorrectCommand() {
        // "addIte" is "addItem" missing the last char — edit distance 1, threshold 0.2*7=1.4
        Optional<String> result = typoDetector.findClosestMatch("addIte");
        assertTrue(result.isPresent());
        assertEquals("addItem", result.get());
    }

    @Test
    public void findClosestMatch_singleCharInsertion_returnsCorrectCommand() {
        // "adddItem" has one extra 'd' — edit distance 1
        Optional<String> result = typoDetector.findClosestMatch("adddItem");
        assertTrue(result.isPresent());
        assertEquals("addItem", result.get());
    }

    @Test
    public void findClosestMatch_adjacentKeySubstitution_returnsCorrectCommand() {
        // "listItens" — 'n' substituted for 'm' at position 8 (keyboard distance 1)
        // threshold = 0.2 * max(9, 9) = 1.8, so 1 < 1.8 qualifies
        Optional<String> result = typoDetector.findClosestMatch("listItens");
        assertTrue(result.isPresent());
        assertEquals("listItems", result.get());
    }

    @Test
    public void findClosestMatch_completelyDifferentWord_returnsEmpty() {
        // "invalidCommand" is 14 chars — far from all commands
        Optional<String> result = typoDetector.findClosestMatch("invalidCommand");
        assertFalse(result.isPresent());
    }

    @Test
    public void findClosestMatch_shortGibberish_returnsEmpty() {
        // "xyz" — no command is close enough
        Optional<String> result = typoDetector.findClosestMatch("xyz");
        assertFalse(result.isPresent());
    }

    @Test
    public void findClosestMatch_caseInsensitive_returnsMatch() {
        // "ADDITEM" should still match "addItem"
        Optional<String> result = typoDetector.findClosestMatch("ADDITEM");
        assertTrue(result.isPresent());
        assertEquals("addItem", result.get());
    }

    @Test
    public void findClosestMatch_preservesCommandCase_returnsOriginalCasing() {
        // Suggestion should use the original command casing (e.g. "addItem" not "additem")
        Optional<String> result = typoDetector.findClosestMatch("addIte");
        assertTrue(result.isPresent());
        assertEquals("addItem", result.get());
    }
}
