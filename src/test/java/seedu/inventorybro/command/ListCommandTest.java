package seedu.inventorybro.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;
import seedu.inventorybro.Ui;

//@@author adbsw
/**
 * Execution tests for {@link ListCommand}.
 */
class ListCommandTest {
    private final Ui ui = new Ui();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream standardOut = System.out;

    /**
     * Redirects standard output stream to a new PrintStream with ByteArrayOutputStream
     * to inspect printed output.
     */
    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    /**
     * Restores standard output stream to original state after each tests to avoid affecting
     * other tests.
     */
    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
    }

    /**
     * Verifies that a non-empty list is printed with expected values.
     */
    @Test
    void execute_validUserInputListNotEmpty_success() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 50));
        items.addItem(new Item("Banana", 40));
        items.addItem(new Item("Orange", 30));

        new ListCommand("listItems").execute(items, ui);

        String expectedOutput = "Here are your current inventory items:" + System.lineSeparator()
                + "1. Apple (Quantity: 50)" + System.lineSeparator()
                + "2. Banana (Quantity: 40)" + System.lineSeparator()
                + "3. Orange (Quantity: 30)" + System.lineSeparator();

        assertEquals(expectedOutput, outContent.toString());
    }

    /**
     * Verifies that a message to the user that the inventory is empty is printed when the
     * list is empty.
     */
    @Test
    void execute_validUserInputListEmpty_success() {
        ItemList items = new ItemList();

        new ListCommand("listItems").execute(items, ui);

        String expectedOutput = "Your inventory is empty." + System.lineSeparator();

        assertEquals(expectedOutput, outContent.toString());
    }
}
