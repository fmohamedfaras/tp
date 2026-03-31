package seedu.inventorybro.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;
import seedu.inventorybro.Ui;

//@@author kenpegrasio
/**
 * Execution tests for {@link AddCommand}.
 */
class AddCommandTest {

    private final Ui ui = new Ui();

    /**
     * Verifies that a valid add command creates a new item with the expected values.
     */
    @Test
    void execute_validCommand_itemAdded() {
        ItemList items = new ItemList();

        new AddCommand("addItem d/Apple q/10").execute(items, ui);

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

        new AddCommand("addItem d/Green Apple q/25").execute(items, ui);

        Item item = items.getItems().get(0);

        assertEquals("Green Apple", item.getDescription());
        assertEquals(25, item.getQuantity());
    }
}
