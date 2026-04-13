package seedu.inventorybro.validator;

import seedu.inventorybro.CategoryList;
import seedu.inventorybro.CommandWord;
import seedu.inventorybro.ItemList;

//@@author elliotjohnwu
/**
 * Validates the raw input string for the showTransactionHistory command.
 */
public class ShowTransactionHistoryCommandValidator implements Validator {
    private final String input;

    public ShowTransactionHistoryCommandValidator(String input) {
        assert input != null : "Input line should not be null";
        this.input = input;
    }

    @Override
    public void validate(ItemList items, CategoryList categories) {
        String[] words = input.split(" ");
        if (!words[0].equals(CommandWord.SHOW_HISTORY.getWord())) {
            throw new IllegalArgumentException("Invalid command format. Use: showHistory");
        }
        if (words.length > 1) {
            throw new IllegalArgumentException("showHistory does not take any arguments. Use: showHistory");
        }
    }
}
