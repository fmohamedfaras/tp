package seedu.inventorybro.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.inventorybro.Category;
import seedu.inventorybro.CategoryList;
import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;
import seedu.inventorybro.Ui;
import seedu.inventorybro.validator.AddCommandValidator;

//@@author kenpegrasio
/**
 * Adds a new item to the inventory.
 */
public class AddCommand implements Command {
    private static final Pattern ADD_COMMAND_PATTERN =
            Pattern.compile("^addItem\\s+d/(.+?)\\s+q/(\\d+)\\s+p/(\\d+(\\.\\d+)?)\\s+c/(.+)$");

    private final String input;

    /**
     * Creates an add command bound to the given raw user input.
     *
     * @param input The full command string, expected to match
     *              {@code addItem d/NAME q/INITIAL_QUANTITY p/PRICE}.
     */
    public AddCommand(String input) {
        assert input != null : "Input should not be null";
        this.input = input;
    }

    /**
     * Validates the input, then creates a new {@link Item} and appends it to the inventory.
     *
     * @param items The inventory item list to append the new item to.
     * @param ui    The UI object used to display the confirmation message.
     * @param categories The category list to refer to.
     * @throws IllegalArgumentException if the input does not match the expected format.
     */
    @Override
    public void execute(ItemList items, CategoryList categories, Ui ui) {
        assert items != null : "ItemList should not be null";
        assert categories != null : "CategoryList should not be null";
        assert ui != null : "Ui should not be null";

        new AddCommandValidator(input).validate(items, categories);

        Matcher matcher = ADD_COMMAND_PATTERN.matcher(input);
        matcher.matches();

        String name = matcher.group(1).trim();
        int quantity = Integer.parseInt(matcher.group(2));
        double price = Double.parseDouble(matcher.group(3));
        String categoryInput = matcher.group(5).trim();

        Category targetCategory = categories.getCategory(categoryInput);

        Item newItem = new Item(name, quantity, price, targetCategory);
        items.addItem(newItem);

        ui.showMessage("Added: " + newItem);
    }
}
