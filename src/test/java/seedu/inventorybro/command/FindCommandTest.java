package seedu.inventorybro.command;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.inventorybro.CategoryList;
import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;
import seedu.inventorybro.Ui;

/**
 * Tests for {@link FindCommand}.
 */
class FindCommandTest {

    private final Ui ui = new Ui();
    private final CategoryList categories = new CategoryList();

    @Test
    void execute_validKeyword_doesNotCrash() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 10, 0.0, categories.getCategory("Others")));
        items.addItem(new Item("Pineapple", 5, 0.0, categories.getCategory("Others")));
        items.addItem(new Item("Banana", 3, 0.0, categories.getCategory("Others")));

        assertDoesNotThrow(() -> new FindCommand("findItem app").execute(items, categories, ui));
        assertDoesNotThrow(() -> new FindCommand("findItem bAnAnA").execute(items, categories, ui));
    }

    @Test
    void execute_keywordNotFound_doesNotCrash() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 10, 0.0, categories.getCategory("Others")));

        assertDoesNotThrow(() -> new FindCommand("findItem ghost").execute(items, categories, ui));
    }

    @Test
    void execute_missingKeyword_throwsException() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 10, 0.0, categories.getCategory("Others")));

        assertThrows(
                IllegalArgumentException.class,
                () -> new FindCommand("findItem").execute(items, categories, ui)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> new FindCommand("findItem   ").execute(items, categories, ui)
        );
    }
}
