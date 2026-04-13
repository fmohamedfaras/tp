package seedu.inventorybro.command;

import seedu.inventorybro.ItemList;
import seedu.inventorybro.CategoryList;
import seedu.inventorybro.Ui;
import seedu.inventorybro.storage.TransactionStorage;
import seedu.inventorybro.validator.ShowTransactionHistoryCommandValidator;

import java.util.ArrayList;

//@@author elliotjohnwu
/**
 * Retrieves and displays the transaction history in a readable, point-form layout.
 */
public class ShowTransactionHistoryCommand implements Command {

    private final TransactionStorage transactionStorage;
    private final String input;

    /**
     * Creates a showHistory command with injected storage (for testing).
     *
     * @param input              The full showHistory command string.
     * @param transactionStorage The storage to load history from.
     */
    public ShowTransactionHistoryCommand(String input, TransactionStorage transactionStorage) {
        assert input != null : "Input line should not be null";
        assert transactionStorage != null : "TransactionStorage should not be null";
        this.input = input;
        this.transactionStorage = transactionStorage;
    }

    /**
     * Executes the command to load and display the transaction history.
     * Parses raw storage strings into user-friendly "Sale" or "Restock" receipt cards.
     *
     * @param items      The current list of items in the inventory.
     * @param categories The master list of categories.
     * @param ui         The UI object used to display messages to the user.
     */
    @Override
    public void execute(ItemList items, CategoryList categories, Ui ui) {
        new ShowTransactionHistoryCommandValidator(input).validate(items, categories);
        ArrayList<String> history = transactionStorage.load();

        assert history != null : "Loaded transaction history should be an initialized list, not null";

        if (history.isEmpty()) {
            ui.showMessage("Transaction history is currently empty.");
            return;
        }

        ui.showMessage("Here is your transaction history:");
        //@@author fmohamedfaras
        for (int i = 0; i < history.size(); i++) {
            String entry = history.get(i);

            String[] parts = entry.split(" \\| ");

            if (parts.length >= 3) {
                try {
                    String desc = parts[0].trim();
                    int qtyChange = Integer.parseInt(parts[1].trim());
                    String date = parts[2].trim();

                    String type = qtyChange < 0 ? "SALE" : "RESTOCK";
                    int absQty = Math.abs(qtyChange);

                    ui.showMessage("Transaction " + (i + 1));
                    ui.showMessage("  Date & Time : " + date);
                    ui.showMessage("  Type        : " + type);
                    ui.showMessage("  Qty         : " + absQty);
                    ui.showMessage("  Description : " + desc);

                } catch (NumberFormatException e) {
                    ui.showMessage((i + 1) + ". " + entry);
                }
            } else {
                ui.showMessage((i + 1) + ". " + entry);
            }

            if (i < history.size() - 1) {
                ui.showMessage("----------------------------------------");
            }
        }
    }
}
