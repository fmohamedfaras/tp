package seedu.inventorybro.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.inventorybro.CategoryList;
import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;

class EditPriceCommandValidatorTest {

    private ItemList items;
    private CategoryList categories;

    @BeforeEach
    void setUp() {
        items = new ItemList();
        categories = new CategoryList();
        items.addItem(new Item("Apple", 5, categories.getCategory("Others")));
        items.addItem(new Item("Banana", 3, categories.getCategory("Others")));
        items.addItem(new Item("Cherry", 8, categories.getCategory("Others")));
    }

    @Test
    void validate_validIntegerPrice_doesNotThrow() {
        assertDoesNotThrow(() ->
                new EditPriceCommandValidator("editPrice 1 p/10").validate(items, categories));
    }

    @Test
    void validate_validDecimalPrice_doesNotThrow() {
        assertDoesNotThrow(() ->
                new EditPriceCommandValidator("editPrice 1 p/9.99").validate(items, categories));
    }

    @Test
    void validate_validZeroPrice_doesNotThrow() {
        assertDoesNotThrow(() ->
                new EditPriceCommandValidator("editPrice 2 p/0.0").validate(items, categories));
    }

    @Test
    void validate_validInputLastIndex_doesNotThrow() {
        assertDoesNotThrow(() ->
                new EditPriceCommandValidator("editPrice 3 p/100.00").validate(items, categories));
    }

    @Test
    void validate_missingArguments_throwsFormatError() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                new EditPriceCommandValidator("editPrice").validate(items, categories));
        assertEquals("Invalid editPrice format. Use: editPrice INDEX p/NEW_PRICE",
                ex.getMessage());
    }

    @Test
    void validate_missingPricePrefix_throwsFormatError() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                new EditPriceCommandValidator("editPrice 1 9.99").validate(items, categories));
        assertEquals("Invalid editPrice format. Use: editPrice INDEX p/NEW_PRICE",
                ex.getMessage());
    }

    @Test
    void validate_indexZero_throwsInvalidIndex() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                new EditPriceCommandValidator("editPrice 0 p/9.99").validate(items, categories));
        assertEquals("Invalid index.", ex.getMessage());
    }

    @Test
    void validate_indexExceedsListSize_throwsInvalidIndex() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                new EditPriceCommandValidator("editPrice 4 p/9.99").validate(items, categories));
        assertEquals("Invalid index.", ex.getMessage());
    }

    @Test
    void validate_negativeIndex_throwsInvalidIndex() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                new EditPriceCommandValidator("editPrice -1 p/9.99").validate(items, categories));
        assertEquals("Invalid index.", ex.getMessage());
    }

    @Test
    void validate_nonNumericIndex_throwsNumberFormatMessage() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                new EditPriceCommandValidator("editPrice abc p/9.99").validate(items, categories));
        assertEquals("Index must be a number and price must be a valid decimal.", ex.getMessage());
    }

    @Test
    void validate_negativePrice_throwsNegativePriceError() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                new EditPriceCommandValidator("editPrice 1 p/-1.00").validate(items, categories));
        assertEquals("Price cannot be negative.", ex.getMessage());
    }

    @Test
    void validate_nonNumericPrice_throwsNumberFormatMessage() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                new EditPriceCommandValidator("editPrice 1 p/abc").validate(items, categories));
        assertEquals("Index must be a number and price must be a valid decimal.", ex.getMessage());
    }
}
