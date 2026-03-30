package seedu.inventorybro.autocomplete;

import java.util.List;

import seedu.inventorybro.CommandWord;

public class Autocompleter {
    private final Trie trie;

    public Autocompleter() {
        trie = buildTrie();
    }

    public List<String> getMatches(String partial) {
        return trie.findWithPrefix(partial);
    }

    private static Trie buildTrie() {
        Trie t = new Trie();
        for (CommandWord cmd : CommandWord.values()) {
            t.insert(cmd.getWord());
        }
        return t;
    }
}
