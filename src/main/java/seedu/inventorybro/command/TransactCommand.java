package seedu.inventorybro.command;

import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;
import seedu.inventorybro.Ui;
import seedu.inventorybro.validator.TransactCommandValidator;

//@@author elliotjohnwu
/**
 * Adjusts an item's quantity by a signed transaction amount.
 */
public class TransactCommand implements Command {
    private final String input;

    /**
     * Creates a transact command from the raw user input.
     *
     * @param input The full transact command string.
     */
    public TransactCommand(String input) {
        assert input != null : "Input should not be null";
        this.input = input;
    }

    /**
     * Parses the transact command input and updates the targeted item's quantity.
     *
     * @param items The inventory item list to update.
     */
    @Override
    public void execute(ItemList items, Ui ui) {
        assert items != null : "ItemList should not be null";
        assert ui != null : "Ui should not be null";

        new TransactCommandValidator(input).validate(items);

        String[] words = input.split(" ", 2);
        String[] digits = words[1].split("q/", 2);
        int index = Integer.parseInt(digits[0].trim()) - 1;
        int change = Integer.parseInt(digits[1].trim());
        Item item = items.getItem(index);
        int newQuantity = item.getQuantity() + change;

        item.setQuantity(newQuantity);
        assert item.getQuantity() >= 0 : "Quantity became negative after transaction";

        ui.showMessage("Transaction recorded.\n" + item.getDescription() + " new quantity: " + newQuantity);
    }
}
