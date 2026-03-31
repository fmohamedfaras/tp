package seedu.inventorybro.autocomplete;

import java.util.List;

import seedu.inventorybro.CommandWord;

//@@author kenpegrasio
/**
 * Provides command-word autocompletion by matching a partial input string
 * against the set of known {@link CommandWord} values stored in a {@link Trie}.
 */
public class Autocompleter {
    private final Trie trie;

    /**
     * Constructs an {@code Autocompleter} and pre-populates its internal {@link Trie}
     * with all known command words.
     */
    public Autocompleter() {
        trie = buildTrie();
        assert trie != null : "Trie should not be null after construction";
    }

    /**
     * Returns all known command words that start with the given partial string.
     * The match is case-insensitive; the original casing of the stored words is preserved.
     *
     * @param partial The prefix to match against; must not be {@code null}.
     * @return A list of matching command words; empty if no command starts with {@code partial}.
     */
    public List<String> getMatches(String partial) {
        assert partial != null : "Partial input should not be null";
        List<String> matches = trie.findWithPrefix(partial);
        assert matches != null : "Match results should not be null";
        return matches;
    }

    /**
     * Builds and returns a {@link Trie} pre-loaded with every {@link CommandWord} value.
     *
     * @return A fully populated {@link Trie} containing all command words.
     */
    private static Trie buildTrie() {
        Trie t = new Trie();
        assert t != null : "Trie instance should not be null";
        for (CommandWord cmd : CommandWord.values()) {
            t.insert(cmd.getWord());
        }
        return t;
    }
}
