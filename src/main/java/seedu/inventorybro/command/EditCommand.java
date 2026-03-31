package seedu.inventorybro.command;

import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;
import seedu.inventorybro.Ui;
import seedu.inventorybro.validator.EditCommandValidator;

/**
 * Updates an existing item's description, quantity, and price.
 */

//@@author vionyp
public class EditCommand implements Command {
    private final String input;

    /**
     * Creates an edit command from the raw user input.
     *
     * @param input The full edit command string.
     */
    public EditCommand(String input) {
        this.input = input;
    }

    /**
     * Parses the edit command input and updates the targeted item.
     *
     * @param items The inventory item list to update.
     */
    @Override
    public void execute(ItemList items, Ui ui) {
        new EditCommandValidator(input).validate(items);

        String[] words = input.split(" ", 2);
        String[] parts = words[1].split("d/", 2);
        int index = Integer.parseInt(parts[0].trim()) - 1;
        String[] descParts = parts[1].split("q/", 2);
        String newName = descParts[0].trim();
        String[] quantityParts = descParts[1].split("p/", 2);
        int newQuantity = Integer.parseInt(quantityParts[0].trim());
        double newPrice = Double.parseDouble(quantityParts[1].trim());

        Item item = items.getItem(index);
        item.setDescription(newName);
        item.setQuantity(newQuantity);
        item.setPrice(newPrice);

        ui.showMessage("Item updated: " + item);
    }
}
//@@author
