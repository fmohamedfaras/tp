package seedu.inventorybro.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.inventorybro.CategoryList;
import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;
import seedu.inventorybro.Ui;

/**
 * Execution tests for {@link DeleteCommand}.
 */
//@@author fmohamedfaras
class DeleteCommandTest {

    private final Ui ui = new Ui();
    private final CategoryList categories = new CategoryList();

    /**
     * Verifies that deleting a valid index removes the correct item.
     */
    @Test
    void execute_validIndex_itemRemoved() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 10, 0.0, categories.getCategory("Others")));
        items.addItem(new Item("Banana", 5, 0.0, categories.getCategory("Others")));

        new DeleteCommand("deleteItem 1").execute(items, categories, ui);

        assertEquals(1, items.size());
        assertEquals("Banana", items.getItem(0).getDescription());
    }
}
