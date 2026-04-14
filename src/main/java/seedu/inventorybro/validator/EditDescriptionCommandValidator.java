package seedu.inventorybro.validator;

import seedu.inventorybro.CategoryList;
import seedu.inventorybro.ItemList;

/**
 * Validates the raw input string for the editDescription command.
 */
public class EditDescriptionCommandValidator implements Validator {

    private final String input;

    public EditDescriptionCommandValidator(String input) {
        this.input = input;
    }

    @Override
    public void validate(ItemList items, CategoryList categories) {
        if (!input.startsWith("editDescription ")) {
            throw new IllegalArgumentException("Invalid editDescription format. " +
                    "Use: editDescription INDEX d/NEW_DESCRIPTION");
        }

        String[] parts = input.split(" d/", 2);
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid editDescription format. " +
                    "Use: editDescription INDEX d/NEW_DESCRIPTION");
        }

        String indexString = parts[0].substring("editDescription".length()).trim();
        int index;

        try {
            index = Integer.parseInt(indexString);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Index must be a number.");
        }

        if (index <= 0 || index > items.size()) {
            throw new IllegalArgumentException("Invalid index.");
        }

        String newDescription = parts[1].trim();

        if (newDescription.isEmpty()) {
            throw new IllegalArgumentException("Item description cannot be empty.");
        }

        if (newDescription.contains("'")) {
            throw new IllegalArgumentException("Item description cannot contain single quotes (').");
        }

        // Checks if any *other* item in the list already has this exact description
        for (int i = 0; i < items.size(); i++) {
            if (i != (index - 1) && items.getItem(i).getDescription().trim().equalsIgnoreCase(newDescription)) {
                throw new IllegalArgumentException("An item with the description '"
                        + newDescription + "' already exists!");
            }
        }
    }
}
