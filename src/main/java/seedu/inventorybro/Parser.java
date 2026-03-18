package seedu.inventorybro;

import seedu.inventorybro.command.AddCommand;
import seedu.inventorybro.command.Command;
import seedu.inventorybro.command.DeleteCommand;
import seedu.inventorybro.command.EditCommand;
import seedu.inventorybro.command.ExitCommand;
import seedu.inventorybro.command.ListCommand;
import seedu.inventorybro.command.TransactCommand;

public class Parser {
    public static void parse(String line, ItemList items, Ui ui) {
        Command command = parseCommand(line);
        if (command == null) {
            ui.showError("Invalid command, please try add, delete, edit, transact, list, exit");
            return;
        }

        command.execute(items, ui);
    }


    private static Command parseCommand(String line) {
        String trimmedLine = line.trim();
        String lowerCaseLine = trimmedLine.toLowerCase();

        if (lowerCaseLine.startsWith("add")) {
            return new AddCommand(trimmedLine);
        }

        if (lowerCaseLine.startsWith("delete")) {
            return new DeleteCommand(trimmedLine);
        }

        if (lowerCaseLine.startsWith("edit")) {
            return new EditCommand(trimmedLine);
        }

        if (lowerCaseLine.startsWith("transact")) {
            return new TransactCommand(trimmedLine);
        }

        if (lowerCaseLine.startsWith("list")) {
            return new ListCommand(trimmedLine);
        }

        if (lowerCaseLine.startsWith("exit")) {
            return new ExitCommand();
        }

        return null;
    }
}

