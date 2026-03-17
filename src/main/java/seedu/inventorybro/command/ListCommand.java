package seedu.inventorybro.command;

import seedu.inventorybro.ItemList;

/**
 * Displays the current inventory items.
 */
public class ListCommand implements Command {
    private final String input;

    /**
     * Creates a list command from the raw user input.
     *
     * @param input The full list command string.
     */
    public ListCommand(String input) {
        this.input = input;
    }

    /**
     * Validates the list command input and prints all items in the inventory.
     *
     * @param items The inventory item list to display.
     */
    @Override
    public void execute(ItemList items) {
        String[] words = input.split(" ");

        if (!words[0].equalsIgnoreCase("list") || words.length > 1) {
            throw new IllegalArgumentException("Did you mean 'list'?");
        }

        if (items.isEmpty()) {
            System.out.println("Your inventory is empty.");
        }

        System.out.println("Here are your current inventory items:");
        for (int i = 0; i < items.size(); i++) {
            int listIndex = i + 1;
            System.out.println(listIndex + ". " + items.getItem(i));
        }
    }
}
