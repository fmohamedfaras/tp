package seedu.inventorybro.validator;

import seedu.inventorybro.CategoryList;
import seedu.inventorybro.ItemList;

/**
 * Validates the raw input string for the editPrice command.
 */
//@@author vionyp
public class EditPriceCommandValidator implements Validator {
    private static final String FORMAT_ERROR = "Invalid editPrice format. "
            + "Use: editPrice INDEX p/NEW_PRICE";
    private final String input;

    public EditPriceCommandValidator(String input) {
        assert input != null : "Input should not be null";
        this.input = input;
    }

    @Override
    public void validate(ItemList items, CategoryList categories) {
        try {
            String[] words = input.split(" ", 2);
            if (words.length < 2) {
                throw new IllegalArgumentException(FORMAT_ERROR);
            }

            String[] parts = words[1].split("p/", 2);
            if (parts.length < 2) {
                throw new IllegalArgumentException(FORMAT_ERROR);
            }

            int index = Integer.parseInt(parts[0].trim()) - 1;
            if (index < 0 || index >= items.size()) {
                throw new IllegalArgumentException("Invalid index.");
            }

            double newPrice = Double.parseDouble(parts[1].trim());

            if (Double.isNaN(newPrice)) {
                throw new IllegalArgumentException("Price must be a valid number, not 'NaN'.");
            }

            if (Double.isInfinite(newPrice)) {
                throw new IllegalArgumentException("Price is too large to be processed by the system.");
            }

            if (Math.round(newPrice * 100) <= 0) {
                throw new IllegalArgumentException("Price must be at least 0.01 when rounded.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Index must be a number and price must be a valid decimal.");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
//@@author
