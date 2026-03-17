package seedu.inventorybro.command;

import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;
import seedu.inventorybro.Ui;

/**
 * Adjusts an item's quantity by a signed transaction amount.
 */
public class TransactCommand implements Command {
    private final String input;

    /**
     * Creates a transact command from the raw user input.
     *
     * @param input The full transact command string.
     */
    public TransactCommand(String input) {
        this.input = input;
    }

    /**
     * Parses the transact command input and updates the targeted item's quantity.
     *
     * @param items The inventory item list to update.
     */
    @Override
    public void execute(ItemList items, Ui ui) {
        try {
            String[] words = input.split(" ", 2);
            if (words.length < 2 || words[1].isEmpty() || !words[0].equalsIgnoreCase("transact")) {
                throw new IllegalArgumentException("Invalid transact format. "
                        + "Use: transact INDEX q/CHANGE_IN_QUANTITY");
            }

            String[] digits = words[1].split("q/", 2);
            if (digits.length < 2) {
                throw new IllegalArgumentException("Invalid transact format. "
                        + "Use: transact INDEX q/CHANGE_IN_QUANTITY");
            }

            checkIfDigit(digits[0].trim());
            int index = Integer.parseInt(digits[0].trim()) - 1;
            if (index < 0 || index >= items.size()) {
                throw new IllegalArgumentException("Invalid index for transact.");
            }

            checkIfSignedDigit(digits[1].trim());
            int change = Integer.parseInt(digits[1].trim());
            Item item = items.getItem(index);
            int newQuantity = item.getQuantity() + change;
            if (newQuantity < 0) {
                throw new IllegalArgumentException("Transaction failed. Quantity cannot go below 0.");
            }

            item.setQuantity(newQuantity);
            ui.showMessage("Transaction recorded.\n" + item.getDescription() + " new quantity: " + newQuantity);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * Ensures that the provided string contains only digit characters.
     *
     * @param digits The string to validate.
     */
    private void checkIfDigit(String digits) {
        for (char digit : digits.toCharArray()) {
            if (!Character.isDigit(digit)) {
                throw new IllegalArgumentException("Invalid transact, Index or Quantity Must be a digit");
            }
        }
    }

    /**
     * Ensures that the provided string is either a positive integer or a negative integer.
     *
     * @param digits The signed numeric string to validate.
     */
    private void checkIfSignedDigit(String digits) {
        if (digits.isEmpty()) {
            throw new IllegalArgumentException("Invalid transact. Quantity cannot be empty.");
        }

        int start = 0;
        if (digits.charAt(0) == '-') {
            start = 1;
        }

        if (start == 1 && digits.length() == 1) {
            throw new IllegalArgumentException("Invalid transact. Quantity cannot be just a minus sign.");
        }

        checkIfDigit(digits.substring(start));
    }
}
