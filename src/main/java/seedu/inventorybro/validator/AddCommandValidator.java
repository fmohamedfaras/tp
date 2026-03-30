package seedu.inventorybro.validator;

import java.util.regex.Pattern;

import seedu.inventorybro.ItemList;

//@@author kenpegrasio
/**
 * Validates the raw input string for the addItem command.
 */
public class AddCommandValidator implements Validator {
    private static final Pattern ADD_COMMAND_PATTERN = Pattern.compile("^addItem d/(.*?) q/(\\d+)$");
    private final String input;

    public AddCommandValidator(String input) {
        assert input != null : "Input should not be null";
        this.input = input;
    }

    @Override
    public void validate(ItemList items) {
        assert input != null : "Input should not be null";
        if (!ADD_COMMAND_PATTERN.matcher(input).matches()) {
            throw new IllegalArgumentException(
                    "Invalid addItem format! Use: addItem d/NAME q/INITIAL_QUANTITY"
            );
        }
    }
}
