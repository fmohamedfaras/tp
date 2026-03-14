package seedu.inventorybro;

public class Parser {
    public static void parse(String line, ItemList items) {
        if (line.toLowerCase().startsWith("add")) {
            parseAdd(line, items);
            return;
        }

        if (line.toLowerCase().startsWith("delete")) {
            parseDelete(line, items);
            return;
        }

        if (line.toLowerCase().startsWith("edit")) {
            parseEdit(line, items);
            return;
        }

        if (line.toLowerCase().startsWith("transact")) {
            transact(line, items);
            return;
        }

        if (line.equalsIgnoreCase("list")) {
            parseList(items);
            return;
        }

        if (line.toLowerCase().startsWith("exit")) {
            exit();
            return;
        }

        System.out.println("Invalid command, please try add, delete, edit, transact, list, exit");

    }

    private static void transact(String text, ItemList items) {
        try {
            String[] words = text.split(" ", 2);
            if (words[1].isEmpty() || !words[0].equalsIgnoreCase("transact")) {
                throw new IllegalArgumentException("Invalid transact format. " +
                        "Use: transact INDEX q/CHANGE_IN_QUANTITY");
            }

            String[] digits = words[1].split("q/", 2);
            if (digits.length < 2) {
                throw new IllegalArgumentException("Invalid transact format. " +
                        "Use: transact INDEX q/CHANGE_IN_QUANTITY");
            }

            checkIfDigit(digits[0].trim());
            int index = Integer.parseInt(digits[0].trim()) - 1;
            if (index < 0 || index >= items.size()) {
                throw new IllegalArgumentException("Invalid index for transact.");
            }

            checkIfSignedDigit(digits[1].trim());
            int change = Integer.parseInt(digits[1].trim());
            Item item = items.getItem(index);
            int newQuantity = item.getQuantity() + change;
            if (newQuantity < 0) {
                throw new IllegalArgumentException("Transaction failed. Quantity cannot go below 0.");
            }

            item.setQuantity(newQuantity);
            System.out.println("Transaction recorded.");
            System.out.println(item.getDescription() + " new quantity: " + newQuantity);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void checkIfDigit(String digits) {
        for (char digit : digits.toCharArray()) {
            if (!Character.isDigit(digit)) {
                throw new IllegalArgumentException("Invalid transact, Index or Quantity Must be a digit");
            }
        }
    }

    private static void checkIfSignedDigit(String digits) {
        if (digits.isEmpty()) {
            throw new IllegalArgumentException("Invalid transact. Quantity cannot be empty.");
        }

        int start = 0;
        if (digits.charAt(0) == '-') {
            start = 1;
        }

        if (start == 1 && digits.length() == 1) {
            throw new IllegalArgumentException("Invalid transact. Quantity cannot be just a minus sign.");
        }

        checkIfDigit(digits.substring(start));
    }

    private static void parseList(ItemList items) {
        if (items.isEmpty()) {
            System.out.println("Your inventory is empty.");
        }

        System.out.println("Here are your current inventory items:");
        for (int i = 0; i < items.size(); i++) {
            int listIndex = i + 1;
            System.out.println((listIndex) + ". " + items.getItem(i).toString());
        }
    }

    private static void parseDelete(String text, ItemList items) {
    }

    private static void parseAdd(String text, ItemList items) {
    }

    private static void parseEdit(String text, ItemList items) {
    }

    private static void exit() {
    }
}

