package seedu.inventorybro.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

//@@author elliotjohnwu
/**
 * Tests for TransactionStorage covering saveHistory, load, and corruption handling.
 */
class TransactionStorageTest {

    private static final String TEST_FILE = "./data/test_transactions.txt";

    private TransactionStorage createStorage() {
        return new TransactionStorageStub(TEST_FILE);
    }

    private void deleteTestFile() {
        new File(TEST_FILE).delete();
    }

    @Test
    void saveHistory_validEntry_appendedToFile() {
        TransactionStorage storage = createStorage();

        storage.saveHistory("Coke Can", -5);
        ArrayList<String> loaded = storage.load();

        assertEquals(1, loaded.size());
        assertTrue(loaded.get(0).startsWith("Coke Can | -5 |"));

        deleteTestFile();
    }

    @Test
    void saveHistory_multipleEntries_allAppended() {
        TransactionStorage storage = createStorage();

        storage.saveHistory("Coke Can", -5);
        storage.saveHistory("Sprite Bottle", 10);
        ArrayList<String> loaded = storage.load();

        assertEquals(2, loaded.size());
        assertTrue(loaded.get(0).startsWith("Coke Can | -5 |"));
        assertTrue(loaded.get(1).startsWith("Sprite Bottle | 10 |"));

        deleteTestFile();
    }

    @Test
    void load_noFileExists_returnsEmptyList() {
        TransactionStorage storage = createStorage();

        new File(TEST_FILE).delete();
        ArrayList<String> loaded = storage.load();

        assertTrue(loaded.isEmpty());
    }

    @Test
    void saveHistory_nullItem_noCrash() {
        TransactionStorage storage = createStorage();

        storage.saveHistory(null, 5);

        ArrayList<String> loaded = storage.load();
        assertEquals(1, loaded.size()); // or 0 depending on encode()
    }

    @Test
    void load_corruptedLine_skipsAndLoadsValid() throws IOException {
        TransactionStorage storage = createStorage();

        File file = new File(TEST_FILE);
        file.getParentFile().mkdirs();

        FileWriter fw = new FileWriter(file);
        fw.write("Coke Can | -5 | 2026-03-26 14:30" + System.lineSeparator());
        fw.write("CORRUPTED" + System.lineSeparator());
        fw.write("Sprite Bottle | 10 | 2026-03-26 14:31" + System.lineSeparator());
        fw.close();

        ArrayList<String> loaded = storage.load();

        assertEquals(2, loaded.size());
        assertTrue(loaded.get(0).startsWith("Coke Can"));
        assertTrue(loaded.get(1).startsWith("Sprite Bottle"));

        deleteTestFile();
    }

    @Test
    void load_nonNumericChange_lineSkipped() throws IOException {
        TransactionStorage storage = createStorage();

        File file = new File(TEST_FILE);
        file.getParentFile().mkdirs();

        FileWriter fw = new FileWriter(file);
        fw.write("Coke Can | abc | 2026-03-26 14:30" + System.lineSeparator());
        fw.close();

        ArrayList<String> loaded = storage.load();
        assertTrue(loaded.isEmpty());

        deleteTestFile();
    }

    @Test
    void saveHistory_invalidPath_throwsRuntimeException() {
        TransactionStorage storage =
                new TransactionStorageStub("\0_invalid_path/test.txt");

        assertThrows(RuntimeException.class, () ->
                storage.saveHistory("Coke", 5)
        );
    }

}

