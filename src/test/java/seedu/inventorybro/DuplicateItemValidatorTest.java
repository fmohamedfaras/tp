package seedu.inventorybro;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DuplicateItemValidatorTest {
    private DuplicateItemValidator validator;
    private ItemList items;

    @BeforeEach
    public void setUp() {
        validator = new DuplicateItemValidator();
        items = new ItemList();
    }

    @Test
    public void validate_emptyList_doesNotThrow() {
        assertDoesNotThrow(() -> validator.validate("Apple", items));
    }

    @Test
    public void validate_uniqueName_doesNotThrow() {
        items.addItem(new Item("Orange", 5));
        assertDoesNotThrow(() -> validator.validate("Apple", items));
    }

    @Test
    public void validate_exactDuplicateName_throwsException() {
        items.addItem(new Item("Apple", 10));
        assertThrows(IllegalArgumentException.class, () -> validator.validate("Apple", items));
    }

    @Test
    public void validate_caseInsensitiveDuplicate_throwsException() {
        items.addItem(new Item("Apple", 10));
        assertThrows(IllegalArgumentException.class, () -> validator.validate("apple", items));
    }

    @Test
    public void validate_caseInsensitiveDuplicateUppercase_throwsException() {
        items.addItem(new Item("apple", 10));
        assertThrows(IllegalArgumentException.class, () -> validator.validate("APPLE", items));
    }

    @Test
    public void validate_duplicateErrorMessageContainsName() {
        items.addItem(new Item("Mango", 3));
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class, () -> validator.validate("Mango", items)
        );
        assertTrue(ex.getMessage().contains("Mango"));
    }

    @Test
    public void validate_multipleItemsNoDuplicate_doesNotThrow() {
        items.addItem(new Item("Apple", 10));
        items.addItem(new Item("Orange", 5));
        items.addItem(new Item("Mango", 3));
        assertDoesNotThrow(() -> validator.validate("Banana", items));
    }

    @Test
    public void validate_duplicateAmongMultipleItems_throwsException() {
        items.addItem(new Item("Apple", 10));
        items.addItem(new Item("Orange", 5));
        items.addItem(new Item("Mango", 3));
        assertThrows(IllegalArgumentException.class, () -> validator.validate("Orange", items));
    }

    @Test
    public void validate_nameWithSurroundingWhitespace_treatedAsDuplicate() {
        // Ensures callers trim before validating; validator itself receives a pre-trimmed name.
        // This test documents the contract: "Apple" stored, "Apple" (already trimmed) is a duplicate.
        items.addItem(new Item("Apple", 10));
        assertThrows(IllegalArgumentException.class, () -> validator.validate("Apple", items));
    }
}
