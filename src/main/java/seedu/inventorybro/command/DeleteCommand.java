package seedu.inventorybro.command;

import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;
import seedu.inventorybro.Ui;
import seedu.inventorybro.validator.DeleteCommandValidator;

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
    //@@author fmohamedfaras
    @Override
    public void execute(ItemList items, Ui ui) {
        new DeleteCommandValidator(input).validate(items);

        String[] words = input.split(" ");
        int index = Integer.parseInt(words[1]) - 1;
        Item removedItem = items.deleteItem(index);

        ui.showMessage("Noted, BRO. I've removed this item:\n  " + removedItem +
                "\nNow you have " + items.size() + " items in the list.");
    }
}
