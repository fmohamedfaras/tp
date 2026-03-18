package seedu.inventorybro.command;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.inventorybro.ItemList;
import seedu.inventorybro.Ui;

/**
 * Tests for {@link ExitCommand}.
 */
class ExitCommandTest {
    private final Ui ui = new Ui();
    /**
     * Verifies that executing the exit command triggers application termination.
     */
    @Test
    void execute_callsSystemExit() {
        assertThrows(Exception.class, () -> new ExitCommand().execute(new ItemList(), ui));
    }
}
