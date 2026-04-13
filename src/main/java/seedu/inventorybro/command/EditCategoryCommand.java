package seedu.inventorybro.command;

import seedu.inventorybro.Category;
import seedu.inventorybro.CategoryList;
import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;
import seedu.inventorybro.Ui;
import seedu.inventorybro.validator.EditCategoryCommandValidator;

/**
 * Edits the category of an existing item in the inventory.
 */
public class EditCategoryCommand implements Command {

    private final String input;

    public EditCategoryCommand(String input) {
        assert input != null : "Input line should not be null";
        this.input = input;
    }

    @Override
    public void execute(ItemList items, CategoryList categories, Ui ui) {
        new EditCategoryCommandValidator(input).validate(items, categories);

        String[] parts = input.split(" c/", 2);
        int index = Integer.parseInt(parts[0].substring("editCategory".length()).trim()) - 1;
        String newCategoryName = parts[1].trim();

        Item item = items.getItem(index);
        Category newCategory = categories.getCategory(newCategoryName);

        item.setCategory(newCategory);

        ui.showMessage("Item category updated: " + item);
    }
}
