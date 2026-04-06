package seedu.inventorybro.command;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import seedu.inventorybro.ItemList;
import seedu.inventorybro.Ui;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ExitCommandTest {
    private final Ui ui = new Ui();

    @AfterEach
    void tearDown() {
        ExitCommand.resetExiter();
    }

    @Test
    void execute_callsSystemExit() {
        boolean[] exitCalled = {false};
        ExitCommand.setExiter(() -> exitCalled[0] = true);

        new ExitCommand().execute(new ItemList(), ui);

        assertTrue(exitCalled[0]);
    }
}
