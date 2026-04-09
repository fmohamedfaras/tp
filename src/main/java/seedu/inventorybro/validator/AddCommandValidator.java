package seedu.inventorybro.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.inventorybro.ItemList;

//@@author kenpegrasio
/**
 * Validates the raw input string for the addItem command.
 */
public class AddCommandValidator implements Validator {
    private static final Pattern ADD_COMMAND_PATTERN =
            Pattern.compile("^addItem d/(.*?) q/(-?\\d+) p/(-?\\d+(\\.\\d+)?)$");
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

    /**
     * Checks that the input matches the {@code addItem d/NAME q/INITIAL_QUANTITY p/PRICE} pattern,
     * that quantity is not negative, that price rounds to at least 0.01,
     * and that no item with the same name already exists in the inventory.
     *
     * @param items The current inventory; used to check for duplicate names.
     * @throws IllegalArgumentException if the input does not conform to the expected format,
     *                                  quantity or price is not positive, or the item name already exists.
     */
    @Override
    public void validate(ItemList items) {
        Matcher matcher = ADD_COMMAND_PATTERN.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(
                    "Invalid addItem format! Use: addItem d/NAME q/INITIAL_QUANTITY p/PRICE"
            );
        }
        int quantity = Integer.parseInt(matcher.group(2));
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        double price = Double.parseDouble(matcher.group(3));
        if (Math.round(price * 100) <= 0) {
            throw new IllegalArgumentException("Price must be at least 0.01 when rounded");
        }
        String name = matcher.group(1).trim();
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be empty.");
        }
        new DuplicateItemValidator(name).validate(items);
    }
}
