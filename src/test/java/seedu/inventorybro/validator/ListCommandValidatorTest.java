package seedu.inventorybro.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.inventorybro.CategoryList;
import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;

//@@author adbsw
/**
 * Validation tests for {@link ListCommandValidator}.
 */
class ListCommandValidatorTest {
    private final CategoryList categories = new CategoryList();

    /**
     * Verifies that the exact listItems input passes validation.
     */
    @Test
    void validate_validInput_noException() {
        ItemList items = new ItemList();

        assertDoesNotThrow(() -> new ListCommandValidator("listItems").validate(items, categories));
        assertDoesNotThrow(() -> new ListCommandValidator("listItems quantity high").validate(items, categories));
        assertDoesNotThrow(() -> new ListCommandValidator("listItems quantity low").validate(items, categories));
        assertDoesNotThrow(() -> new ListCommandValidator("listItems price high").validate(items, categories));
        assertDoesNotThrow(() -> new ListCommandValidator("listItems price low").validate(items, categories));
    }

    /**
     * Verifies that a wrong command word is rejected.
     */
    @Test
    void validate_wrongCommandWord_throwsException() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 50, 0.0, categories.getCategory("Others")));

        assertThrows(
                IllegalArgumentException.class,
                () -> new ListCommandValidator("list").validate(items, categories)
        );
    }

    /**
     * Verifies that invalid descriptors in user input are rejected.
     */
    @Test
    void validate_invalidDescriptors_throwsException() {
        ItemList items = new ItemList();

        assertThrows(
                IllegalArgumentException.class,
                () -> new ListCommandValidator("listItems invalidField").validate(items, categories)
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> new ListCommandValidator("listItems quantity invalidOrder").validate(items, categories)
        );
    }

    /**
     * Verifies that extra descriptors in user input for are rejected.
     */
    @Test
    void validate_extraDescriptors_throwsException() {
        ItemList items = new ItemList();

        assertThrows(
                IllegalArgumentException.class,
                () -> new ListCommandValidator("ListItems quantity price high").validate(items, categories)
        );
    }

    /**
     * Verifies that a case-variant of the command is rejected.
     */
    @Test
    void validate_wrongCase_throwsException() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 50, 0.0, categories.getCategory("Others")));

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new ListCommandValidator("LiStItEms").validate(items, categories)
        );
        assertEquals("Did you mean 'listItems'?", ex.getMessage());
    }
}
