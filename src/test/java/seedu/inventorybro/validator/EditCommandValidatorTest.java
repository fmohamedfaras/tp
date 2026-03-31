package seedu.inventorybro.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;

/**
 * Validation tests for {@link EditCommandValidator}.
 */
class EditCommandValidatorTest {

    /**
     * Verifies that a well-formed edit input passes validation.
     */
    @Test
    void validate_validInput_noException() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 10));

        assertDoesNotThrow(() -> new EditCommandValidator("edit 1 d/Orange q/20 p/1.50").validate(items));
    }

    /**
     * Verifies that an out-of-bounds index is rejected.
     */
    @Test
    void validate_indexOutOfBounds_throwsException() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 10));

        assertThrows(
                IllegalArgumentException.class,
                () -> new EditCommandValidator("edit 99 d/Ghost q/0").validate(items)
        );
    }

    /**
     * Verifies that index zero is rejected.
     */
    @Test
    void validate_zeroIndex_throwsException() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 10));

        assertThrows(
                IllegalArgumentException.class,
                () -> new EditCommandValidator("edit 0 d/Apple q/5").validate(items)
        );
    }

    /**
     * Verifies that a non-numeric index is rejected.
     */
    @Test
    void validate_nonNumericIndex_throwsException() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 10));

        assertThrows(
                IllegalArgumentException.class,
                () -> new EditCommandValidator("edit abc d/Apple q/5").validate(items)
        );
    }

    /**
     * Verifies that a non-numeric quantity is rejected.
     */
    @Test
    void validate_nonNumericQuantity_throwsException() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 10));

        assertThrows(
                IllegalArgumentException.class,
                () -> new EditCommandValidator("edit 1 d/Apple q/abc").validate(items)
        );
    }
}
