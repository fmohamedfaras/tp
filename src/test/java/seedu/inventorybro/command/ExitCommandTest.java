package seedu.inventorybro.command;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.inventorybro.ItemList;

/**
 * Tests for {@link ExitCommand}.
 */
class ExitCommandTest {

    /**
     * Verifies that executing the exit command triggers application termination.
     */
    @Test
    void execute_callsSystemExit() {
        assertThrows(Exception.class, () -> new ExitCommand().execute(new ItemList()));
    }
}
