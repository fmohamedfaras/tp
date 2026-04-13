package seedu.inventorybro.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;
import seedu.inventorybro.Category;
import seedu.inventorybro.CategoryList;

//@@author elliotjohnwu
/**
 * Tests for ArrayStorage using ArrayStorageStub.
 * Covers saveArray, load, loadItemList, price handling, and corruption handling.
 */
class ArrayStorageTest {

    private static final String TEST_FILE = "./data/test_inventory.txt";
    private final CategoryList categories = new CategoryList();

    private final Category defaultCat = categories.getCategory("Others");
    /**
     * Verifies that saving and loading an ItemList with quantity only preserves all fields.
     */
    @Test
    void saveArray_andLoad_itemsPreservedWithDefaultPrice() {
        ArrayStorageStub storage = new ArrayStorageStub(TEST_FILE, categories);

        ItemList items = new ItemList();
        items.addItem(new Item("Coke Can", 50, defaultCat));
        items.addItem(new Item("Sprite Bottle", 30, defaultCat));

        storage.saveArray(items.getItems());
        ArrayList<Item> loaded = storage.load();

        assertEquals(2, loaded.size());
        assertEquals("Coke Can", loaded.get(0).getDescription());
        assertEquals(50, loaded.get(0).getQuantity());
        assertEquals(0.0, loaded.get(0).getPrice());
        assertEquals("Sprite Bottle", loaded.get(1).getDescription());
        assertEquals(30, loaded.get(1).getQuantity());

        storage.deleteTestFile();
    }

    @Test
    void saveArray_andLoad_pricePreserved() {
        ArrayStorageStub storage = new ArrayStorageStub(TEST_FILE, categories);

        ItemList items = new ItemList();
        Item item = new Item("Coke Can", 50, 1.50, defaultCat);
        items.addItem(item);

        storage.saveArray(items.getItems());
        ArrayList<Item> loaded = storage.load();

        assertEquals(1, loaded.size());
        assertEquals(1.50, loaded.get(0).getPrice(), 0.001);

        storage.deleteTestFile();
    }

    @Test
    void load_noFileExists_returnsEmptyList() {
        ArrayStorageStub storage = new ArrayStorageStub(TEST_FILE, categories);
        storage.deleteTestFile();

        ArrayList<Item> loaded = storage.load();

        assertTrue(loaded.isEmpty());
    }

    @Test
    void saveArray_emptyList_loadsEmpty() {
        ArrayStorageStub storage = new ArrayStorageStub(TEST_FILE, categories);

        storage.saveArray(new ArrayList<>());
        ArrayList<Item> loaded = storage.load();

        assertTrue(loaded.isEmpty());
        storage.deleteTestFile();
    }


    /**
     * Verifies that saveArray overwrites previous content entirely.
     */
    @Test
    void saveArray_overwritesPreviousContent() {
        ArrayStorageStub storage = new ArrayStorageStub(TEST_FILE, categories);
        ItemList first = new ItemList();
        first.addItem(new Item("Coke Can", 50, defaultCat));
        storage.saveArray(first.getItems());

        ItemList second = new ItemList();
        second.addItem(new Item("Fanta", 20, defaultCat));
        storage.saveArray(second.getItems());

        ArrayList<Item> loaded = storage.load();
        assertEquals(1, loaded.size());
        assertEquals("Fanta", loaded.get(0).getDescription());

        storage.deleteTestFile();
    }

    @Test
    void load_corruptedLine_skipsAndLoadsValid() throws IOException {
        ArrayStorageStub storage = new ArrayStorageStub(TEST_FILE, categories);

        File file = new File(TEST_FILE);
        file.getParentFile().mkdirs();
        try (FileWriter fw = new FileWriter(file)) {
            fw.write("50 | Coke Can | 1.50 | Others" + System.lineSeparator());
            fw.write("CORRUPTED LINE" + System.lineSeparator());
            fw.write("30 | Sprite Bottle | 2.00 | Others" + System.lineSeparator());
        }

        ArrayList<Item> loaded = storage.load();

        assertEquals(2, loaded.size());
        assertEquals("Coke Can", loaded.get(0).getDescription());
        assertEquals("Sprite Bottle", loaded.get(1).getDescription());

        storage.deleteTestFile();
    }

    @Test
    void load_nonNumericQuantity_lineSkipped() throws IOException {
        ArrayStorageStub storage = new ArrayStorageStub(TEST_FILE, categories);

        File file = new File(TEST_FILE);
        file.getParentFile().mkdirs();
        try (FileWriter fw = new FileWriter(file)) {
            fw.write("abc | Coke Can | 1.50 | Others" + System.lineSeparator());
        }

        ArrayList<Item> loaded = storage.load();

        assertTrue(loaded.isEmpty());
        storage.deleteTestFile();
    }

    @Test
    void load_nonNumericPrice_lineSkipped() throws IOException {
        ArrayStorageStub storage = new ArrayStorageStub(TEST_FILE, categories);

        File file = new File(TEST_FILE);
        file.getParentFile().mkdirs();
        try (FileWriter fw = new FileWriter(file)) {
            fw.write("50 | Coke Can | notaprice | Others" + System.lineSeparator());
        }

        ArrayList<Item> loaded = storage.load();

        assertTrue(loaded.isEmpty());
        storage.deleteTestFile();
    }


    @Test
    void loadItemList_returnsCorrectItemList() {
        ArrayStorageStub storage = new ArrayStorageStub(TEST_FILE, categories);

        ItemList items = new ItemList();
        Item item = new Item("Coke Can", 50, defaultCat);
        item.setPrice(1.50);
        items.addItem(item);
        storage.saveArray(items.getItems());

        ItemList loaded = storage.loadItemList();

        assertEquals(1, loaded.size());
        assertEquals("Coke Can", loaded.getItem(0).getDescription());
        assertEquals(50, loaded.getItem(0).getQuantity());
        assertEquals(1.50, loaded.getItem(0).getPrice(), 0.001);

        storage.deleteTestFile();
    }

    /**
     * Verifies that asserting a null description in Item throws AssertionError.
     * Tests the Item constructor assertion guard.
     */
    @Test
    void item_nullDescription_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> {
            new Item(null, 50, defaultCat);
        });
    }

    /**
     * Verifies that asserting a negative quantity in Item throws AssertionError.
     * Tests the Item constructor assertion guard.
     */
    @Test
    void item_negativeQuantity_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> {
            new Item("Coke Can", -1, defaultCat);
        });
    }

    /**
     * Verifies that asserting a negative price in Item throws AssertionError.
     * Tests the setPrice assertion guard.
     */
    @Test
    void item_negativePrice_throwsAssertionError() {
        Item item = new Item("Coke Can", 50, defaultCat);
        assertThrows(AssertionError.class, () -> {
            item.setPrice(-1.0);
        });
    }
    
}
