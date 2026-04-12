package seedu.inventorybro.validator;

import seedu.inventorybro.CategoryList;
import seedu.inventorybro.CommandWord;
import seedu.inventorybro.ItemList;

//@@author adbsw
/**
 * Validates the raw input string for the help command.
 */
public class HelpCommandValidator implements Validator {
    private final String input;

    /**
     * Creates a help command validator from the raw user input
     * @param input The full help command string.
     */
    public HelpCommandValidator(String input) {
        assert input != null : "Input line should not be null";
        this.input = input;
    }

    /**
     * Validates the help command input and checks if a valid command name is specified in the input.
     *
     * @param items The current inventory item list, provided for context-sensitive validation.
     */
    @Override
    public void validate(ItemList items, CategoryList categories) {
        String[] words = input.split(" ");

        if (!words[0].equals("help")) {
            throw new IllegalArgumentException("Did you mean 'help'?");
        }

        if (words.length > 1) {
            validateCommandSpecified(words);
        }
    }

    /**
     * Checks if only one command name is specified in the user input and is valid, else throws an error.
     *
     * @param words String array of words from the raw user input.
     */
    private static void validateCommandSpecified(String[] words) {
        if (!isValidCommand(words[1]) || !(words.length == 2)) {
            throw new IllegalArgumentException("Invalid help format. "
                    + "Use: help [VALID_COMMAND_NAME]"
                    + System.lineSeparator()
                    + "or enter 'help' to display each command name and their summaries.");
        }
    }

    /**
     * Checks if the command word is valid and returns true, else returns false.
     *
     * @param word The command word.
     * @return true if the command word is valid, else returns false.
     */
    private static boolean isValidCommand(String word) {
        for (CommandWord c : CommandWord.values()) {
            if (c.getWord().equals(word)) {
                return true;
            }
        }
        return false;
    }
}
