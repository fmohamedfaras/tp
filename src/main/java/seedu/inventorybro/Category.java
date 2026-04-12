package seedu.inventorybro;

/**
 * Represents a product category in the inventory.
 */
public class Category {
    private String name;

    public Category(String name) {
        assert name != null && !name.trim().isEmpty() : "Category name cannot be null or empty";
        this.name = name.trim().toUpperCase();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "[" + name + "]";
    }

    /**
     * Ensures categories are compared ignoring case.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Category otherCategory = (Category) obj;
        return name.equalsIgnoreCase(otherCategory.name);
    }
}
