package seedu.inventorybro;

/**
 * Validates that an item name does not already exist in the inventory.
 * Name comparison is case-insensitive so that "Apple" and "apple" are treated as the same item.
 */
public class DuplicateItemValidator {

    /**
     * Throws an {@link IllegalArgumentException} if the given name already exists in the item list.
     *
     * @param name  The item name to check.
     * @param items The current inventory.
     */
    public void validate(String name, ItemList items) {
        if (isDuplicate(name, items)) {
            throw new IllegalArgumentException(
                    "An item named '" + name + "' already exists in the inventory."
            );
        }
    }

    private boolean isDuplicate(String name, ItemList items) {
        for (int i = 0; i < items.size(); i++) {
            if (items.getItem(i).getDescription().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
}
