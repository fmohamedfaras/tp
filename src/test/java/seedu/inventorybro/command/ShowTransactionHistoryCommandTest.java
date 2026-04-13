package seedu.inventorybro.command;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.inventorybro.CategoryList;
import seedu.inventorybro.ItemList;
import seedu.inventorybro.Ui;
import seedu.inventorybro.storage.TransactionStorageHistoryStub;

//@@author elliotjohnwu
/**
 * Tests for {@link ShowTransactionHistoryCommand}
 */
class ShowTransactionHistoryCommandTest {

    private final ItemList items = new ItemList();
    private final CategoryList categories = new CategoryList();
    private final Ui ui = new Ui();

    @Test
    void execute_validCommand_emptyHistoryShowsEmptyMessage() {

        TransactionStorageHistoryStub stub =
                new TransactionStorageHistoryStub(new ArrayList<>());

        ShowTransactionHistoryCommand command =
                new ShowTransactionHistoryCommand("showHistory", stub);

        assertDoesNotThrow(() -> command.execute(items, categories, ui));
    }

    @Test
    void execute_validCommand_oneEntryDisplaysEntry() {

        ArrayList<String> entries = new ArrayList<>();
        entries.add("Coke Can | -5 | 2026-03-26 14:30");

        TransactionStorageHistoryStub stub =
                new TransactionStorageHistoryStub(entries);

        ShowTransactionHistoryCommand command =
                new ShowTransactionHistoryCommand("showHistory", stub);

        assertDoesNotThrow(() -> command.execute(items, categories, ui));
    }

    @Test
    void execute_validCommand_multipleEntriesDisplaysAll() {

        ArrayList<String> entries = new ArrayList<>();
        entries.add("Coke Can | -5 | 2026-03-26 14:30");
        entries.add("Sprite Bottle | 10 | 2026-03-26 14:31");
        entries.add("Fanta | -3 | 2026-03-26 14:32");

        TransactionStorageHistoryStub stub =
                new TransactionStorageHistoryStub(entries);

        ShowTransactionHistoryCommand command =
                new ShowTransactionHistoryCommand("showHistory", stub);

        assertDoesNotThrow(() -> command.execute(items, categories, ui));
    }

    @Test
    void execute_extraArguments_throwsException() {

        ShowTransactionHistoryCommand command =
                new ShowTransactionHistoryCommand(
                        "showHistory extra",
                        new TransactionStorageHistoryStub(new ArrayList<>())
                );

        assertThrows(IllegalArgumentException.class,
                () -> command.execute(items, categories, ui));
    }

    @Test
    void execute_wrongCommandWord_throwsException() {

        ShowTransactionHistoryCommand command =
                new ShowTransactionHistoryCommand(
                        "history",
                        new TransactionStorageHistoryStub(new ArrayList<>())
                );

        assertThrows(IllegalArgumentException.class,
                () -> command.execute(items, categories, ui));
    }

    @Test
    void execute_wrongCase_throwsException() {

        ShowTransactionHistoryCommand command =
                new ShowTransactionHistoryCommand(
                        "ShowHistory",
                        new TransactionStorageHistoryStub(new ArrayList<>())
                );

        assertThrows(IllegalArgumentException.class,
                () -> command.execute(items, categories, ui));
    }
}
