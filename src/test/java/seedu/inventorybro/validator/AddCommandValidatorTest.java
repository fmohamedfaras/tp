package seedu.inventorybro.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.inventorybro.ItemList;

//@@author kenpegrasio
/**
 * Validation tests for {@link AddCommandValidator}.
 */
class AddCommandValidatorTest {

    private final ItemList items = new ItemList();

    /**
     * Verifies that a well-formed addItem input passes validation.
     */
    @Test
    void validate_validInput_noException() {
        assertDoesNotThrow(() -> new AddCommandValidator("addItem d/Apple q/10").validate(items));
    }

    /**
     * Verifies that a well-formed addItem input with a multi-word name passes validation.
     */
    @Test
    void validate_multiWordName_noException() {
        assertDoesNotThrow(() -> new AddCommandValidator("addItem d/Green Apple q/25").validate(items));
    }

    /**
     * Verifies that malformed addItem input is rejected.
     */
    @Test
    void validate_invalidFormat_throwsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new AddCommandValidator("addItem Apple 10").validate(items)
        );
    }

    /**
     * Verifies that missing quantity prefix is rejected.
     */
    @Test
    void validate_missingQuantityPrefix_throwsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new AddCommandValidator("addItem d/Apple").validate(items)
        );
    }
}
