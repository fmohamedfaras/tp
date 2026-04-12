package seedu.inventorybro.storage;

import seedu.inventorybro.Category;
import seedu.inventorybro.CategoryList;

import java.io.IOException;
import java.util.logging.Level;

/**
 * Handles saving and loading the CategoryList to categories.txt.
 * Extends Storage with Category-specific encode and decode logic.
 */
public class CategoryStorage extends Storage<Category> {

    private static final String FILE_PATH = "./data/categories.txt";

    public CategoryStorage() {
        super(FILE_PATH);
    }

    /**
     * Convenience method — saves the CategoryList directly.
     */
    public void saveCategories(CategoryList categories) throws IOException {
        assert categories != null : "CategoryList should not be null";
        saveArray(categories.getCategories());
    }

    @Override
    protected String encode(Category category) {
        return category.getName();
    }

    @Override
    protected Category decode(String line, int lineNumber) {
        try {
            String name = line.trim();
            if (name.isEmpty()) {
                throw new IllegalArgumentException("Category name is empty");
            }
            return new Category(name);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Skipping corrupted category line {0}: {1} — Reason: {2}",
                    new Object[]{lineNumber, line, e.getMessage()});
            return null;
        }
    }
}
