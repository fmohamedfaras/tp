package seedu.inventorybro.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
     * Verifies that a valid add command with an integer price creates the item correctly.
     */
    @Test
    void execute_validCommandWithIntegerPrice_itemAdded() {
        ItemList items = new ItemList();

        new AddCommand("addItem d/Apple q/10 p/5").execute(items, ui);

        Item item = items.getItems().get(0);

        assertEquals("Apple", item.getDescription());
        assertEquals(10, item.getQuantity());
        assertEquals(5.0, item.getPrice());
    }

    /**
     * Verifies that a valid add command with a decimal price sets the price correctly.
     */
    @Test
    void execute_validCommandWithDecimalPrice_itemAdded() {
        ItemList items = new ItemList();

        new AddCommand("addItem d/Apple q/10 p/5.99").execute(items, ui);

        Item item = items.getItems().get(0);

        assertEquals("Apple", item.getDescription());
        assertEquals(10, item.getQuantity());
        assertEquals(5.99, item.getPrice(), 1e-9);
    }

    /**
     * Verifies that an item description may contain multiple words.
     */
    @Test
    void execute_multiWordName_itemAdded() {
        ItemList items = new ItemList();

        new AddCommand("addItem d/Green Apple q/25 p/3.50").execute(items, ui);

        Item item = items.getItems().get(0);

        assertEquals("Green Apple", item.getDescription());
        assertEquals(25, item.getQuantity());
        assertEquals(3.50, item.getPrice(), 1e-9);
    }

    /**
     * Verifies that a quantity of zero is accepted (out-of-stock item).
     */
    @Test
    void execute_zeroQuantity_itemAdded() {
        ItemList items = new ItemList();

        new AddCommand("addItem d/Apple q/0 p/1.00").execute(items, ui);

        Item item = items.getItems().get(0);

        assertEquals("Apple", item.getDescription());
        assertEquals(0, item.getQuantity());
        assertEquals(1.00, item.getPrice());
    }

    /**
     * Verifies that passing null as input to the constructor triggers an AssertionError.
     */
    @Test
    void constructor_nullInput_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> new AddCommand(null));
    }

    /**
     * Verifies that passing null as the item list triggers an AssertionError.
     */
    @Test
    void execute_nullItems_throwsAssertionError() {
        assertThrows(AssertionError.class,
                () -> new AddCommand("addItem d/Apple q/10 p/5").execute(null, ui));
    }

    /**
     * Verifies that passing null as the UI triggers an AssertionError.
     */
    @Test
    void execute_nullUi_throwsAssertionError() {
        ItemList items = new ItemList();
        assertThrows(AssertionError.class,
                () -> new AddCommand("addItem d/Apple q/10 p/5").execute(items, null));
    }
}
