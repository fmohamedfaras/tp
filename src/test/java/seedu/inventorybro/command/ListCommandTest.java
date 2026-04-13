package seedu.inventorybro.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.inventorybro.CategoryList;
import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;
import seedu.inventorybro.Ui;

//@@author adbsw
/**
 * Execution tests for {@link ListCommand}.
 */
class ListCommandTest {
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
    void execute_validUserInputListEmpty_success() {
        ItemList items = new ItemList();

        new ListCommand("listItems").execute(items, categories, ui);

        String expectedOutput = "Your inventory is empty." + System.lineSeparator();

        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    void execute_validUserInputListNotEmpty_success() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 50, 0.0, categories.getCategory("Others")));
        items.addItem(new Item("Banana", 40, 0.0, categories.getCategory("Others")));
        items.addItem(new Item("Orange", 30, 0.0, categories.getCategory("Others")));

        new ListCommand("listItems").execute(items, categories, ui);

        String expectedOutput = "Here are your current inventory items:" + System.lineSeparator()
                + "1. [OTHERS] Apple (Quantity: 50, Price: $0.00)" + System.lineSeparator()
                + "2. [OTHERS] Banana (Quantity: 40, Price: $0.00)" + System.lineSeparator()
                + "3. [OTHERS] Orange (Quantity: 30, Price: $0.00)" + System.lineSeparator();

        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    void execute_validUserInputQuantityHighOrder_success() {
        ItemList items = new ItemList();
        items.addItem(new Item("Item 1", 52, 0.0, categories.getCategory("Others")));
        items.addItem(new Item("Item 2", 17, 0.0, categories.getCategory("Others")));
        items.addItem(new Item("Item 3", 23, 0.0, categories.getCategory("Others")));
        items.addItem(new Item("Item 4", 165, 0.0, categories.getCategory("Others")));
        items.addItem(new Item("Item 5", 29, 0.0, categories.getCategory("Others")));
        items.addItem(new Item("Item 6", 9, 0.0, categories.getCategory("Others")));

        new ListCommand("listItems quantity high").execute(items, categories, ui);

        String expectedOutput = "Here are your current inventory items by quantity in decreasing order:"
                + System.lineSeparator()
                + "1. [OTHERS] Item 4 (Quantity: 165, Price: $0.00)" + System.lineSeparator()
                + "2. [OTHERS] Item 1 (Quantity: 52, Price: $0.00)" + System.lineSeparator()
                + "3. [OTHERS] Item 5 (Quantity: 29, Price: $0.00)" + System.lineSeparator()
                + "4. [OTHERS] Item 3 (Quantity: 23, Price: $0.00)" + System.lineSeparator()
                + "5. [OTHERS] Item 2 (Quantity: 17, Price: $0.00)" + System.lineSeparator()
                + "6. [OTHERS] Item 6 (Quantity: 9, Price: $0.00)" + System.lineSeparator();

        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    void execute_validUserInputQuantityLowOrder_success() {
        ItemList items = new ItemList();
        items.addItem(new Item("Item 1", 52, 0.0, categories.getCategory("Others")));
        items.addItem(new Item("Item 2", 17, 0.0, categories.getCategory("Others")));
        items.addItem(new Item("Item 3", 23, 0.0, categories.getCategory("Others")));
        items.addItem(new Item("Item 4", 165, 0.0, categories.getCategory("Others")));
        items.addItem(new Item("Item 5", 29, 0.0, categories.getCategory("Others")));
        items.addItem(new Item("Item 6", 9, 0.0, categories.getCategory("Others")));

        new ListCommand("listItems quantity low").execute(items, categories, ui);

        String expectedOutput = "Here are your current inventory items by quantity in increasing order:"
                + System.lineSeparator()
                + "1. [OTHERS] Item 6 (Quantity: 9, Price: $0.00)" + System.lineSeparator()
                + "2. [OTHERS] Item 2 (Quantity: 17, Price: $0.00)" + System.lineSeparator()
                + "3. [OTHERS] Item 3 (Quantity: 23, Price: $0.00)" + System.lineSeparator()
                + "4. [OTHERS] Item 5 (Quantity: 29, Price: $0.00)" + System.lineSeparator()
                + "5. [OTHERS] Item 1 (Quantity: 52, Price: $0.00)" + System.lineSeparator()
                + "6. [OTHERS] Item 4 (Quantity: 165, Price: $0.00)" + System.lineSeparator();

        assertEquals(expectedOutput, outContent.toString());
    }
}
