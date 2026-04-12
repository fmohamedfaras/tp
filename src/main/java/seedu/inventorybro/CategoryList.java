package seedu.inventorybro;

import java.util.ArrayList;

/**
 * Represents the list of active categories available in the application.
 */
public class CategoryList {
    private final ArrayList<Category> categories;

    public CategoryList() {
        this.categories = new ArrayList<>();
        // The default fallback category is always instantiated first
        this.categories.add(new Category("Others"));
    }

    public void addCategory(Category category) {
        assert category != null : "Cannot add a null category";
        categories.add(category);
    }

    /**
     * Checks if a category already exists (case-insensitive).
     */
    public boolean containsCategory(String categoryName) {
        for (Category c : categories) {
            if (c.getName().equalsIgnoreCase(categoryName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves the Category object by name.
     * Returns the default "Others" category if the name is not found.
     */
    public Category getCategory(String categoryName) {
        for (Category c : categories) {
            if (c.getName().equalsIgnoreCase(categoryName)) {
                return c;
            }
        }
        return categories.get(0); // Safely falls back to "Others"
    }

    /**
     * Returns the full list of categories.
     */
    public ArrayList<Category> getCategories() {
        return categories;
    }
}
