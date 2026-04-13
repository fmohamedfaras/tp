package seedu.inventorybro.command;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;
import seedu.inventorybro.CategoryList;
import seedu.inventorybro.Ui;
import seedu.inventorybro.validator.TransactCommandValidator;
import seedu.inventorybro.storage.TransactionStorage;

//@@author elliotjohnwu
/**
 * Adjusts an item's quantity by a signed transaction amount.
 */
public class TransactCommand implements Command {

    private static final Logger logger = Logger.getLogger(TransactCommand.class.getName());
    private final String input;
    private final TransactionStorage transactionStorage;



    /**
     * Creates a transact command from the raw user input.
     *
     * @param input The full transact command string.
     */
    public TransactCommand(String input) {
        assert input != null : "Input should not be null";
        this.input = input;
        transactionStorage = new TransactionStorage();
    }

    /**
     * Parses the transact command input and updates the targeted item's quantity.
     *
     * @param items The inventory item list to update.
     */
    @Override
    public void execute(ItemList items, CategoryList categories, Ui ui) {
        assert items != null : "ItemList should not be null";
        assert ui != null : "Ui should not be null";

        logger.log(Level.INFO, "Executing transact command: {0}", input);

        new TransactCommandValidator(input).validate(items, categories);

        String[] words = input.split(" ", 2);
        String[] digits = words[1].split("q/", 2);
        int index = Integer.parseInt(digits[0].trim()) - 1;
        int change = Integer.parseInt(digits[1].trim());
        Item item = items.getItem(index);
        int newQuantity = item.getQuantity() + change;

        item.setQuantity(newQuantity);
        assert item.getQuantity() >= 0 : "Quantity became negative after transaction";

        transactionStorage.saveHistory(item.getDescription(), change);
        logger.log(Level.INFO, "Transaction complete. {0} new quantity: {1}",
                new Object[]{item.getDescription(), newQuantity});
        ui.showMessage("Transaction recorded.\n" + item.getDescription() + " new quantity: " + newQuantity);
    }
}
