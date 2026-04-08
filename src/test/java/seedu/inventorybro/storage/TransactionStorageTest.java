package seedu.inventorybro.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

//@@author elliotjohnwu
/**
 * Tests for TransactionStorage using TransactionStorageStub.
 * Covers saveHistory, load, and corruption handling.
 * Each test manages its own file cleanup inline.
 */
class TransactionStorageTest {

    private static final String TEST_FILE = "./data/test_transactions.txt";

    /**
     * Creates a TransactionStorage stub that redirects file operations to the test file.
     *
     * @return A test-safe TransactionStorage stub.
     */
    private TransactionStorage createStub() {
        return new TransactionStorage() {
            @Override
            public void saveHistory(String entry) throws IOException {
                File file = new File(TEST_FILE);
                file.getParentFile().mkdirs();
                FileWriter fw = new FileWriter(file, true);
                fw.write(entry + System.lineSeparator());
                fw.close();
            }

            @Override
            public ArrayList<String> load() {
                ArrayList<String> entries = new ArrayList<>();
                File file = new File(TEST_FILE);
                if (!file.exists()) {
                    return entries;
                }
                try (java.util.Scanner s = new java.util.Scanner(file)) {
                    int lineNumber = 0;
                    while (s.hasNextLine()) {
                        lineNumber++;
                        String line = s.nextLine().trim();
                        if (!line.isEmpty()) {
                            String decoded = decode(line, lineNumber);
                            if (decoded != null) {
                                entries.add(decoded);
                            }
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return entries;
            }
        };
    }

    private void deleteTestFile() {
        new File(TEST_FILE).delete();
    }

    // ========================= happy path =========================

    /**
     * Verifies that a single saveHistory call appends the entry with correct format.
     */
    @Test
    void saveHistory_singleEntry_appendedWithCorrectFormat() {
        TransactionStorage storage = createStub();
        storage.saveHistory("Coke Can", -5);

        ArrayList<String> loaded = storage.load();

        assertEquals(1, loaded.size());
        assertTrue(loaded.get(0).startsWith("Coke Can | -5 |"));

        deleteTestFile();
    }

    /**
     * Verifies that multiple saveHistory calls append in order.
     */
    @Test
    void saveHistory_multipleEntries_allAppendedInOrder() {
        TransactionStorage storage = createStub();
        storage.saveHistory("Coke Can", -5);
        storage.saveHistory("Sprite Bottle", 10);

        ArrayList<String> loaded = storage.load();

        assertEquals(2, loaded.size());
        assertTrue(loaded.get(0).startsWith("Coke Can | -5 |"));
        assertTrue(loaded.get(1).startsWith("Sprite Bottle | 10 |"));

        deleteTestFile();
    }

    // ========================= boundary / empty cases =========================

    /**
     * Verifies that loading from a non-existent file returns an empty list.
     */
    @Test
    void load_noFileExists_returnsEmptyList() {
        TransactionStorage storage = createStub();
        deleteTestFile();

        ArrayList<String> loaded = storage.load();

        assertTrue(loaded.isEmpty());
    }

    // ========================= corruption handling =========================

    /**
     * Verifies that a corrupted line is skipped and valid lines still load.
     */
    @Test
    void load_oneCorruptedLine_skipsAndLoadsValid() throws IOException {
        TransactionStorage storage = createStub();
        File file = new File(TEST_FILE);
        file.getParentFile().mkdirs();
        try (FileWriter fw = new FileWriter(file)) {
            fw.write("Coke Can | -5 | 2026-03-26 14:30" + System.lineSeparator());
            fw.write("CORRUPTED" + System.lineSeparator());
            fw.write("Sprite Bottle | 10 | 2026-03-26 14:31" + System.lineSeparator());
        }

        ArrayList<String> loaded = storage.load();

        assertEquals(2, loaded.size());
        assertTrue(loaded.get(0).startsWith("Coke Can"));
        assertTrue(loaded.get(1).startsWith("Sprite Bottle"));

        deleteTestFile();
    }

    /**
     * Verifies that a line with non-numeric change value is skipped.
     * Representative EP test for field-type corruption.
     */
    @Test
    void load_nonNumericChange_lineSkipped() throws IOException {
        TransactionStorage storage = createStub();
        File file = new File(TEST_FILE);
        file.getParentFile().mkdirs();
        try (FileWriter fw = new FileWriter(file)) {
            fw.write("Coke Can | abc | 2026-03-26 14:30" + System.lineSeparator());
        }

        ArrayList<String> loaded = storage.load();

        assertTrue(loaded.isEmpty());
        deleteTestFile();
    }

    /**
     * Verifies that a line missing the timestamp field is skipped.
     * Tests the parts.length less than 3 guard in decode().
     */
    @Test
    void load_missingTimestamp_lineSkipped() throws IOException {
        TransactionStorage storage = createStub();
        File file = new File(TEST_FILE);
        file.getParentFile().mkdirs();
        try (FileWriter fw = new FileWriter(file)) {
            fw.write("Coke Can | -5" + System.lineSeparator());
        }

        ArrayList<String> loaded = storage.load();

        assertTrue(loaded.isEmpty());
        deleteTestFile();
    }

    // ========================= assertThrows cases =========================

    /**
     * Verifies that saveHistory with a null item name throws AssertionError.
     * Tests the assertion guard in saveHistory(String, int).
     */
    @Test
    void saveHistory_nullItemName_throwsAssertionError() {
        TransactionStorage storage = new TransactionStorage();
        assertThrows(AssertionError.class, () -> storage.saveHistory(null, -5));
    }

    /**
     * Verifies that saveHistory with an empty item name throws AssertionError.
     * Separate EP from null — both are invalid but trigger different assertion messages.
     */
    @Test
    void saveHistory_emptyItemName_throwsAssertionError() {
        TransactionStorage storage = new TransactionStorage();
        assertThrows(AssertionError.class, () -> storage.saveHistory("", -5));
    }

    /**
     * Verifies that TransactionStorageStub load() returns predefined entries unchanged.
     * Tests the stub itself works correctly for use in command tests.
     */
    @Test
    void transactionStorageStub_load_returnsPredefinedEntries() {
        ArrayList<String> predefined = new ArrayList<>();
        predefined.add("Coke Can | -5 | 2026-03-26 14:30");
        predefined.add("Sprite Bottle | 10 | 2026-03-26 14:31");

        TransactionStorageStub stub = new TransactionStorageStub(predefined);
        ArrayList<String> loaded = stub.load();

        assertEquals(2, loaded.size());
        assertEquals("Coke Can | -5 | 2026-03-26 14:30", loaded.get(0));
        assertEquals("Sprite Bottle | 10 | 2026-03-26 14:31", loaded.get(1));
    }
}
