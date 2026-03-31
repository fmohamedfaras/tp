package seedu.inventorybro.validator;

import seedu.inventorybro.ItemList;

/**
 * Validates the raw input string for the editItem command.
 */
public class EditCommandValidator implements Validator {
    private static final String FORMAT_ERROR = "Invalid edit format. "
            + "Use: edit INDEX d/NEW_NAME q/NEW_QUANTITY p/NEW_PRICE";
    private final String input;

    public EditCommandValidator(String input) {
        this.input = input;
    }

    @Override
    public void validate(ItemList items) {
        try {
            String[] words = input.split(" ", 2);
            if (words.length < 2) {
                throw new IllegalArgumentException(FORMAT_ERROR);
            }

            String[] parts = words[1].split("d/", 2);
            if (parts.length < 2) {
                throw new IllegalArgumentException(FORMAT_ERROR);
            }

            int index = Integer.parseInt(parts[0].trim()) - 1;
            if (index < 0 || index >= items.size()) {
                throw new IllegalArgumentException("Invalid index.");
            }

            String[] descParts = parts[1].split("q/", 2);
            if (descParts.length < 2) {
                throw new IllegalArgumentException(FORMAT_ERROR);
            }

            String newName = descParts[0].trim();
            if (newName.isEmpty()) {
                throw new IllegalArgumentException("Item name cannot be empty.");
            }

            String[] quantityParts = descParts[1].split("p/", 2);
            if (quantityParts.length < 2) {
                throw new IllegalArgumentException(FORMAT_ERROR);
            }

            int newQuantity = Integer.parseInt(quantityParts[0].trim());
            if (newQuantity < 0) {
                throw new IllegalArgumentException("Quantity cannot be negative.");
            }

            double newPrice = Double.parseDouble(quantityParts[1].trim());
            if (newPrice < 0) {
                throw new IllegalArgumentException("Price cannot be negative.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Index and quantity must be numbers, price must be a valid decimal.");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
