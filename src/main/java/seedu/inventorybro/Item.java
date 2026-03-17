package seedu.inventorybro;

/**
 * Represents an item with a description and quantity.
 */
public class Item {
    protected String description;
    protected int quantity;

    /**
     * Creates an item with the given description and quantity.
     *
     * @param description The item description.
     * @param quantity    The quantity of item.
     */
    public Item(String description, int quantity) {
        this.description = description;
        this.quantity = quantity;
    }

    /**
     * Set this item quantity.
     *
     * @param quantity The quantity of item.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Sets the item description.
     *
     * @param description The updated item description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the quantity of item.
     *
     * @return The quantity of item.
     */
    public int getQuantity() {
        return this.quantity;
    }


    /**
     * Returns the item in save file format.
     *
     * @return A string formatted for writing to the save file.
     */
    public String toSaveFormat() {
        return (quantity) + " | " + description;
    }

    /**
     * Returns the item description.
     *
     * @return The description string.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the string of the item's description and quantity.
     *
     * @return The string format of the item's description and quantity.
     */
    @Override
    public String toString() {
        return description + "(Quantity: " + quantity + ")";
    }
}


