package seedu.inventorybro.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.inventorybro.ItemList;
import seedu.inventorybro.CategoryList;

/**
 * Validates the raw input string for the addCategory command.
 */
public class AddCategoryCommandValidator implements Validator {
    private static final Pattern ADD_CAT_PATTERN = Pattern.compile("^addCategory\\s+c/(.+)$");
    private final String input;

    public AddCategoryCommandValidator(String input) {
        assert input != null : "Input should not be null";
        this.input = input;
    }

    @Override
    public void validate(ItemList items, CategoryList categories) {
        Matcher matcher = ADD_CAT_PATTERN.matcher(input);

        if (!matcher.matches() || matcher.group(1).trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid format! Use: addCategory c/CATEGORY_NAME");
        }

        String catName = matcher.group(1).trim();
        // Check if the category already exists
        if (categories.containsCategory(catName)) {
            throw new IllegalArgumentException("The category [" + catName + "] already exists!");
        }
    }
}
