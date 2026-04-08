package seedu.inventorybro.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

//@@author elliotjohnwu
/**
 * Stub implementation of TransactionStorage for testing.
 * Uses a custom file path to avoid interfering with real data.
 */
public class TransactionStorageStub extends TransactionStorage {

    private final String testFilePath;

    public TransactionStorageStub(String testFilePath) {
        super(); // call parent constructor if needed
        this.testFilePath = testFilePath;
    }

    @Override
    public void saveHistory(String itemName, int change) {
        String entry = itemName + " | " + change + " | TEST_TIMESTAMP";
        try {
            File file = new File(testFilePath);
            file.getParentFile().mkdirs();
            FileWriter fw = new FileWriter(file, true);
            fw.write(entry + System.lineSeparator());
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<String> load() {
        ArrayList<String> entries = new ArrayList<>();
        File file = new File(testFilePath);

        if (!file.exists()) {
            return entries;
        }

        try (Scanner s = new Scanner(file)) {
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
}
