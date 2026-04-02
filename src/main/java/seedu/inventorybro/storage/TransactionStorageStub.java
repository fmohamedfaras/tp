package seedu.inventorybro.storage;

import java.util.ArrayList;

//@@author elliotjohnwu
/**
 * A stub for TransactionStorage used in unit testing.
 * Returns predefined transaction history instead of reading from file.
 */
public class TransactionStorageStub extends TransactionStorage {

    private final ArrayList<String> entries;

    /**
     * Creates a stub with predefined transaction entries.
     *
     * @param entries The entries that load() should return.
     */
    public TransactionStorageStub(ArrayList<String> entries) {
        this.entries = entries;
    }

    /**
     * Returns the predefined entries instead of reading from file.
     *
     * @return List of transaction history entries.
     */
    @Override
    public ArrayList<String> load() {
        return entries;
    }
}
