package seedu.inventorybro.command;

import static java.util.Map.entry;

import java.util.Map;

import seedu.inventorybro.ItemList;
import seedu.inventorybro.CategoryList;
import seedu.inventorybro.Ui;
import seedu.inventorybro.validator.HelpCommandValidator;

//@@author adbsw

/**
 * Displays to the user the names and summaries of each command of the application or the detailed
 * instruction of the command specified in the input.
 */
public class HelpCommand implements Command {
    private static final String HELPSUMMARYMESSAGE = """
            Command names and their summaries:
            addItem:          Adds a new item of a given name, quantity, and price to the inventory list.
            deleteItem:       Deletes an item from the current inventory list.
            editDescription:  Edits the description of an existing item in the inventory based on the
                              item index. Existing description is updated to the provided input.
            editPrice:        Edits the price of an existing item in the inventory based on the item
                              index. Existing price is updated to the provided input.
            editQuantity:     Edits the quantity of an existing item in the inventory based on the item
                              index. Existing quantity is updated to the provided input.
            findItem:         Finds an item in the current inventory list based on the keyword typed
                              or displays message to the user that the inventory does not have item
                              that matches keyword.
            filterItem:       Displays only the items that match one or more field-based conditions.
                              Conditions can be combined using AND or OR operators.
            transact:         Updates stock quantities after a sale or restock.
            showHistory:      Displays a complete, numbered list of all past transactions
                              (sales and restocks) recorded by the application.
            listItems:        Displays all items currently in the inventory, or displays message
                              to the user that the inventory is empty if there are no items.
            help:             Displays summaries of each command to the user, or displays a detailed
                              instruction of a specified command.
            exit:             Closes the application. All saved data can be found in '/data/inventory.txt`
                              and '/data/transaction.txt'.

            For further details on a particular command, specify it using 'help [COMMAND_NAME]'.
            """;
    private static final String HELPADDITEMMESSAGE = """
            addItem:
            Adds a new item with a given name, quantity, and price to the current inventory list.
            - Name (d/): cannot be empty.
            - Quantity (q/): must be 0 or greater (negative values are not allowed).
            - Price (p/): must be at least 0.01 when rounded to 2 decimal places (e.g. 0.001 is rejected).

            Format: addItem d/NAME q/INITIAL_QUANTITY p/PRICE

            Example usage: addItem d/Apples q/10 p/1.50
            This adds an item named 'Apples' with quantity '10' and price '$1.50' to the inventory list.
            """;
    private static final String HELPDELETEITEMMESSAGE = """
            deleteItem:
            Deletes an item from the current inventory list based on the provided list index.
            Enter 'listItems' to view the list index of the item you wish to delete.

            Example usage: deleteItem 1
            This removes the item indexed at 1 in the inventory list.
            """;
    private static final String HELPEDITDESCRIPTIONMESSAGE = """
            editDescription:
            Edits the description of an existing item in the current inventory list based on
            the provided list index.
            
            Example usage: editDescription 1  d/Sprite Bottle
            This updates the description of the item indexed at 1 in the inventory list to
            'Sprite Bottle'.
            """;
    private static final String HELPEDITPRICEMESSAGE = """
            editPrice:
            Edits the price of an existing item in the current inventory list based on the
            provided list index.
            
            Example usage: editPrice 1 p/2
            This updates the price of the item indexed at 1 in the inventory list to '$2'.
            """;
    private static final String HELPEDITQUANTITYMESSAGE = """
            editQuantity:
            Edits the quantity of an existing item in the current inventory list based on the
            provided list index.
            
            Example usage: editQuantity 1 q/50
            This updates the quantity of the item indexed at 1 in the inventory list to '50'.
            """;
    private static final String HELPFINDITEMMESSAGE = """
            findItem:
            Finds and lists all items in the inventory whose description contains the keyword.
            The search is case-insensitive and matches partial words.

            Example usage: findItem app
            This displays all items containing 'app' in their name, such as 'Apples' or 'Pineapple'.
            """;
    private static final String HELPFILTERITEMMESSAGE = """
            filterItem:
            Displays only the items that match one or more field-based conditions.
            Conditions can be combined using AND (both must match) or OR (either must match).
            AND binds tighter than OR.

            Supported fields: description, quantity, price
            Supported operators: = < >

            - description values must be wrapped in single quotes (e.g. 'Coke Can').
            - quantity values must be whole numbers (decimals are not accepted).
            - price values accept up to 2 decimal places (e.g. 5 or 1.99). Values with more
              than 2 decimal places (e.g. 1.999) are rejected. Comparison is done on the price
              rounded to 2 decimal places.

            Format: filterItem FIELD OPERATOR VALUE [AND|OR FIELD OPERATOR VALUE ...]

            Example usages:-
            (Single condition): filterItem quantity > 10
            This displays all items with a quantity greater than 10.

            (AND condition): filterItem quantity > 10 AND quantity < 40
            This displays items with quantity between 11 and 39 (inclusive).

            (OR condition): filterItem description = 'Coke Can' OR description = 'Sprite Bottle'
            This displays items whose description matches either 'Coke Can' or 'Sprite Bottle'.

            (Price filter — integer): filterItem price < 5
            This displays all items with a price less than 5.

            (Price filter — decimal): filterItem price > 1.50
            This displays all items with a price greater than $1.50.
            """;
    private static final String HELPTRANSACTMESSAGE = """
            transact:
            Increases (restocking) or decreases (selling) quantity of the item based on the
            provided list index.

            To increase the quantity, simply key in the change in quantity.
            To decrease the quantity, precede the change in quantity with '-'.

            The existing quantity value will be updated by adding or subtracting the provided
            change in quantity. The quantity cannot be decreased below 0 or it will result in
            an error.

            Enter 'listItems' to view the list index of the item you wish to transact.

            Example usages:-
            (Restocking): transact 1 q/10
            This adds '10' to the existing quantity assigned to the item indexed at 1
            in the inventory list.

            (Selling): transact 1 q/-10
            This subtract off '10' from the existing quantity assigned to the item indexed at 1
            in the inventory list, provided that it does not result in a quantity lower than 0.
            Otherwise, an error will be shown.
            """;
    private static final String HELPSHOWHISTORYMESSAGE = """
            showHistory:
            Displays a complete, numbered list of all past transactions (sales and restocks)
            recorded by the application, or displays a message to the user that no transaction
            history is found if there are no recorded transactions.
            
            The display format of each transaction is as such:
            
            1. [Item description] | [Item quantity] | [YYYY-MM-DD] [HH-MM]
            
            where [YYYY-MM-DD] [HH-MM] are the transaction date and time.
            
            Example usage: showHistory
            """;
    private static final String HELPLISTITEMSMESSAGE = """
            listItems:
            Displays the list of items and their quantities in the current inventory, or displays
            a message to the user that inventory is empty if there are no items in the current
            inventory.

            Example usage: listItems
            """;
    private static final String HELPHELPMESSAGE = """
            help:
            Displays the command names of the application and their summaries, or a command can
            be specified to display a more detailed instruction of it. You can use 'help' to view
            the valid name of the command, and specify that command to view further details of
            it.

            Example usages:-
            (Without specifying a command): help
            This displays each command name and their summaries.

            (Specifying a command): help addItem
            This displays a more detailed instruction of how to use the command 'addItem'.
            """;
    private static final String HELPEXITMESSAGE = """
            exit:
            Closes the application. You can view your saved data in '/data/inventory.txt` and
            your transaction history in '/data/transaction.txt'. If the folder or files do not
            exist, InventoryBRO will automatically create it for you
            upon startup.

            Example usage: exit
            """;
    private static final Map<String, String> COMMANDMESSAGES = Map.ofEntries(
            entry("addItem", HELPADDITEMMESSAGE),
            entry("deleteItem", HELPDELETEITEMMESSAGE),
            entry("editDescription", HELPEDITDESCRIPTIONMESSAGE),
            entry("editPrice", HELPEDITPRICEMESSAGE),
            entry("editQuantity", HELPEDITQUANTITYMESSAGE),
            entry("findItem", HELPFINDITEMMESSAGE),
            entry("filterItem", HELPFILTERITEMMESSAGE),
            entry("transact", HELPTRANSACTMESSAGE),
            entry("showHistory", HELPSHOWHISTORYMESSAGE),
            entry("listItems", HELPLISTITEMSMESSAGE),
            entry("help", HELPHELPMESSAGE),
            entry("exit", HELPEXITMESSAGE)
    );
    private final String input;

    /**
     * Creates a help command from the raw user input.
     *
     * @param input The full help command string.
     */
    public HelpCommand(String input) {
        assert input != null : "Input line should not be null";
        this.input = input;
    }

    /**
     * Validates the help command input and prints the summaries of each command to the user, or
     * prints the detailed instruction of the command specified in the input.
     *
     * @param items The inventory item list to operate on.
     * @param ui    The ui object.
     */
    @Override
    public void execute(ItemList items, CategoryList categories, Ui ui) {
        new HelpCommandValidator(input).validate(items, categories);

        String[] words = input.split(" ");

        assert words.length <= 2 : "Input should have at most 2 words";

        String info = "";

        if (words.length == 1) {
            info = HELPSUMMARYMESSAGE;
        } else if (words.length == 2) {
            info = COMMANDMESSAGES.get(words[1]);
        }

        ui.showMessage(info);
    }
}
