package seedu.inventorybro.command;

import seedu.inventorybro.ItemList;

/**
 * Terminates the application.
 */
public class ExitCommand implements Command {
    /**
     * Prints the farewell message and exits the program.
     *
     * @param items The inventory item list, unused for this command.
     */
    @Override
    public void execute(ItemList items) {
        System.out.println("Bye! See you next time.");
        System.exit(0);
    }
}
