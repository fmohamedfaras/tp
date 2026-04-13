package seedu.inventorybro.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.inventorybro.CategoryList;
import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;

/**
 * Validation tests for {@link DeleteCommandValidator}.
 */
//@@author fmohamedfaras
class DeleteCommandValidatorTest {

    private final CategoryList categories = new CategoryList();

    /**
     * Verifies that a valid index within bounds passes validation.
     */
    @Test
    void validate_validIndex_noException() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 10, 0.0, categories.getCategory("Others")));

        assertDoesNotThrow(() -> new DeleteCommandValidator("deleteItem 1").validate(items, categories));
    }

    /**
     * Verifies that an out-of-range index is rejected.
     */
    @Test
    void validate_indexOutOfRange_throwsException() {
        ItemList items = new ItemList();
        items.addItem(new Item("Coke Can", 50, 0.0, categories.getCategory("Others")));

        assertThrows(
                IllegalArgumentException.class,
                () -> new DeleteCommandValidator("deleteItem 99").validate(items, categories)
        );
    }

    /**
     * Verifies that index zero is rejected.
     */
    @Test
    void validate_indexZero_throwsException() {
        ItemList items = new ItemList();
        items.addItem(new Item("Coke Can", 50, 0.0, categories.getCategory("Others")));

        assertThrows(
                IllegalArgumentException.class,
                () -> new DeleteCommandValidator("deleteItem 0").validate(items, categories)
        );
    }

    /**
     * Verifies that a missing index is rejected.
     */
    @Test
    void validate_missingIndex_throwsException() {
        ItemList items = new ItemList();
        items.addItem(new Item("Coke Can", 50, 0.0, categories.getCategory("Others")));

        assertThrows(
                IllegalArgumentException.class,
                () -> new DeleteCommandValidator("deleteItem").validate(items, categories)
        );
    }

    /**
     * Verifies that a non-numeric index is rejected.
     */
    @Test
    void validate_nonNumericIndex_throwsException() {
        ItemList items = new ItemList();
        items.addItem(new Item("Coke Can", 50, 0.0, categories.getCategory("Others")));

        assertThrows(
                IllegalArgumentException.class,
                () -> new DeleteCommandValidator("deleteItem abc").validate(items, categories)
        );
    }
}
