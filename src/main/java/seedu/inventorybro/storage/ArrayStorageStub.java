package seedu.inventorybro.storage;

import seedu.inventorybro.Item;
import seedu.inventorybro.CategoryList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

//@@author elliotjohnwu
/**
 * Test stub for ArrayStorage that redirects file operations
 * to a specified test file path instead of the real inventory file.
 */
public class ArrayStorageStub extends ArrayStorage {

    private final String testFilePath;

    /**
     * Creates a stub storage that uses a custom test file path and a category list.
     *
     * @param testFilePath Path to the test file.
     * @param categories   The master CategoryList to link loaded items to.
     */
    public ArrayStorageStub(String testFilePath, CategoryList categories) {
        super(categories); // Passes the category list to the parent ArrayStorage
        this.testFilePath = testFilePath;
    }

    /**
     * Saves items to the test file, overwriting existing content.
     *
     * @param items The list of items to save.
     */
    @Override
    public void saveArray(ArrayList<Item> items) {
        try {
            File file = new File(testFilePath);
            file.getParentFile().mkdirs();
            FileWriter fw = new FileWriter(file);
            for (Item item : items) {
                fw.write(item.toSaveFormat() + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException("Test stub save failed: " + e.getMessage());
        }
    }

    /**
     * Loads items from the test file.
     * Returns an empty list if the test file does not exist.
     * Skips corrupted lines using the parent decode logic.
     *
     * @return The loaded list of items.
     */
    @Override
    public ArrayList<Item> load() {
        ArrayList<Item> items = new ArrayList<>();
        File file = new File(testFilePath);
        if (!file.exists()) {
            return items;
        }
        try (java.util.Scanner s = new java.util.Scanner(file)) {
            int lineNumber = 0;
            while (s.hasNextLine()) {
                lineNumber++;
                String line = s.nextLine().trim();
                if (!line.isEmpty()) {
                    Item decoded = decode(line, lineNumber);
                    if (decoded != null) {
                        items.add(decoded);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Test stub load failed: " + e.getMessage());
        }
        return items;
    }

    /**
     * Deletes the test file to clean up after a test.
     */
    public void deleteTestFile() {
        new File(testFilePath).delete();
    }
}
