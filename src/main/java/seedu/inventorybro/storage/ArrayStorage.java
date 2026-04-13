package seedu.inventorybro.storage;

import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;
import seedu.inventorybro.Category;
import seedu.inventorybro.CategoryList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

//@@author elliotjohnwu
/**
 * Handles saving and loading the ItemList.
 * Extends Storage with Item-specific encode and decode logic.
 */
public class ArrayStorage extends Storage<Item> {

    private static final String FILE_PATH = "./data/inventory.txt";
    private final CategoryList masterCategories;
    /**
     * Creates an ArrayStorage using the default inventory file path.
     * @param categories The master CategoryList to link loaded items to.
     */
    public ArrayStorage(CategoryList categories) {
        super(FILE_PATH);
        assert categories != null : "CategoryList should not be null";
        this.masterCategories = categories;
    }

    /**
     * Convenience overload — saves an ItemList directly.
     *
     * @param items The ItemList to save.
     */
    public void saveArray(ItemList items) throws IOException {
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
            if (parts.length < 4) {
                throw new IllegalArgumentException("Expected at least 4 parts");
            }

            int quantity = Integer.parseInt(parts[0].trim());
            String description = parts[1].trim();
            double price = Double.parseDouble(parts[2].trim());
            String categoryName = parts[3].trim();

            if (description.isEmpty()) {
                throw new IllegalArgumentException("Description is empty");
            }
            if (!masterCategories.containsCategory(categoryName)) {
                masterCategories.addCategory(new Category(categoryName));
            }

            Category category = masterCategories.getCategory(categoryName);

            return new Item(description, quantity, price, category);

        } catch (Exception e) {
            logger.log(Level.WARNING, "Skipping corrupted item line {0}: {1} — Reason: {2}",
                    new Object[]{lineNumber, line, e.getMessage()});
            return null;
        }
    }
}

