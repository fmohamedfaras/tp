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

/**
 * Execution tests for {@link FilterCommand}.
 */
class FilterCommandTest {
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

    private ItemList buildItems() {
        ItemList items = new ItemList();
        Item apple = new Item("Apple", 10, 0.0, categories.getCategory("Others"));
        apple.setPrice(15);
        Item banana = new Item("Banana", 5, 0.0, categories.getCategory("Others"));
        banana.setPrice(8);
        Item cherry = new Item("Cherry", 20, 0.0, categories.getCategory("Others"));
        cherry.setPrice(3);
        items.addItem(apple);
        items.addItem(banana);
        items.addItem(cherry);
        return items;
    }

    @Test
    void execute_descriptionEquals_returnsExactMatch() {
        new FilterCommand("filterItem description = 'Apple'").execute(buildItems(), categories, ui);

        String expected = "Here are the filtered items:" + System.lineSeparator()
                + "1. [OTHERS] Apple (Quantity: 10, Price: $15.00)" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    @Test
    void execute_descriptionLessThan_returnsLexicographicallySmaller() {
        new FilterCommand("filterItem description < 'Banana'").execute(buildItems(), categories, ui);

        String expected = "Here are the filtered items:" + System.lineSeparator()
                + "1. [OTHERS] Apple (Quantity: 10, Price: $15.00)" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    @Test
    void execute_descriptionGreaterThan_returnsLexicographicallyLarger() {
        new FilterCommand("filterItem description > 'Banana'").execute(buildItems(), categories, ui);

        String expected = "Here are the filtered items:" + System.lineSeparator()
                + "1. [OTHERS] Cherry (Quantity: 20, Price: $3.00)" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    @Test
    void execute_descriptionMultiWordValue_returnsMatch() {
        ItemList items = new ItemList();
        items.addItem(new Item("Green Apple", 15, 0.0, categories.getCategory("Others")));
        items.addItem(new Item("Apple", 10, 0.0, categories.getCategory("Others")));

        new FilterCommand("filterItem description = 'Green Apple'").execute(items, categories, ui);

        String expected = "Here are the filtered items:" + System.lineSeparator()
                + "1. [OTHERS] Green Apple (Quantity: 15, Price: $0.00)" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    @Test
    void execute_quantityEquals_returnsExactMatch() {
        new FilterCommand("filterItem quantity = 10").execute(buildItems(), categories, ui);

        String expected = "Here are the filtered items:" + System.lineSeparator()
                + "1. [OTHERS] Apple (Quantity: 10, Price: $15.00)" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    @Test
    void execute_quantityLessThan_returnsItemsBelow() {
        new FilterCommand("filterItem quantity < 10").execute(buildItems(), categories, ui);

        String expected = "Here are the filtered items:" + System.lineSeparator()
                + "1. [OTHERS] Banana (Quantity: 5, Price: $8.00)" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    @Test
    void execute_quantityGreaterThan_returnsItemsAbove() {
        new FilterCommand("filterItem quantity > 10").execute(buildItems(), categories, ui);

        String expected = "Here are the filtered items:" + System.lineSeparator()
                + "1. [OTHERS] Cherry (Quantity: 20, Price: $3.00)" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    @Test
    void execute_noMatchingItems_showsNoMatchMessage() {
        new FilterCommand("filterItem description = 'Durian'").execute(buildItems(), categories, ui);

        String expected = "No items match the given filter." + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    @Test
    void execute_quantityGreaterThan_returnsMultipleMatches() {
        new FilterCommand("filterItem quantity > 4").execute(buildItems(), categories, ui);

        String expected = "Here are the filtered items:" + System.lineSeparator()
                + "1. [OTHERS] Apple (Quantity: 10, Price: $15.00)" + System.lineSeparator()
                + "2. [OTHERS] Banana (Quantity: 5, Price: $8.00)" + System.lineSeparator()
                + "3. [OTHERS] Cherry (Quantity: 20, Price: $3.00)" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    @Test
    void execute_andFilter_itemsMustMatchBoth() {
        new FilterCommand("filterItem quantity > 5 AND quantity < 15").execute(buildItems(), categories, ui);

        String expected = "Here are the filtered items:" + System.lineSeparator()
                + "1. [OTHERS] Apple (Quantity: 10, Price: $15.00)" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    @Test
    void execute_andFilterNoMatch_showsNoMatchMessage() {
        new FilterCommand("filterItem description = 'Apple' AND quantity > 50")
                .execute(buildItems(), categories, ui);

        String expected = "No items match the given filter." + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    @Test
    void execute_orFilter_itemsMatchEither() {
        new FilterCommand("filterItem description = 'Apple' OR description = 'Cherry'")
                .execute(buildItems(), categories, ui);

        String expected = "Here are the filtered items:" + System.lineSeparator()
                + "1. [OTHERS] Apple (Quantity: 10, Price: $15.00)" + System.lineSeparator()
                + "2. [OTHERS] Cherry (Quantity: 20, Price: $3.00)" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    @Test
    void execute_andThenOr_correctGrouping() {
        new FilterCommand("filterItem quantity > 5 AND quantity < 15 OR description = 'Cherry'")
                .execute(buildItems(), categories, ui);

        String expected = "Here are the filtered items:" + System.lineSeparator()
                + "1. [OTHERS] Apple (Quantity: 10, Price: $15.00)" + System.lineSeparator()
                + "2. [OTHERS] Cherry (Quantity: 20, Price: $3.00)" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    @Test
    void execute_orThenAnd_correctGrouping() {
        new FilterCommand("filterItem description = 'Banana' OR quantity > 5 AND quantity < 15")
                .execute(buildItems(), categories, ui);

        String expected = "Here are the filtered items:" + System.lineSeparator()
                + "1. [OTHERS] Apple (Quantity: 10, Price: $15.00)" + System.lineSeparator()
                + "2. [OTHERS] Banana (Quantity: 5, Price: $8.00)" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    @Test
    void execute_priceEquals_returnsExactMatch() {
        new FilterCommand("filterItem price = 15").execute(buildItems(), categories, ui);

        String expected = "Here are the filtered items:" + System.lineSeparator()
                + "1. [OTHERS] Apple (Quantity: 10, Price: $15.00)" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    @Test
    void execute_priceLessThan_returnsItemsBelow() {
        new FilterCommand("filterItem price < 8").execute(buildItems(), categories, ui);

        String expected = "Here are the filtered items:" + System.lineSeparator()
                + "1. [OTHERS] Cherry (Quantity: 20, Price: $3.00)" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    @Test
    void execute_priceGreaterThan_returnsItemsAbove() {
        new FilterCommand("filterItem price > 8").execute(buildItems(), categories, ui);

        String expected = "Here are the filtered items:" + System.lineSeparator()
                + "1. [OTHERS] Apple (Quantity: 10, Price: $15.00)" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    @Test
    void execute_priceFilter_noMatch() {
        new FilterCommand("filterItem price = 99").execute(buildItems(), categories, ui);

        String expected = "No items match the given filter." + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    @Test
    void execute_priceAndQuantityFilter_returnsCorrectItems() {
        new FilterCommand("filterItem price < 15 AND quantity > 3").execute(buildItems(), categories, ui);

        String expected = "Here are the filtered items:" + System.lineSeparator()
                + "1. [OTHERS] Banana (Quantity: 5, Price: $8.00)" + System.lineSeparator()
                + "2. [OTHERS] Cherry (Quantity: 20, Price: $3.00)" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    private ItemList buildDecimalPriceItems() {
        ItemList items = new ItemList();
        Item mango = new Item("Mango", 10, 0.0, categories.getCategory("Others"));
        mango.setPrice(1.99);
        Item papaya = new Item("Papaya", 15, 0.0, categories.getCategory("Others"));
        papaya.setPrice(8.50);
        Item lychee = new Item("Lychee", 5, 0.0, categories.getCategory("Others"));
        lychee.setPrice(3.25);
        items.addItem(mango);
        items.addItem(papaya);
        items.addItem(lychee);
        return items;
    }

    @Test
    void execute_priceDecimalEquals_returnsExactMatch() {
        new FilterCommand("filterItem price = 1.99").execute(buildDecimalPriceItems(), categories, ui);

        String expected = "Here are the filtered items:" + System.lineSeparator()
                + "1. [OTHERS] Mango (Quantity: 10, Price: $1.99)" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    @Test
    void execute_priceDecimalLessThan_returnsItemsBelow() {
        new FilterCommand("filterItem price < 8.50").execute(buildDecimalPriceItems(), categories, ui);

        String expected = "Here are the filtered items:" + System.lineSeparator()
                + "1. [OTHERS] Mango (Quantity: 10, Price: $1.99)" + System.lineSeparator()
                + "2. [OTHERS] Lychee (Quantity: 5, Price: $3.25)" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    @Test
    void execute_priceDecimalGreaterThan_returnsItemsAbove() {
        new FilterCommand("filterItem price > 3.25").execute(buildDecimalPriceItems(), categories, ui);

        String expected = "Here are the filtered items:" + System.lineSeparator()
                + "1. [OTHERS] Papaya (Quantity: 15, Price: $8.50)" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    @Test
    void execute_priceRoundingComparison_roundedItemMatchesFilter() {
        ItemList items = new ItemList();
        Item item = new Item("RoundItem", 1, 0.0, categories.getCategory("Others"));
        item.setPrice(1.999);
        items.addItem(item);

        new FilterCommand("filterItem price = 2").execute(items, categories, ui);

        String expected = "Here are the filtered items:" + System.lineSeparator()
                + "1. [OTHERS] RoundItem (Quantity: 1, Price: $2.00)" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    @Test
    void execute_priceIntegerAndDecimalEquivalent_sameResult() {
        new FilterCommand("filterItem price = 8.00").execute(buildItems(), categories, ui);

        String expected = "Here are the filtered items:" + System.lineSeparator()
                + "1. [OTHERS] Banana (Quantity: 5, Price: $8.00)" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }
}
