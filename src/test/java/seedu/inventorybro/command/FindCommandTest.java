package seedu.inventorybro.command;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;
import seedu.inventorybro.Ui;

/**
 * Tests for {@link FindCommand}.
 */
class FindCommandTest {

    private final Ui ui = new Ui();

    /**
     * Verifies that searching with a valid keyword executes successfully without exceptions.
     */
    @Test
    void execute_validKeyword_doesNotCrash() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 10));
        items.addItem(new Item("Pineapple", 5));
        items.addItem(new Item("Banana", 3));

        // Searching for "app" should execute cleanly (matches Apple and Pineapple)
        assertDoesNotThrow(() -> new FindCommand("findItem app").execute(items, ui));

        // Searching with mixed case should also execute cleanly
        assertDoesNotThrow(() -> new FindCommand("findItem bAnAnA").execute(items, ui));
    }

    /**
     * Verifies that searching for a keyword not in the inventory executes cleanly.
     */
    @Test
    void execute_keywordNotFound_doesNotCrash() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 10));

        // Searching for a non-existent item should just print a "not found" message, not crash
        assertDoesNotThrow(() -> new FindCommand("findItem ghost").execute(items, ui));
    }

    /**
     * Verifies that missing or blank keywords are correctly rejected.
     */
    @Test
    void execute_missingKeyword_throwsException() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 10));

        // Missing keyword entirely
        assertThrows(
                IllegalArgumentException.class,
                () -> new FindCommand("findItem").execute(items, ui)
        );

        // Blank keyword (only spaces)
        assertThrows(
                IllegalArgumentException.class,
                () -> new FindCommand("findItem   ").execute(items, ui)
        );
    }
}
