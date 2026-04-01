package seedu.inventorybro.storage;

import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;

import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Handles saving and loading the ItemList.
 * Extends Storage with Item-specific encode and decode logic.
 */
public class ArrayStorage extends Storage<Item> {

    private static final String FILE_PATH = "./data/inventory.txt";

    /**
     * Creates an ArrayStorage using the default inventory file path.
     */
    public ArrayStorage() {
        super(FILE_PATH);
    }

    /**
     * Convenience overload — saves an ItemList directly.
     *
     * @param items The ItemList to save.
     */
    public void saveArray(ItemList items) {
        assert items != null : "ItemList should not be null";
        saveArray(items.getItems());
    }

    /**
     * Convenience method — loads items and returns them as an ItemList.
     *
     * @return The loaded ItemList.
     */
    public ItemList loadItemList() {
        ArrayList<Item> loaded = load();
        return new ItemList(loaded);
    }

    /**
     * Encodes an Item into pipe-delimited save format.
     *
     * @param item The item to encode.
     * @return Encoded string e.g. "50 | Coke Can"
     */
    @Override
    protected String encode(Item item) {
        return item.toSaveFormat();
    }

    /**
     * Decodes a pipe-delimited line into an Item.
     * Returns null if the line is corrupted.
     *
     * @param line       The line to decode.
     * @param lineNumber The line number for logging.
     * @return The decoded Item, or null if corrupted.
     */
    @Override
    protected Item decode(String line, int lineNumber) {
        try {
            String[] parts = line.split(" \\| ");
            if (parts.length < 2) {
                throw new IllegalArgumentException("Expected at least 2 parts");
            }
            int quantity = Integer.parseInt(parts[0].trim());
            String description = parts[1].trim();
            if (description.isEmpty()) {
                throw new IllegalArgumentException("Description is empty");
            }
            return new Item(description, quantity);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Skipping corrupted item line {0}: {1} — Reason: {2}",
                    new Object[]{lineNumber, line, e.getMessage()});
            return null;
        }
    }
}

