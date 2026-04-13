package seedu.inventorybro;

import java.util.Optional;

import seedu.inventorybro.command.AddCommand;
import seedu.inventorybro.command.AddCategoryCommand;
import seedu.inventorybro.command.DeleteCategoryCommand;
import seedu.inventorybro.command.ListCategoriesCommand;
import seedu.inventorybro.command.Command;
import seedu.inventorybro.command.DeleteCommand;
import seedu.inventorybro.command.EditDescriptionCommand;
import seedu.inventorybro.command.EditPriceCommand;
import seedu.inventorybro.command.EditQuantityCommand;
import seedu.inventorybro.command.EditCategoryCommand;
import seedu.inventorybro.command.ExitCommand;
import seedu.inventorybro.command.FilterCommand;
import seedu.inventorybro.command.FindCommand;
import seedu.inventorybro.command.HelpCommand;
import seedu.inventorybro.command.ListCommand;
import seedu.inventorybro.command.ShowTransactionHistoryCommand;
import seedu.inventorybro.command.TransactCommand;

public class Parser {
    private static final TypoDetector TYPO_DETECTOR = new TypoDetector();

    public static void parse(String line, ItemList items, CategoryList categories, Ui ui) {
        assert line != null : "Input line should not be null";
        assert items != null : "ItemList should not be null";
        assert categories != null : "CategoryList should not be null";
        assert ui != null : "Ui should not be null";

        Command command = parseCommand(line);
        if (command == null) {
            handleUnknownCommand(line, ui);
            return;
        }

        command.execute(items, categories, ui);
    }

    private static Command parseCommand(String line) {
        String trimmedLine = line.trim();
        String firstWord = extractFirstWord(trimmedLine).toLowerCase();

        switch (firstWord) {
        case "additem":
            return new AddCommand(trimmedLine);
        case "deleteitem":
            return new DeleteCommand(trimmedLine);
        case "editquantity":
            return new EditQuantityCommand(trimmedLine);
        case "editdescription":
            return new EditDescriptionCommand(trimmedLine);
        case "editprice":
            return new EditPriceCommand(trimmedLine);
        case "editcategory":
            return new EditCategoryCommand(trimmedLine);
        case "transact":
            return new TransactCommand(trimmedLine);
        case "filteritem":
            return new FilterCommand(trimmedLine);
        case "showhistory":
            return new ShowTransactionHistoryCommand(trimmedLine);
        case "listitems":
            return new ListCommand(trimmedLine);
        case "finditem":
            return new FindCommand(trimmedLine);
        case "addcategory":
            return new AddCategoryCommand(trimmedLine);
        case "listcategories":
            return new ListCategoriesCommand(trimmedLine);
        case "deletecategory":
            return new DeleteCategoryCommand(trimmedLine);
        case "help":
            return new HelpCommand(trimmedLine);
        case "exit":
            return new ExitCommand();
        default:
            return null;
        }
    }

    private static void handleUnknownCommand(String line, Ui ui) {
        String firstWord = extractFirstWord(line);
        Optional<String> suggestion = TYPO_DETECTOR.findClosestMatch(firstWord);
        if (suggestion.isPresent()) {
            ui.showMessage("Do you mean " + suggestion.get() + "?");
        } else {
            ui.showError("Invalid command, please try addCategory, deleteCategory, " +
                    "listCategories, addItem, deleteItem, editDescription, editPrice," +
                    " editQuantity, transact, showHistory, listItems, help, exit");
        }
    }

    private static String extractFirstWord(String line) {
        return line.trim().split("\\s+")[0];
    }
}
