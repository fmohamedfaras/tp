package seedu.inventorybro.command;

import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;

/**
 * Updates an existing item's description and quantity.
 */
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
    public void execute(ItemList items) {
        try {
            String[] words = input.split(" ", 2);
            if (words.length < 2) {
                throw new IllegalArgumentException("Invalid edit format. "
                        + "Use: edit INDEX d/NEW_NAME q/NEW_QUANTITY");
            }

            String[] parts = words[1].split("d/", 2);
            int index = Integer.parseInt(parts[0].trim()) - 1;
            if (index < 0 || index >= items.size()) {
                throw new IllegalArgumentException("Invalid index.");
            }

            String[] descParts = parts[1].split("q/", 2);
            String newName = descParts[0].trim();
            int newQuantity = Integer.parseInt(descParts[1].trim());

            Item item = items.getItem(index);
            item.setDescription(newName);
            item.setQuantity(newQuantity);

            System.out.println("Item updated: " + item);
        } catch (NumberFormatException e) {
            System.out.println("Index and quantity must be numbers.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
