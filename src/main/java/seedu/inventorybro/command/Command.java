package seedu.inventorybro.command;

import seedu.inventorybro.ItemList;
import seedu.inventorybro.Ui;

public interface Command {
    /**
     * Executes the command against the provided item list.
     *
     * @param items The inventory item list to operate on.
     */
    void execute(ItemList items, Ui ui);
}
