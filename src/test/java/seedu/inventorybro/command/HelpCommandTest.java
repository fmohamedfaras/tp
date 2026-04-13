package seedu.inventorybro.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.inventorybro.CategoryList;
import seedu.inventorybro.ItemList;
import seedu.inventorybro.Ui;

/**
 * Execute tests for {@link HelpCommand}.
 */
class HelpCommandTest {
    private final Ui ui = new Ui();
    private final CategoryList categories = new CategoryList();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream standardOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void execute_validInputHelp_success() {
        new HelpCommand("help").execute(new ItemList(), categories, ui);

        String expectedOutput = """
            Command names and their summaries:
            addItem:          Adds a new item of a given name, quantity, and price to the inventory list.
            deleteItem:       Deletes an item from the current inventory list.
            editDescription:  Edits the description of an existing item in the inventory based on the
                              item index. Existing description is updated to the provided input.
            editPrice:        Edits the price of an existing item in the inventory based on the item
                              index. Existing price is updated to the provided input.
            editQuantity:     Edits the quantity of an existing item in the inventory based on the item
                              index. Existing quantity is updated to the provided input.
            findItem:         Finds an item in the current inventory list based on the keyword typed
                              or displays message to the user that the inventory does not have item
                              that matches keyword.
            filterItem:       Displays only the items that match one or more field-based conditions.
                              Conditions can be combined using AND or OR operators.
            transact:         Updates stock quantities after a sale or restock.
            showHistory:      Displays a complete, numbered list of all past transactions
                              (sales and restocks) recorded by the application.
            listItems:        Displays all items currently in the inventory, or displays message
                              to the user that the inventory is empty if there are no items.
            help:             Displays summaries of each command to the user, or displays a detailed
                              instruction of a specified command.
            exit:             Closes the application. All saved data can be found in '/data/inventory.txt`
                              and '/data/transaction.txt'.

            For further details on a particular command, specify it using 'help [COMMAND_NAME]'.
            """ + System.lineSeparator();

        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    void execute_helpValidCommandName_success() {
        new HelpCommand("help addItem").execute(new ItemList(), categories, ui);

        String expectedOutput = """
            addItem:
            Adds a new item with a given name, quantity, and price to the current inventory list.
            - Name (d/): cannot be empty.
            - Quantity (q/): must be 0 or greater (negative values are not allowed).
            - Price (p/): must be at least 0.01 when rounded to 2 decimal places (e.g. 0.001 is rejected).

            Format: addItem d/NAME q/INITIAL_QUANTITY p/PRICE

            Example usage: addItem d/Apples q/10 p/1.50
            This adds an item named 'Apples' with quantity '10' and price '$1.50' to the inventory list.
            """ + System.lineSeparator();

        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    void execute_helpInvalidCommandName_throwsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new HelpCommand("help invalidCommand").execute(new ItemList(), categories, ui)
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> new HelpCommand("help addItems").execute(new ItemList(), categories, ui)
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> new HelpCommand("help edit").execute(new ItemList(), categories, ui)
        );
    }

    @Test
    void execute_helpMoreThanOneArgument_throwsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new HelpCommand("help deleteItem transact").execute(new ItemList(), categories, ui)
        );
    }
}
