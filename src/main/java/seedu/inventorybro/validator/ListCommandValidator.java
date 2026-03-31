package seedu.inventorybro.validator;

import seedu.inventorybro.ItemList;

//@@author adbsw
/**
 * Validates the raw input string for the listItems command.
 */
public class ListCommandValidator implements Validator {
    private final String input;

    public ListCommandValidator(String input) {
        assert input != null : "Input line should not be null";
        this.input = input;
    }

    @Override
    public void validate(ItemList items) {
        String[] words = input.split(" ");
        if (!words[0].equals("listItems") || words.length > 1) {
            throw new IllegalArgumentException("Did you mean 'listItems'?");
        }
    }
}
