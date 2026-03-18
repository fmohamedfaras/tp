package seedu.inventorybro.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;
import seedu.inventorybro.Ui;

/**
 * Tests for {@link TransactCommand}.
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

    /**
     * Verifies that transactions which would make the quantity negative are rejected.
     */
    @Test
    void execute_quantityBelowZero_throwsException() {
        ItemList items = new ItemList();
        items.addItem(new Item("Coke Can", 50));

        assertThrows(
                IllegalArgumentException.class,
                () -> new TransactCommand("transact 1 q/-999").execute(items, ui)
        );

        assertEquals(50, items.getItem(0).getQuantity());
    }

    /**
     * Verifies that an out-of-bounds index is rejected.
     */
    @Test
    void execute_invalidIndex_throwsException() {
        ItemList items = new ItemList();
        items.addItem(new Item("Coke Can", 50));

        assertThrows(
                IllegalArgumentException.class,
                () -> new TransactCommand("transact 99 q/10").execute(items, ui)
        );

        assertEquals(50, items.getItem(0).getQuantity());
    }

    /**
     * Verifies that a missing q/ separator is rejected.
     */
    @Test
    void execute_missingQuantityPrefix_throwsException() {
        ItemList items = new ItemList();
        items.addItem(new Item("Coke Can", 50));

        assertThrows(
                IllegalArgumentException.class,
                () -> new TransactCommand("transact 1 10").execute(items, ui)
        );

        assertEquals(50, items.getItem(0).getQuantity());
    }

    /**
     * Verifies that a non-digit index is rejected.
     */
    @Test
    void execute_nonDigitIndex_throwsException() {
        ItemList items = new ItemList();
        items.addItem(new Item("Coke Can", 50));

        assertThrows(
                IllegalArgumentException.class,
                () -> new TransactCommand("transact abc q/10").execute(items, ui)
        );

        assertEquals(50, items.getItem(0).getQuantity());
    }

    /**
     * Verifies that a lone minus sign as quantity is rejected.
     */
    @Test
    void execute_justMinusSign_throwsException() {
        ItemList items = new ItemList();
        items.addItem(new Item("Coke Can", 50));

        assertThrows(
                IllegalArgumentException.class,
                () -> new TransactCommand("transact 1 q/-").execute(items, ui)
        );

        assertEquals(50, items.getItem(0).getQuantity());
    }

    /**
     * Verifies that a non-digit quantity is rejected.
     */
    @Test
    void execute_nonDigitQuantity_throwsException() {
        ItemList items = new ItemList();
        items.addItem(new Item("Coke Can", 50));

        assertThrows(
                IllegalArgumentException.class,
                () -> new TransactCommand("transact 1 q/abc").execute(items, ui)
        );

        assertEquals(50, items.getItem(0).getQuantity());
    }
}

