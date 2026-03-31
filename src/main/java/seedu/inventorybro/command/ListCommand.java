package seedu.inventorybro.command;

import seedu.inventorybro.ItemList;
import seedu.inventorybro.Ui;

//@@author adbsw
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
        assert input != null : "Input line should not be null";
        this.input = input;
    }

    /**
     * Validates the list command input and prints all items in the inventory.
     *
     * @param items The inventory item list to display.
     * @param ui The ui object.
     */
    @Override
    public void execute(ItemList items, Ui ui) {
        String[] words = input.split(" ");

        if (!words[0].equals("listItems") || words.length > 1) {
            throw new IllegalArgumentException("Did you mean 'listItems'?");
        }

        if (items.isEmpty()) {
            ui.showMessage("Your inventory is empty.");
            return;
        }
        assert !items.isEmpty() : "List of items should not be empty";

        int listIndex = 0;
        ui.showMessage("Here are your current inventory items:");
        for (int i = 0; i < items.size(); i++) {
            listIndex = i + 1;
            System.out.println(listIndex + ". " + items.getItem(i));
        }
        assert listIndex == items.size() : "List index should be equal to total number " +
                "of items in list after iterating through and printing all items";
    }
}
