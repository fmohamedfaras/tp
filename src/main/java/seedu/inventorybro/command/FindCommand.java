package seedu.inventorybro.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;
import seedu.inventorybro.Ui;

/**
 * Finds and lists all items in the inventory whose description contains the keyword.
 */
public class FindCommand implements Command {
    private static final Pattern FIND_COMMAND_PATTERN = Pattern.compile("^findItem\\s+(.+)$");

    private final String input;

    /**
     * Creates a find command from the raw user input.
     *
     * @param input The full find command string.
     */
    public FindCommand(String input) {
        this.input = input;
    }

    /**
     * Parses the find command input and displays matching items.
     *
     * @param items The inventory item list to search.
     * @param ui    The UI object to handle user output.
     */
    @Override
    public void execute(ItemList items, Ui ui) {
        Matcher matcher = FIND_COMMAND_PATTERN.matcher(input);
        String[] words = input.split(" ", 2);

        if (!matcher.matches() || matcher.group(1).trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Invalid find format! Use: findItem KEYWORD"
            );
        }

        String keyword = matcher.group(1).trim().toLowerCase();
        boolean isFound = false;


        for (int i = 0; i < items.size(); i++) {
            Item item = items.getItem(i);

            if (item.getDescription().toLowerCase().contains(keyword)) {
                if (!isFound) {
                    ui.showMessage("Here are the matching items in your inventory:");
                    isFound = true;
                }
                ui.showMessage((i + 1) + ". " + item);
            }
        }

        if (!isFound) {
            ui.showMessage("No matching items found for: " + matcher.group(1).trim());
        }
    }
}
