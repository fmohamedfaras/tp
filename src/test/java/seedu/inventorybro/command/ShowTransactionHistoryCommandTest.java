package seedu.inventorybro.command;

import org.junit.jupiter.api.Test;
import seedu.inventorybro.ItemList;
import seedu.inventorybro.Ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

//@@author elliotjohnwu
public class ShowTransactionHistoryCommandTest {

    @Test
    public void execute_validCommand_noCrash() {
        ShowTransactionHistoryCommand command =
                new ShowTransactionHistoryCommand("showHistory");

        Ui ui = new Ui();
        ItemList items = new ItemList();

        assertDoesNotThrow(() -> command.execute(items, ui));
    }

    @Test
    public void execute_invalidCommand_throwsException() {
        ShowTransactionHistoryCommand command =
                new ShowTransactionHistoryCommand("showHistory extra");

        Ui ui = new Ui();
        ItemList items = new ItemList();

        try {
            command.execute(items, ui);
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("showHistory");
        }
    }
}
