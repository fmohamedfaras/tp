package seedu.inventorybro.validator;

import seedu.inventorybro.ItemList;

/**
 * Validates the raw input string for the deleteItem command.
 */
public class DeleteCommandValidator implements Validator {
    private final String input;

    public DeleteCommandValidator(String input) {
        this.input = input;
    }

    @Override
    public void validate(ItemList items) {
        String[] words = input.split(" ");

        if (words.length != 2 || !words[0].equalsIgnoreCase("deleteItem")) {
            throw new IllegalArgumentException("Invalid delete format! Use: deleteItem INDEX");
        }

        try {
            int index = Integer.parseInt(words[1]) - 1;

            if (index < 0 || index >= items.size()) {
                throw new IllegalArgumentException("Invalid index! Please provide a valid item number.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("The index must be a number! Use: deleteItem INDEX");
        }
    }
}
