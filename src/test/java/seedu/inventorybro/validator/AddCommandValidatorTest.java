package seedu.inventorybro.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.inventorybro.CategoryList;
import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;

//@@author kenpegrasio
/**
 * Validation tests for {@link AddCommandValidator}.
 */
class AddCommandValidatorTest {

    private final ItemList items = new ItemList();
    private final CategoryList categories = new CategoryList();

    /**
     * Verifies that a well-formed addItem input with an integer price passes validation.
     */
    @Test
    void validate_validInputWithIntegerPrice_noException() {
        assertDoesNotThrow(() ->
                new AddCommandValidator("addItem d/Apple q/10 p/5 c/Others").validate(items, categories));
    }

    /**
     * Verifies that a well-formed addItem input with a decimal price passes validation.
     */
    @Test
    void validate_validInputWithDecimalPrice_noException() {
        assertDoesNotThrow(() ->
                new AddCommandValidator("addItem d/Apple q/10 p/5.99 c/Others").validate(items, categories));
    }

    /**
     * Verifies that a well-formed addItem input with a multi-word name passes validation.
     */
    @Test
    void validate_multiWordNameWithPrice_noException() {
        assertDoesNotThrow(() ->
                new AddCommandValidator("addItem d/Green Apple q/25 p/3.50 c/Others").validate(items, categories));
    }

    /**
     * Verifies that an empty name is rejected.
     */
    @Test
    void validate_emptyName_throwsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new AddCommandValidator("addItem d/ q/10 p/5 c/Others").validate(items, categories)
        );
    }

    /**
     * Verifies that a whitespace-only name is rejected after trimming.
     */
    @Test
    void validate_whitespaceOnlyName_throwsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new AddCommandValidator("addItem d/   q/10 p/5 c/Others").validate(items, categories)
        );
    }

    /**
     * Verifies that missing the price prefix is rejected.
     */
    @Test
    void validate_missingPricePrefix_throwsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new AddCommandValidator("addItem d/Apple q/10 c/Others").validate(items, categories)
        );
    }

    /**
     * Verifies that malformed addItem input (missing prefixes) is rejected.
     */
    @Test
    void validate_invalidFormat_throwsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new AddCommandValidator("addItem Apple 10 c/Others").validate(items, categories)
        );
    }

    /**
     * Verifies that missing quantity prefix is rejected.
     */
    @Test
    void validate_missingQuantityPrefix_throwsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new AddCommandValidator("addItem d/Apple p/5 c/Others").validate(items, categories)
        );
    }

    /**
     * Verifies that a negative quantity gives a clear error (not a format error).
     */
    @Test
    void validate_negativeQuantity_throwsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new AddCommandValidator("addItem d/Apple q/-5 p/5 c/Others").validate(items, categories)
        );
    }

    /**
     * Verifies that a negative price gives a clear error (not a format error).
     */
    @Test
    void validate_negativePrice_throwsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new AddCommandValidator("addItem d/Apple q/10 p/-1.50 c/Others").validate(items, categories)
        );
    }

    /**
     * Verifies that a quantity of zero is accepted (out-of-stock item).
     */
    @Test
    void validate_zeroQuantity_noException() {
        assertDoesNotThrow(() ->
                new AddCommandValidator("addItem d/Apple q/0 p/5 c/Others").validate(items, categories));
    }

    /**
     * Verifies that a price of zero is rejected.
     */
    @Test
    void validate_zeroPriceWithValidQuantity_throwsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new AddCommandValidator("addItem d/Apple q/10 p/0 c/Others").validate(items, categories)
        );
    }

    /**
     * Verifies that a price of 0.00 (decimal zero) is rejected.
     */
    @Test
    void validate_decimalZeroPrice_throwsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new AddCommandValidator("addItem d/Apple q/10 p/0.00 c/Others").validate(items, categories)
        );
    }

    /**
     * Verifies that a price that rounds to 0.00 (e.g. 0.001) is rejected.
     */
    @Test
    void validate_priceTooSmallToDisplay_throwsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new AddCommandValidator("addItem d/Apple q/10 p/0.001 c/Others").validate(items, categories)
        );
    }

    /**
     * Verifies that adding an item whose name already exists (case-insensitive) is rejected.
     */
    @Test
    void validate_duplicateName_throwsException() {
        items.addItem(new Item("Apple", 10, categories.getCategory("Others")));

        assertThrows(
                IllegalArgumentException.class,
                () -> new AddCommandValidator("addItem d/apple q/5 p/1 c/Others").validate(items, categories)
        );
    }

    /**
     * Verifies that a name with trailing whitespace is trimmed before the duplicate check,
     * so "Apple " and "Apple" are treated as the same item.
     */
    @Test
    void validate_duplicateNameWithTrailingSpace_throwsException() {
        items.addItem(new Item("Apple", 10, categories.getCategory("Others")));

        assertThrows(
                IllegalArgumentException.class,
                () -> new AddCommandValidator("addItem d/Apple  q/5 p/1 c/Others").validate(items, categories)
        );
    }

    /**
     * Verifies that adding a unique name to a non-empty list passes validation.
     */
    @Test
    void validate_uniqueNameWithOtherItemsPresent_noException() {
        items.addItem(new Item("Banana", 5, categories.getCategory("Others")));

        assertDoesNotThrow(() ->
                new AddCommandValidator("addItem d/Apple q/10 p/3 c/Others").validate(items, categories));
    }

    /**
     * Verifies that a duplicate quantity flag is rejected.
     */
    @Test
    void validate_duplicateQuantityFlag_throwsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new AddCommandValidator("addItem d/Double Test q/10 q/20 p/1.50 c/Others")
                        .validate(items, categories)
        );
    }

    /**
     * Verifies that a duplicate description flag is rejected.
     */
    @Test
    void validate_duplicateDescriptionFlag_throwsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new AddCommandValidator("addItem d/Test d/Real q/10 p/1.50 c/Others")
                        .validate(items, categories)
        );
    }

    /**
     * Verifies that a duplicate price flag is rejected.
     */
    @Test
    void validate_duplicatePriceFlag_throwsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new AddCommandValidator("addItem d/Test p/bad q/10 p/1.50 c/Others")
                        .validate(items, categories)
        );
    }

    /**
     * Verifies that a quantity exceeding Integer.MAX_VALUE is rejected.
     */
    @Test
    void validate_quantityOverflow_throwsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new AddCommandValidator("addItem d/Apple q/99999999999999999999 p/1.50 c/Others")
                        .validate(items, categories)
        );
    }

    /**
     * Verifies that a price large enough to produce Infinity is rejected.
     */
    @Test
    void validate_priceOverflow_throwsException() {
        String hugePrice = "9".repeat(400);
        assertThrows(
                IllegalArgumentException.class,
                () -> new AddCommandValidator("addItem d/Apple q/10 p/" + hugePrice + " c/Others")
                        .validate(items, categories)
        );
    }

    /**
     * Verifies that a name containing a single quote is rejected.
     */
    @Test
    void validate_nameWithSingleQuote_throwsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new AddCommandValidator("addItem d/Coke's Can q/10 p/1.50 c/Others").validate(items, categories)
        );
    }

    /**
     * Verifies that passing null as input to the constructor triggers an AssertionError.
     */
    @Test
    void constructor_nullInput_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> new AddCommandValidator(null));
    }
}
