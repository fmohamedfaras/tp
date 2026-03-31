package seedu.inventorybro.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;
import seedu.inventorybro.Ui;

/**
 * Execution tests for {@link DeleteCommand}.
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
}
