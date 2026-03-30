package seedu.inventorybro.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;
import seedu.inventorybro.Ui;
import seedu.inventorybro.validator.AddCommandValidator;

//@@author kenpegrasio
/**
 * Adds a new item to the inventory.
 */
public class AddCommand implements Command {
    private static final Pattern ADD_COMMAND_PATTERN = Pattern.compile("^addItem d/(.*?) q/(\\d+)$");

    private final String input;

    /**
     * Creates an add command from the raw user input.
     *
     * @param input The full add command string.
     */
    public AddCommand(String input) {
        assert input != null : "Input should not be null";
        this.input = input;
    }

    /**
     * Parses the add command input and appends the new item to the list.
     *
     * @param items The inventory item list to update.
     */
    @Override
    public void execute(ItemList items, Ui ui) {
        assert items != null : "ItemList should not be null";
        assert ui != null : "Ui should not be null";

        new AddCommandValidator(input).validate(items);

        Matcher matcher = ADD_COMMAND_PATTERN.matcher(input);
        matcher.matches();
        String name = matcher.group(1);
        int quantity = Integer.parseInt(matcher.group(2));
        assert quantity >= 0 : "Parsed quantity should be non-negative";
        Item newItem = new Item(name, quantity);
        items.addItem(newItem);
        assert items.size() > 0 : "Item list should not be empty after adding";

        ui.showMessage("Added: " + newItem);
    }
}
