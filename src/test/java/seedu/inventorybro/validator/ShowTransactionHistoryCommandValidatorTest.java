package seedu.inventorybro.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.inventorybro.CategoryList;
import seedu.inventorybro.ItemList;

//@@author elliotjohnwu
/**
 * Validation tests for {@link ShowTransactionHistoryCommandValidator}.
 */
class ShowTransactionHistoryCommandValidatorTest {

    private final CategoryList categories = new CategoryList();

    /**
     * Verifies that the exact "showHistory" input passes validation.
     */
    @Test
    void validate_validInput_noException() {
        ItemList items = new ItemList();

        assertDoesNotThrow(() ->
                new ShowTransactionHistoryCommandValidator("showHistory").validate(items, categories)
        );
    }

    /**
     * Verifies that a wrong command word is rejected.
     */
    @Test
    void validate_wrongCommandWord_throwsException() {
        ItemList items = new ItemList();

        assertThrows(
                IllegalArgumentException.class,
                () -> new ShowTransactionHistoryCommandValidator("history").validate(items, categories)
        );
    }

    /**
     * Verifies that extra arguments after showHistory are rejected.
     */
    @Test
    void validate_extraArguments_throwsException() {
        ItemList items = new ItemList();

        assertThrows(
                IllegalArgumentException.class,
                () -> new ShowTransactionHistoryCommandValidator("showHistory all").validate(items, categories)
        );
    }

    /**
     * Verifies that a case-variant of the command is rejected.
     */
    @Test
    void validate_wrongCase_throwsException() {
        ItemList items = new ItemList();

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new ShowTransactionHistoryCommandValidator("ShowHistory").validate(items, categories)
        );

        assertEquals("Did you mean 'showHistory'?", ex.getMessage());
    }
}
