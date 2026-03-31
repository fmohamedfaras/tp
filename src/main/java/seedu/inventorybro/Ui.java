package seedu.inventorybro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import seedu.inventorybro.autocomplete.Autocompleter;

public class Ui {
    private static final String PROMPT = "> ";
    private final boolean isInteractive;
    private LineReader lineReader;
    private BufferedReader fallbackReader;
    private final Autocompleter autocompleter;

    public Ui() {
        autocompleter = new Autocompleter();
        isInteractive = System.console() != null;
        if (isInteractive) {
            lineReader = buildLineReader();
        } else {
            fallbackReader = new BufferedReader(new InputStreamReader(System.in));
        }
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
        if (isInteractive) {
            try {
                String line = lineReader.readLine(PROMPT);
                return line == null ? "" : line.trim();
            } catch (UserInterruptException | EndOfFileException e) {
                return "exit";
            }
        } else {
            try {
                String line = fallbackReader.readLine();
                return line == null ? "exit" : line.trim();
            } catch (IOException e) {
                return "exit";
            }
        }
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showError(String message) {
        System.out.println("ERROR: " + message);
    }

    private LineReader buildLineReader() {
        try {
            Terminal terminal = TerminalBuilder.builder().system(true).build();
            return LineReaderBuilder.builder()
                    .terminal(terminal)
                    .completer(this::complete)
                    .option(LineReader.Option.COMPLETE_IN_WORD, false)
                    .build();
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialise terminal", e);
        }
    }

    private void complete(org.jline.reader.LineReader reader, org.jline.reader.ParsedLine line,
            List<org.jline.reader.Candidate> candidates) {
        if (line.wordIndex() != 0) {
            return;
        }
        for (String keyword : autocompleter.getMatches(line.word())) {
            candidates.add(new org.jline.reader.Candidate(keyword));
        }
    }
}
