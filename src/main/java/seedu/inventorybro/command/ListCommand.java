package seedu.inventorybro.command;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;
import seedu.inventorybro.CategoryList;
import seedu.inventorybro.Ui;
import seedu.inventorybro.validator.ListCommandValidator;

//@@author adbsw

/**
 * Displays the current inventory items.
 * Can optionally filter by a specific category and/or sort the output.
 */
public class ListCommand implements Command {
    private static final Pattern LIST_PATTERN =
            Pattern.compile("^listItems(?:\\s+c/(.+?))?(?:\\s+(price|quantity)\\s+(high|low))?$");

    private final String input;

    /**
     * Creates a list command from the raw user input.
     *
     * @param input The full list command string.
     */
    public ListCommand(String input) {
        assert input != null : "Input line should not be null";
        this.input = input;
    }

    /**
     * Validates the list command input and prints all items in the inventory.
     * If a preferred order is specified, the items are sorted and printed in that order.
     *
     * @param items      The inventory item list to display.
     * @param categories The master list of categories.
     * @param ui         The ui object.
     */
    @Override
    public void execute(ItemList items, CategoryList categories, Ui ui) {
        new ListCommandValidator(input).validate(items, categories);

        if (items.isEmpty()) {
            ui.showMessage("Your inventory is empty.");
            return;
        }
        assert !items.isEmpty() : "List of items should not be empty";

        Matcher matcher = LIST_PATTERN.matcher(input);
        matcher.matches();
        String categoryInput = matcher.group(1);
        String field = matcher.group(2) == null ? "" : matcher.group(2);
        String order = matcher.group(3) == null ? "" : matcher.group(3);

        ArrayList<Item> displayList = new ArrayList<>();
        if (categoryInput != null) {
            String targetCat = categoryInput.trim();
            for (int i = 0; i < items.size(); i++) {
                if (items.getItem(i).getCategory().getName().equalsIgnoreCase(targetCat)) {
                    displayList.add(items.getItem(i));
                }
            }
            if (displayList.isEmpty()) {
                ui.showMessage("There are no items in the [" + targetCat.toUpperCase() + "] category.");
                return;
            }
        } else {
            for (int i = 0; i < items.size(); i++) {
                displayList.add(items.getItem(i));
            }
        }

        if (!field.isEmpty() && !order.isEmpty()) {
            displayList.sort((item1, item2) -> {
                int result = field.equals("price")
                        ? Double.compare(item1.getPrice(), item2.getPrice())
                        : Integer.compare(item1.getQuantity(), item2.getQuantity());
                return order.equals("high") ? -result : result;
            });
        }

        String catMsg = categoryInput != null ? (" in [" + categoryInput.trim().toUpperCase() + "]") : "";
        int listIndex = 0;

        ui.showMessage("Here are your current inventory items" + catMsg
                + (field.isEmpty() ? "" : " by ") + field
                + (order.isEmpty() ? "" : (order.equals("high") ? " in decreasing order" : " in increasing order"))
                + ":");

        for (int i = 0; i < displayList.size(); i++) {
            listIndex = i + 1;
            ui.showMessage(listIndex + ". " + displayList.get(i));
        }

        assert listIndex == displayList.size() : "List index should be equal to total number " +
                "of items in list after iterating through and printing all items";
    }
}
