package seedu.inventorybro.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;
import seedu.inventorybro.Ui;

//@@author elliotjohnwu
/**
 * Execution tests for {@link TransactCommand}.
 */
class TransactCommandTest {
    private final Ui ui = new Ui();

    /**
     * Verifies that a valid negative quantity transaction decreases stock.
     */
    @Test
    void execute_validSale_quantityDecreased() {
        ItemList items = new ItemList();
        items.addItem(new Item("Coke Can", 50));

        new TransactCommand("transact 1 q/-5").execute(items, ui);

        assertEquals(45, items.getItem(0).getQuantity());
    }

    /**
     * Verifies that a valid positive quantity transaction increases stock.
     */
    @Test
    void execute_validRestock_quantityIncreased() {
        ItemList items = new ItemList();
        items.addItem(new Item("Sprite Bottle", 30));

        new TransactCommand("transact 1 q/10").execute(items, ui);

        assertEquals(40, items.getItem(0).getQuantity());
    }

    /**
     * Verifies that a transaction may reduce the quantity exactly to zero.
     */
    @Test
    void execute_exactDepletion_quantityBecomesZero() {
        ItemList items = new ItemList();
        items.addItem(new Item("Coke Can", 50));

        new TransactCommand("transact 1 q/-50").execute(items, ui);

        assertEquals(0, items.getItem(0).getQuantity());
    }

    /**
     * Verifies that a zero-quantity transaction leaves the quantity unchanged.
     */
    @Test
    void execute_zeroChange_quantityUnchanged() {
        ItemList items = new ItemList();
        items.addItem(new Item("Coke Can", 50));

        new TransactCommand("transact 1 q/0").execute(items, ui);

        assertEquals(50, items.getItem(0).getQuantity());
    }
}

