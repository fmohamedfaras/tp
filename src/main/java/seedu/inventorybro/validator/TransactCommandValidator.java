package seedu.inventorybro.validator;

import seedu.inventorybro.CategoryList;
import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;

//@@author elliotjohnwu
/**
 * Validates the raw input string for the transact command.
 */
public class TransactCommandValidator implements Validator {
    private final String input;

    public TransactCommandValidator(String input) {
        assert input != null : "Input should not be null";
        this.input = input;
    }

    /**
     * Executes the validation logic.
     * Checks for correct formatting, prevents transactions of 0 quantity,
     * and prevents selling more stock than available.
     *
     * @param items      The current list of items in the inventory.
     * @param categories The master list of categories.
     * @throws IllegalArgumentException if the format is invalid, index is out of bounds,
     *      quantity is zero, or stock drops below zero.
     */
    @Override
    public void validate(ItemList items, CategoryList categories) {
        assert items != null : "ItemList should not be null";
        assert categories != null : "CategoryList should not be null when validating";

        try {
            String[] digits = getDigits();

            checkIfDigit(digits[0].trim());
            int index = Integer.parseInt(digits[0].trim()) - 1;
            if (index < 0 || index >= items.size()) {
                throw new IllegalArgumentException("Invalid index for transact.");
            }

            checkIfSignedDigit(digits[1].trim());
            int change = Integer.parseInt(digits[1].trim());

            if (change == 0) {
                throw new IllegalArgumentException("Transaction quantity cannot be zero! " +
                        "Please enter a positive number to restock, or a negative number for a sale.");
            }

            Item item = items.getItem(index);
            if (item.getQuantity() + change < 0) {
                throw new IllegalArgumentException("Transaction failed. Quantity cannot go below 0.");
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private String[] getDigits() {
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
        return digits;
    }

    private void checkIfDigit(String digits) {
        assert digits != null : "Digits string should not be null";
        for (char digit : digits.toCharArray()) {
            if (!Character.isDigit(digit)) {
                throw new IllegalArgumentException("Invalid transact, Index or Quantity Must be a digit");
            }
        }
    }

    private void checkIfSignedDigit(String digits) {
        assert digits != null : "Digits string should not be null";
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
