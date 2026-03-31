package seedu.inventorybro.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;
import seedu.inventorybro.Ui;

/**
 * Tests for {@link DeleteCommand}.
 */
class DeleteCommandTest {

    private final Ui ui = new Ui();
    /**
     * Verifies that deleting a valid index removes the correct item.
     */
    //@@author fmohamedfaras
    @Test
    void execute_validIndex_itemRemoved() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 10));
        items.addItem(new Item("Banana", 5));

        new DeleteCommand("deleteItem 1").execute(items, ui);

        assertEquals(1, items.size());
        assertEquals("Banana", items.getItem(0).getDescription());
    }

    /**
     * Verifies that out-of-range indices are rejected without mutating the list.
     */
    //@@author fmohamedfaras
    @Test
    void execute_invalidIndex_throwsException() {
        ItemList items = new ItemList();
        items.addItem(new Item("Coke Can", 50));

        assertThrows(
                IllegalArgumentException.class,
                () -> new DeleteCommand("deleteItem 99").execute(items, ui)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> new DeleteCommand("deleteItem 0").execute(items, ui)
        );

        assertEquals(1, items.size());
        assertEquals("Coke Can", items.getItem(0).getDescription());
    }

    /**
     * Verifies that malformed delete input is rejected.
     */
    //@@author fmohamedfaras
    @Test
    void execute_invalidInputFormats_throwsException() {
        ItemList items = new ItemList();
        items.addItem(new Item("Coke Can", 50));

        assertThrows(
                IllegalArgumentException.class,
                () -> new DeleteCommand("deleteItem").execute(items, ui)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> new DeleteCommand("deleteItem abc").execute(items, ui)
        );

        assertEquals(1, items.size());
    }
}
