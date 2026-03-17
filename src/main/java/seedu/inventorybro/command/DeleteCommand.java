package seedu.inventorybro.command;

import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;

/**
 * Removes an item from the inventory using a one-based index.
 */
public class DeleteCommand implements Command {
    private final String input;

    /**
     * Creates a delete command from the raw user input.
     *
     * @param input The full delete command string.
     */
    public DeleteCommand(String input) {
        this.input = input;
    }

    /**
     * Parses the delete command input and removes the targeted item.
     *
     * @param items The inventory item list to update.
     */
    @Override
    public void execute(ItemList items) {
        String[] words = input.split(" ");

        if (words.length != 2 || !words[0].equalsIgnoreCase("deleteItem")) {
            throw new IllegalArgumentException("Invalid delete format! Use: deleteItem INDEX");
        }

        try {
            int index = Integer.parseInt(words[1]) - 1;

            if (index < 0 || index >= items.size()) {
                throw new IllegalArgumentException("Invalid index! Please provide a valid item number.");
            }

            Item removedItem = items.deleteItem(index);

            System.out.println("Noted, BRO. I've removed this item:");
            System.out.println("  " + removedItem);
            System.out.println("Now you have " + items.size() + " items in the list.");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("The index must be a number! Use: deleteItem INDEX");
        }
    }
}
