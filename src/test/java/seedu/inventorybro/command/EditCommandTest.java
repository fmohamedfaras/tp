package seedu.inventorybro.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;
import seedu.inventorybro.Ui;

/**
 * Execution tests for {@link EditCommand}.
 */
//@@author vionyp
class EditCommandTest {
    private final Ui ui = new Ui();

    /**
     * Verifies that editName updates the item name correctly.
     */
    @Test
    void execute_editName_updatesNameCorrectly() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 10));

        new EditCommand("editName 1 d/Orange").execute(items, ui);

        assertEquals("Orange", items.getItem(0).getDescription());
    }

    /**
     * Verifies that editQuantity updates the item quantity correctly.
     */
    @Test
    void execute_editQuantity_updatesQuantityCorrectly() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 10));

        new EditCommand("editQuantity 1 q/20").execute(items, ui);

        assertEquals(20, items.getItem(0).getQuantity());
    }

    /**
     * Verifies that editPrice updates the item price correctly.
     */
    @Test
    void execute_editPrice_updatesPriceCorrectly() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 10));

        new EditCommand("editPrice 1 p/1.50").execute(items, ui);

        assertEquals(1.50, items.getItem(0).getPrice());
    }

    /**
     * Verifies that editing one item does not modify the others.
     */
    @Test
    void execute_doesNotAffectOtherItems() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 10));
        items.addItem(new Item("Banana", 5));

        new EditCommand("editName 1 d/Orange").execute(items, ui);

        assertEquals("Banana", items.getItem(1).getDescription());
        assertEquals(5, items.getItem(1).getQuantity());
    }

    /**
     * Verifies that editName works correctly for a later item in the list.
     */
    @Test
    void execute_secondItem_updatesCorrectly() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 10));
        items.addItem(new Item("Banana", 5));

        new EditCommand("editName 2 d/Mango").execute(items, ui);

        assertEquals("Mango", items.getItem(1).getDescription());
    }
}
//@@author
