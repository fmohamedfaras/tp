package seedu.inventorybro.command;

import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;
import seedu.inventorybro.Ui;

/**
 * Sets the price of an existing item.
 */
//@@author vionyp
public class PriceCommand implements Command {
    private final String input;

    /**
     * Creates a price command from the raw user input.
     *
     * @param input The full price command string.
     */
    public PriceCommand(String input) {
        assert input != null : "Input should not be null";
        this.input = input;
    }

    /**
     * Parses the price command input and updates the targeted item's price.
     *
     * @param items The inventory item list to update.
     * @param ui    The UI to display messages.
     */
    @Override
    public void execute(ItemList items, Ui ui) {
        assert items != null : "ItemList should not be null";
        assert ui != null : "Ui should not be null";

        try {
            // Format: setPrice INDEX p/PRICE
            String[] words = input.split(" ", 2);
            if (words.length < 2) {
                throw new IllegalArgumentException("Invalid setPrice format. "
                        + "Use: setPrice INDEX p/PRICE");
            }

            String[] parts = words[1].split("p/", 2);
            if (parts.length < 2) {
                throw new IllegalArgumentException("Invalid setPrice format. "
                        + "Use: setPrice INDEX p/PRICE");
            }

            int index = Integer.parseInt(parts[0].trim()) - 1;
            if (index < 0 || index >= items.size()) {
                throw new IllegalArgumentException("Invalid index.");
            }

            double price = Double.parseDouble(parts[1].trim());
            if (price < 0) {
                throw new IllegalArgumentException("Price cannot be negative.");
            }

            Item item = items.getItem(index);
            item.setPrice(price);

            ui.showMessage("Price updated: " + item);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Index must be a number and price must be a valid decimal.");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
//@@author
