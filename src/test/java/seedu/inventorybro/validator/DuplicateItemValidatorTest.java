package seedu.inventorybro.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.inventorybro.CategoryList;
import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;

class DuplicateItemValidatorTest {
    private ItemList items;
    private CategoryList categories;

    @BeforeEach
    public void setUp() {
        items = new ItemList();
        categories = new CategoryList();
    }

    @Test
    public void validate_emptyList_doesNotThrow() {
        assertDoesNotThrow(() -> new DuplicateItemValidator("Apple").validate(items, categories));
    }

    @Test
    public void validate_uniqueName_doesNotThrow() {
        items.addItem(new Item("Orange", 5, categories.getCategory("Others")));
        assertDoesNotThrow(() -> new DuplicateItemValidator("Apple").validate(items, categories));
    }

    @Test
    public void validate_exactDuplicateName_throwsException() {
        items.addItem(new Item("Apple", 10, categories.getCategory("Others")));
        assertThrows(IllegalArgumentException.class, () -> new
                DuplicateItemValidator("Apple").validate(items, categories));
    }

    @Test
    public void validate_caseInsensitiveDuplicate_throwsException() {
        items.addItem(new Item("Apple", 10, categories.getCategory("Others")));
        assertThrows(IllegalArgumentException.class, () ->
                new DuplicateItemValidator("apple").validate(items, categories));
    }

    @Test
    public void validate_caseInsensitiveDuplicateUppercase_throwsException() {
        items.addItem(new Item("apple", 10, categories.getCategory("Others")));
        assertThrows(IllegalArgumentException.class, () ->
                new DuplicateItemValidator("APPLE").validate(items, categories));
    }

    @Test
    public void validate_duplicateErrorMessageContainsName() {
        items.addItem(new Item("Mango", 3, categories.getCategory("Others")));
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class, () ->
                        new DuplicateItemValidator("Mango").validate(items, categories)
        );
        assertTrue(ex.getMessage().contains("Mango"));
    }

    @Test
    public void validate_multipleItemsNoDuplicate_doesNotThrow() {
        items.addItem(new Item("Apple", 10, categories.getCategory("Others")));
        items.addItem(new Item("Orange", 5, categories.getCategory("Others")));
        items.addItem(new Item("Mango", 3, categories.getCategory("Others")));
        assertDoesNotThrow(() -> new DuplicateItemValidator("Banana").validate(items, categories));
    }

    @Test
    public void validate_duplicateAmongMultipleItems_throwsException() {
        items.addItem(new Item("Apple", 10, categories.getCategory("Others")));
        items.addItem(new Item("Orange", 5, categories.getCategory("Others")));
        items.addItem(new Item("Mango", 3, categories.getCategory("Others")));
        assertThrows(IllegalArgumentException.class, () ->
                new DuplicateItemValidator("Orange").validate(items, categories));
    }

    @Test
    public void validate_nameWithSurroundingWhitespace_treatedAsDuplicate() {
        items.addItem(new Item("Apple", 10, categories.getCategory("Others")));
        assertThrows(IllegalArgumentException.class, () ->
                new DuplicateItemValidator("Apple").validate(items, categories));
    }
}
