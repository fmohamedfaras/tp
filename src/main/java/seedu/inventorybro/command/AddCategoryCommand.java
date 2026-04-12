package seedu.inventorybro.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import seedu.inventorybro.Category;
import seedu.inventorybro.ItemList;
import seedu.inventorybro.CategoryList;
import seedu.inventorybro.Ui;
import seedu.inventorybro.validator.AddCategoryCommandValidator;

/**
 * Creates a new category and adds it to the master CategoryList.
 */
public class AddCategoryCommand implements Command {
    private static final Pattern ADD_CAT_PATTERN = Pattern.compile("^addCategory\\s+c/(.+)$");
    private final String input;

    public AddCategoryCommand(String input) {
        assert input != null : "Input should not be null";
        this.input = input;
    }

    @Override
    public void execute(ItemList items, CategoryList categories, Ui ui) {
        // 1. Validate
        new AddCategoryCommandValidator(input).validate(items, categories);

        // 2. Extract Data
        Matcher matcher = ADD_CAT_PATTERN.matcher(input);
        matcher.matches();
        String catName = matcher.group(1).trim();

        // 3. Create and Store
        Category newCategory = new Category(catName);
        categories.addCategory(newCategory);

        // 4. Feedback
        ui.showMessage("New category created successfully: " + newCategory);
    }
}
