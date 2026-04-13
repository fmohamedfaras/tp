package seedu.inventorybro;

/**
 * Represents an item with a description, quantity, price and category.
 */
public class Item {
    protected String description;
    protected int quantity;
    protected double price;
    protected Category category;

    /**
     * Creates an item with the given description, quantity and category.
     *
     * @param description The item description.
     * @param quantity    The quantity of item.
     * @param category    The category this item belongs to.
     */
    public Item(String description, int quantity, Category category) {
        assert description != null && !description.isEmpty() : "Description should not be null or empty";
        assert quantity >= 0 : "Quantity should not be negative: " + quantity;
        assert category != null : "Category should not be null";
        this.description = description;
        this.quantity = quantity;
        this.price = 0.0;
        this.category = category;
    }

    //@@author elliotjohnwu
    /**
     * Creates an item with the given description, quantity, price and category.
     *
     * @param description The item description.
     * @param quantity    The quantity of item.
     * @param price       The price of item.
     * @param category    The category this item belongs to.
     */
    public Item(String description, int quantity, double price, Category category) {
        assert description != null && !description.isEmpty() : "Description should not be null or empty";
        assert quantity >= 0 : "Quantity should not be negative: " + quantity;
        assert price >= 0 : "Price should not be negative: " + price;
        assert category != null : "Category should not be null";
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
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
     * Returns the category of item.
     *
     * @return The category of item.
     */
    public Category getCategory() {
        return this.category;
    }

    /**
     * Sets the item category.
     *
     * @param category The price of item.
     */
    public void setCategory(Category category) {
        assert category != null : "Category should not be null";
        this.category = category;
    }

    /**
     * Returns the item in save file format.
     *
     * @return A string formatted for writing to the save file.
     */
    public String toSaveFormat() {
        return (quantity) + " | " + description + " | " + String.format("%.2f", price) + " | " + category.getName();
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
        return category.toString() + " " + description + " (Quantity: " + quantity + ", Price: $"
            + String.format("%.2f", price) + ")";
    }
}
