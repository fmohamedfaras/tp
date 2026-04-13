package seedu.inventorybro.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.inventorybro.CategoryList;
import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;
import seedu.inventorybro.Ui;

//@@author elliotjohnwu
/**
 * Execution tests for {@link TransactCommand}.
 */
class TransactCommandTest {
    private final Ui ui = new Ui();
    private final CategoryList categories = new CategoryList();

    @Test
    void execute_validSale_quantityDecreased() {
        ItemList items = new ItemList();
        items.addItem(new Item("Coke Can", 50, 0.0, categories.getCategory("Others")));

        new TransactCommand("transact 1 q/-5").execute(items, categories, ui);

        assertEquals(45, items.getItem(0).getQuantity());
    }

    @Test
    void execute_validRestock_quantityIncreased() {
        ItemList items = new ItemList();
        items.addItem(new Item("Sprite Bottle", 30, 0.0, categories.getCategory("Others")));

        new TransactCommand("transact 1 q/10").execute(items, categories, ui);

        assertEquals(40, items.getItem(0).getQuantity());
    }

    @Test
    void execute_exactDepletion_quantityBecomesZero() {
        ItemList items = new ItemList();
        items.addItem(new Item("Coke Can", 50, 0.0, categories.getCategory("Others")));

        new TransactCommand("transact 1 q/-50").execute(items, categories, ui);

        assertEquals(0, items.getItem(0).getQuantity());
    }

    @Test
    void execute_zeroChange_quantityUnchanged() {
        ItemList items = new ItemList();
        items.addItem(new Item("Coke Can", 50, 0.0, categories.getCategory("Others")));

        new TransactCommand("transact 1 q/0").execute(items, categories, ui);

        assertEquals(50, items.getItem(0).getQuantity());
    }
}
