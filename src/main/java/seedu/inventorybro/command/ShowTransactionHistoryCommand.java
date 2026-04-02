package seedu.inventorybro.command;

import seedu.inventorybro.ItemList;
import seedu.inventorybro.Ui;
import seedu.inventorybro.storage.TransactionStorage;
import seedu.inventorybro.validator.ShowTransactionHistoryCommandValidator;

import java.util.ArrayList;

//@@author elliotjohnwu
/**
 * Displays all transaction history from the default storage file.
 */
public class ShowTransactionHistoryCommand implements Command {

    private final TransactionStorage transactionStorage;
    private final String input;

    /**
     * Creates a showHistory command from the raw user input.
     *
     * @param input The full showHistory command string.
     */
    public ShowTransactionHistoryCommand(String input) {
        assert input != null : "Input line should not be null";
        this.input = input;
        this.transactionStorage = new TransactionStorage();
    }

    /**
     * Creates a showHistory command with injected storage (for testing).
     *
     * @param input The full showHistory command string.
     * @param transactionStorage The storage to load history from.
     */
    public ShowTransactionHistoryCommand(String input, TransactionStorage transactionStorage) {
        assert input != null : "Input line should not be null";
        assert transactionStorage != null : "TransactionStorage should not be null";
        this.input = input;
        this.transactionStorage = transactionStorage;
    }

    @Override
    public void execute(ItemList items, Ui ui) {
        new ShowTransactionHistoryCommandValidator(input).validate(items);
        ArrayList<String> history = transactionStorage.load();

        if (history.isEmpty()) {
            ui.showMessage("No transaction history found.");
            return;
        }

        ui.showMessage("Transaction History:");

        for (int i = 0; i < history.size(); i++) {
            ui.showMessage((i + 1) + ". " + history.get(i));
        }
    }
}
