package seedu.inventorybro.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;

//@@author adbsw
/**
 * Validation tests for {@link ListCommandValidator}.
 */
class ListCommandValidatorTest {

    /**
     * Verifies that the exact "listItems" input passes validation.
     */
    @Test
    void validate_validInput_noException() {
        ItemList items = new ItemList();

        assertDoesNotThrow(() -> new ListCommandValidator("listItems").validate(items));
    }

    /**
     * Verifies that a wrong command word is rejected.
     */
    @Test
    void validate_wrongCommandWord_throwsException() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 50));

        assertThrows(
                IllegalArgumentException.class,
                () -> new ListCommandValidator("list").validate(items)
        );
    }

    /**
     * Verifies that extra arguments after listItems are rejected.
     */
    @Test
    void validate_extraArguments_throwsException() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 50));

        assertThrows(
                IllegalArgumentException.class,
                () -> new ListCommandValidator("LiSt all").validate(items)
        );
    }

    /**
     * Verifies that a case-variant of the command is rejected.
     */
    @Test
    void validate_wrongCase_throwsException() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 50));

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new ListCommandValidator("LiStItEms").validate(items)
        );
        assertEquals("Did you mean 'listItems'?", ex.getMessage());
    }
}
