package seedu.inventorybro.command;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;

/**
 * Tests for {@link EditCommand}.
 */
class EditCommandTest {

    /**
     * Verifies that a valid edit command updates the targeted item.
     */
    @Test
    void execute_validInput_updatesItemCorrectly() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 10));

        new EditCommand("edit 1 d/Orange q/20").execute(items);

        assertEquals("Orange", items.getItem(0).getDescription());
        assertEquals(20, items.getItem(0).getQuantity());
    }

    /**
     * Verifies that editing works correctly for a later item in the list.
     */
    @Test
    void execute_secondItem_updatesCorrectly() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 10));
        items.addItem(new Item("Banana", 5));

        new EditCommand("edit 2 d/Mango q/7").execute(items);

        assertEquals("Mango", items.getItem(1).getDescription());
        assertEquals(7, items.getItem(1).getQuantity());
    }

    /**
     * Verifies that an out-of-bounds index is handled without throwing.
     */
    @Test
    void execute_indexOutOfBounds_doesNotCrash() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 10));

        assertDoesNotThrow(() -> new EditCommand("edit 99 d/Ghost q/0").execute(items));
    }

    /**
     * Verifies that zero as an index is handled without throwing.
     */
    @Test
    void execute_zeroIndex_doesNotCrash() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 10));

        assertDoesNotThrow(() -> new EditCommand("edit 0 d/Apple q/5").execute(items));
    }

    /**
     * Verifies that a non-numeric index is handled without throwing.
     */
    @Test
    void execute_nonNumericIndex_doesNotCrash() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 10));

        assertDoesNotThrow(() -> new EditCommand("edit abc d/Apple q/5").execute(items));
    }

    /**
     * Verifies that a non-numeric quantity is handled without throwing.
     */
    @Test
    void execute_nonNumericQuantity_doesNotCrash() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 10));

        assertDoesNotThrow(() -> new EditCommand("edit 1 d/Apple q/abc").execute(items));
    }

    /**
     * Verifies that editing one item does not modify the others.
     */
    @Test
    void execute_doesNotAffectOtherItems() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 10));
        items.addItem(new Item("Banana", 5));

        new EditCommand("edit 1 d/Orange q/99").execute(items);

        assertEquals("Banana", items.getItem(1).getDescription());
        assertEquals(5, items.getItem(1).getQuantity());
    }
}
