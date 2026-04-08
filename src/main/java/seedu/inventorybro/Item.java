package seedu.inventorybro;

/**
 * Represents an item with a description, quantity, and price.
 */
public class Item {
    protected String description;
    protected int quantity;
    protected double price;

    /**
     * Creates an item with the given description and quantity.
     *
     * @param description The item description.
     * @param quantity    The quantity of item.
     */
    public Item(String description, int quantity) {
        assert description != null && !description.isEmpty() : "Description should not be null or empty";
        assert quantity >= 0 : "Quantity should not be negative: " + quantity;
        this.description = description;
        this.quantity = quantity;
        this.price = 0.0;
    }

    //@@author elliotjohnwu
    /**
     * Creates an item with the given description, quantity, and price.
     *
     * @param description The item description.
     * @param quantity    The quantity of item.
     * @param price       The price of item.
     */
    public Item(String description, int quantity, double price) {
        assert description != null && !description.isEmpty() : "Description should not be null or empty";
        assert quantity >= 0 : "Quantity should not be negative: " + quantity;
        assert price >= 0 : "Price should not be negative: " + price;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }
    //@@author

    /**
     * Set this item quantity.
     *
     * @param quantity The quantity of item.
     */
    public void setQuantity(int quantity) {
        assert quantity >= 0 : "Quantity should not be negative: " + quantity;
        this.quantity = quantity;
    }

    /**
     * Sets the item description.
     *
     * @param description The updated item description.
     */
    public void setDescription(String description) {
        assert description != null && !description.isEmpty() : "Description should not be null or empty";
        this.description = description;
    }

    /**
     * Sets the item price.
     *
     * @param price The price of item.
     */
    //@@author vionyp
    public void setPrice(double price) {
        assert price >= 0 : "Price should not be negative: " + price;
        this.price = price;
    }

    /**
     * Returns the price of item.
     *
     * @return The price of item.
     */
    public double getPrice() {
        return this.price;
    }
    //@@author

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
        return (quantity) + " | " + description + " | " + String.format("%.2f", price);
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
        return description + " (Quantity: " + quantity + ", Price: $" + String.format("%.2f", price) + ")";
    }
}
