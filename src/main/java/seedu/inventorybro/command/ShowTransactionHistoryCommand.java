package seedu.inventorybro.command;

import seedu.inventorybro.ItemList;
import seedu.inventorybro.Ui;
import seedu.inventorybro.storage.TransactionStorage;

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

    @Override
    public void execute(ItemList items, Ui ui) {
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
