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
     * Constructs a TransactCommand.
     *
     * @param input              The raw input string from the user.
     * @param transactionStorage The storage component responsible for saving the transaction log.
     */
    public TransactCommand(String input, TransactionStorage transactionStorage) {
        assert input != null : "Command input string should not be null";
        assert transactionStorage != null : "TransactionStorage should not be null";
        this.input = input;
        this.transactionStorage = transactionStorage;
    }

    /**
     * Executes the transaction command.
     * Updates the item's quantity in memory, generates a timestamp, saves the log to storage,
     * and displays a success message.
     *
     * @param items      The current list of items in the inventory.
     * @param categories The master list of categories.
     * @param ui         The UI object used to display messages to the user.
     */
    @Override
    public void execute(ItemList items, CategoryList categories, Ui ui) {
        assert items != null : "ItemList should not be null";
        assert ui != null : "Ui should not be null";

        logger.log(Level.FINE, "Executing transact command: {0}", input);

        new TransactCommandValidator(input).validate(items, categories);

        String[] words = input.split(" ", 2);
        String[] digits = words[1].split("q/", 2);

        int index = Integer.parseInt(digits[0].trim()) - 1;
        int qtyChange = Integer.parseInt(digits[1].trim());

        assert index >= 0 && index < items.size() : "Parsed index should be within ItemList bounds after validation";

        Item item = items.getItem(index);
        int newQuantity = item.getQuantity() + qtyChange;

        item.setQuantity(newQuantity);
        assert item.getQuantity() >= 0 : "Quantity became negative after transaction";

        transactionStorage.saveHistory(item.getDescription(), qtyChange);
        logger.log(Level.FINE, "Transaction complete. {0} new quantity: {1}",
                new Object[]{item.getDescription(), newQuantity});

        String action = qtyChange < 0 ? "Sale" : "Restock";
        ui.showMessage(action + " successful! The quantity of '" + item.getDescription() +
                "' is now " + item.getQuantity() + ".");
    }
}
