package seedu.inventorybro.validator;

import seedu.inventorybro.CategoryList;
import seedu.inventorybro.ItemList;

//@@author kenpegrasio
/**
 * Contract for all input validators.
 * Implementations throw {@link IllegalArgumentException} when the input is invalid.
 */
public interface Validator {
    /**
     * Validates the command input against the rules of the implementing class.
     * Implementations may use {@code items} for context-sensitive checks such as index bounds.
     *
     * @param items The current inventory item list, provided for context-sensitive validation.
     * @throws IllegalArgumentException if the input fails validation.
     */
    void validate(ItemList items, CategoryList categories);
}
