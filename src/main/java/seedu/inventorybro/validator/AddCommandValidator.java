package seedu.inventorybro.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.inventorybro.CategoryList;
import seedu.inventorybro.ItemList;

//@@author kenpegrasio
/**
 * Validates the raw input string for the addItem command.
 */
public class AddCommandValidator implements Validator {
    private static final Pattern ADD_COMMAND_PATTERN =
            Pattern.compile("^addItem\\s+d/(.+?)\\s+q/(\\d+)\\s+p/(\\d+(\\.\\d+)?)\\s+c/(.+)$");
    private final String input;

    /**
     * Creates a validator bound to the given raw input string.
     *
     * @param input The full addItem command string to validate.
     */
    public AddCommandValidator(String input) {
        assert input != null : "Input should not be null";
        this.input = input;
    }

    public static Pattern getAddCommandPattern() {
        return ADD_COMMAND_PATTERN;
    }

    /**
     * Checks that the input matches the {@code addItem d/NAME q/INITIAL_QUANTITY p/PRICE} pattern,
     * that quantity is not negative, that price rounds to at least 0.01,
     * and that no item with the same name already exists in the inventory.
     *
     * @param items The current inventory; used to check for duplicate names.
     * @param categories The current list of categories; used to check for valid category
     * @throws IllegalArgumentException if the input does not conform to the expected format,
     *                                  quantity or price is not positive, or the item name already exists.
     */
    @Override
    public void validate(ItemList items, CategoryList categories) {
        Matcher matcher = ADD_COMMAND_PATTERN.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(
                    "Invalid addItem format! Use: addItem d/NAME q/INITIAL_QUANTITY p/PRICE c/CATEGORY"
            );
        }

        String name = matcher.group(1).trim();
        if (name.contains("d/") || name.contains("q/") || name.contains("p/")) {
            throw new IllegalArgumentException(
                    "Duplicate parameter detected. Use: addItem d/NAME q/INITIAL_QUANTITY p/PRICE"
            );
        }
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be empty.");
        }

        new DuplicateItemValidator(name).validate(items, categories);

        int quantity = Integer.parseInt(matcher.group(2));
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }

        double price = Double.parseDouble(matcher.group(3));
        if (Math.round(price * 100) <= 0) {
            throw new IllegalArgumentException("Price must be at least 0.01 when rounded");
        }

        String categoryName = matcher.group(5).trim();
        if (!categories.containsCategory(categoryName)) {
            throw new IllegalArgumentException("The category [" + categoryName + "] does not exist! "
                    + "Please create it first using: addCategory c/" + categoryName
            );
        }
    }
}
