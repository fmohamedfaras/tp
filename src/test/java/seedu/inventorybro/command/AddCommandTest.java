package seedu.inventorybro.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;

/**
 * Tests for {@link AddCommand}.
 */
class AddCommandTest {

    /**
     * Verifies that a valid add command creates a new item with the expected values.
     */
    @Test
    void execute_validCommand_itemAdded() {
        ItemList items = new ItemList();

        new AddCommand("addItem d/Apple q/10").execute(items);

        Item item = items.getItems().get(0);

        assertEquals("Apple", item.getDescription());
        assertEquals(10, item.getQuantity());
    }

    /**
     * Verifies that an item description may contain multiple words.
     */
    @Test
    void execute_multiWordName() {
        ItemList items = new ItemList();

        new AddCommand("addItem d/Green Apple q/25").execute(items);

        Item item = items.getItems().get(0);

        assertEquals("Green Apple", item.getDescription());
        assertEquals(25, item.getQuantity());
    }

    /**
     * Verifies that malformed add input is rejected.
     */
    @Test
    void execute_invalidFormat_throwsException() {
        ItemList items = new ItemList();

        assertThrows(
                IllegalArgumentException.class,
                () -> new AddCommand("addItem Apple 10").execute(items)
        );
    }
}
