package seedu.inventorybro;

import java.io.IOException;

public class InventoryBro {

    /**
     * Main entry-point for the java.seedu.inventorybro.InventoryBro application.
     */
    public static void main(String[] args) {
        System.out.println("InventoryBRO");
        System.out.println("How can I help you today, bro?");
        String currentCommand = new String();
        ItemList items = new ItemList();
        while (true) {
            int ch;
            try {
                ch = System.in.read();
            } catch (IOException e) {
                System.out.println("Error when parsing characters!");
                break;
            }
            switch (ch) {
            case '\r':
                break;
            case -1: // EOF
            case '\n': {
                Parser.parse(currentCommand, items);
                System.out.println("Command received: " + currentCommand);
                currentCommand = new String();
                return;
            }
            default: {
                currentCommand += (char) ch;
                break;
            }
            }
        }
    }
}
