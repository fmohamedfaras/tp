package seedu.inventorybro.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;

/**
 * Tests for {@link DeleteCommand}.
 */
class DeleteCommandTest {

    /**
     * Verifies that deleting a valid index removes the correct item.
     */
    @Test
    void execute_validIndex_itemRemoved() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 10));
        items.addItem(new Item("Banana", 5));

        new DeleteCommand("deleteItem 1").execute(items);

        assertEquals(1, items.size());
        assertEquals("Banana", items.getItem(0).getDescription());
    }

    /**
     * Verifies that out-of-range indices are rejected without mutating the list.
     */
    @Test
    void execute_invalidIndex_throwsException() {
        ItemList items = new ItemList();
        items.addItem(new Item("Coke Can", 50));

        assertThrows(
                IllegalArgumentException.class,
                () -> new DeleteCommand("deleteItem 99").execute(items)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> new DeleteCommand("deleteItem 0").execute(items)
        );

        assertEquals(1, items.size());
        assertEquals("Coke Can", items.getItem(0).getDescription());
    }

    /**
     * Verifies that malformed delete input is rejected.
     */
    @Test
    void execute_invalidInputFormats_throwsException() {
        ItemList items = new ItemList();
        items.addItem(new Item("Coke Can", 50));

        assertThrows(
                IllegalArgumentException.class,
                () -> new DeleteCommand("deleteItem").execute(items)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> new DeleteCommand("deleteItem abc").execute(items)
        );

        assertEquals(1, items.size());
    }
}
