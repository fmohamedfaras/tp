package seedu.inventorybro.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.inventorybro.CategoryList;
import seedu.inventorybro.ItemList;

//@@author adbsw
/**
 * Validation tests for {@link HelpCommandValidator}.
 */
class HelpCommandValidatorTest {

    private final ItemList items = new ItemList();
    private final CategoryList categories = new CategoryList();

    /**
     * Verifies that bare "help" passes validation.
     */
    @Test
    void validate_bareHelp_noException() {
        assertDoesNotThrow(() -> new HelpCommandValidator("help").validate(items, categories));
    }

    /**
     * Verifies that "help" with a valid command name passes validation.
     */
    @Test
    void validate_validCommandSpecified_noException() {
        assertDoesNotThrow(() -> new HelpCommandValidator("help addItem").validate(items, categories));
        assertDoesNotThrow(() -> new HelpCommandValidator("help deleteItem").validate(items, categories));
        assertDoesNotThrow(() -> new HelpCommandValidator("help transact").validate(items, categories));
        assertDoesNotThrow(() -> new HelpCommandValidator("help listItems").validate(items, categories));
        assertDoesNotThrow(() -> new HelpCommandValidator("help exit").validate(items, categories));
    }

    /**
     * Verifies that a wrong command word is rejected.
     */
    @Test
    void validate_wrongCommandWord_throwsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new HelpCommandValidator("HELP").validate(items, categories)
        );
    }

    /**
     * Verifies that an invalid command name after "help" is rejected.
     */
    @Test
    void validate_invalidCommandSpecified_throwsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new HelpCommandValidator("help unknownCommand").validate(items, categories)
        );
    }

    /**
     * Verifies that extra arguments beyond a valid command name are rejected.
     */
    @Test
    void validate_tooManyArguments_throwsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new HelpCommandValidator("help addItem extra").validate(items, categories)
        );
    }
}
