package seedu.inventorybro.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import seedu.inventorybro.CategoryList;
import seedu.inventorybro.ItemList;

//@@author adbsw
/**
 * Validates the raw input string for the listItems command.
 * Ensures the input matches the accepted regex format and checks if a requested category actually exists.
 */
public class ListCommandValidator implements Validator {
    // Upgraded Regex: Captures optional category AND optional sorting parameters
    private static final Pattern LIST_PATTERN =
            Pattern.compile("^listItems(?:\\s+c/(.+?))?(?:\\s+(price|quantity)\\s+(high|low))?$");
    private final String input;

    /**
     * Constructs a ListCommandValidator with the given user input.
     *
     * @param input The raw input string to be validated.
     */
    public ListCommandValidator(String input) {
        assert input != null : "Input line should not be null";
        this.input = input;
    }

    /**
     * Executes the validation logic for the listItems command.
     * Checks the format and throws an exception if the format is invalid or if a specified category does not exist.
     *
     * @param items      The current list of items in the inventory.
     * @param categories The master list of categories used to verify category existence.
     * @throws IllegalArgumentException if the format is invalid or the category is not found.
     */
    @Override
    public void validate(ItemList items, CategoryList categories) {
        Matcher matcher = LIST_PATTERN.matcher(input);

        if (!matcher.matches()) {
            throw new IllegalArgumentException(
                    "Invalid listItems format! Use: listItems [c/CATEGORY_NAME] [price/quantity] [high/low]"
            );
        }

        // If a category was requested, make sure it actually exists!
        String categoryInput = matcher.group(1);
        if (categoryInput != null) {
            categoryInput = categoryInput.trim();
            if (!categories.containsCategory(categoryInput)) {
                throw new IllegalArgumentException(
                        "The category [" + categoryInput.toUpperCase() + "] does not exist!"
                );
            }
        }
    }
}
