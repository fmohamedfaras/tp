package seedu.inventorybro;

import java.util.Scanner;

public class Ui {
    private final Scanner in;

    public Ui() {
        this.in = new Scanner(System.in);
    }

    public void showWelcome() {
        System.out.println("=====================================");
        System.out.println("  InventoryBRO");
        System.out.println("  How can I help you today, bro?");
        System.out.println("=====================================");
    }

    public void showLine() {
        System.out.println("--------------------------------------------------");
    }

    public String readCommand() {
        System.out.print("> ");
        return in.nextLine().trim();
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showError(String message) {
        System.out.println("ERROR: " + message);
    }
}
