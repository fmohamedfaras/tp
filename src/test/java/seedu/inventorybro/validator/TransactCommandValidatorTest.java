package seedu.inventorybro.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;

//@@author elliotjohnwu
/**
 * Validation tests for {@link TransactCommandValidator}.
 */
class TransactCommandValidatorTest {

    /**
     * Verifies that a valid sale transaction passes validation.
     */
    @Test
    void validate_validSale_noException() {
        ItemList items = new ItemList();
        items.addItem(new Item("Coke Can", 50));

        assertDoesNotThrow(() -> new TransactCommandValidator("transact 1 q/-5").validate(items));
    }

    /**
     * Verifies that a valid restock transaction passes validation.
     */
    @Test
    void validate_validRestock_noException() {
        ItemList items = new ItemList();
        items.addItem(new Item("Sprite Bottle", 30));

        assertDoesNotThrow(() -> new TransactCommandValidator("transact 1 q/10").validate(items));
    }

    /**
     * Verifies that a transaction that would result in negative quantity is rejected.
     */
    @Test
    void validate_quantityBelowZero_throwsException() {
        ItemList items = new ItemList();
        items.addItem(new Item("Coke Can", 50));

        assertThrows(
                IllegalArgumentException.class,
                () -> new TransactCommandValidator("transact 1 q/-999").validate(items)
        );
    }

    /**
     * Verifies that an out-of-bounds index is rejected.
     */
    @Test
    void validate_invalidIndex_throwsException() {
        ItemList items = new ItemList();
        items.addItem(new Item("Coke Can", 50));

        assertThrows(
                IllegalArgumentException.class,
                () -> new TransactCommandValidator("transact 99 q/10").validate(items)
        );
    }

    /**
     * Verifies that a missing q/ separator is rejected.
     */
    @Test
    void validate_missingQuantityPrefix_throwsException() {
        ItemList items = new ItemList();
        items.addItem(new Item("Coke Can", 50));

        assertThrows(
                IllegalArgumentException.class,
                () -> new TransactCommandValidator("transact 1 10").validate(items)
        );
    }

    /**
     * Verifies that a non-digit index is rejected.
     */
    @Test
    void validate_nonDigitIndex_throwsException() {
        ItemList items = new ItemList();
        items.addItem(new Item("Coke Can", 50));

        assertThrows(
                IllegalArgumentException.class,
                () -> new TransactCommandValidator("transact abc q/10").validate(items)
        );
    }

    /**
     * Verifies that a lone minus sign as quantity is rejected.
     */
    @Test
    void validate_justMinusSign_throwsException() {
        ItemList items = new ItemList();
        items.addItem(new Item("Coke Can", 50));

        assertThrows(
                IllegalArgumentException.class,
                () -> new TransactCommandValidator("transact 1 q/-").validate(items)
        );
    }

    /**
     * Verifies that a non-digit quantity is rejected.
     */
    @Test
    void validate_nonDigitQuantity_throwsException() {
        ItemList items = new ItemList();
        items.addItem(new Item("Coke Can", 50));

        assertThrows(
                IllegalArgumentException.class,
                () -> new TransactCommandValidator("transact 1 q/abc").validate(items)
        );
    }
}
