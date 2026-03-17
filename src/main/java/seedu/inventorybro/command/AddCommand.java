package seedu.inventorybro.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;

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
        this.input = input;
    }

    /**
     * Parses the add command input and appends the new item to the list.
     *
     * @param items The inventory item list to update.
     */
    @Override
    public void execute(ItemList items) {
        Matcher matcher = ADD_COMMAND_PATTERN.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(
                    "Invalid addItem format! Use: addItem d/NAME q/INITIAL_QUANTITY"
            );
        }

        String name = matcher.group(1);
        int quantity = Integer.parseInt(matcher.group(2));
        items.addItem(new Item(name, quantity));
    }
}
