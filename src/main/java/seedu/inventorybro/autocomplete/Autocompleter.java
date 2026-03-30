package seedu.inventorybro.autocomplete;

import java.util.List;

import seedu.inventorybro.CommandWord;

//@@author kenpegrasio
public class Autocompleter {
    private final Trie trie;

    public Autocompleter() {
        trie = buildTrie();
        assert trie != null : "Trie should not be null after construction";
    }

    public List<String> getMatches(String partial) {
        assert partial != null : "Partial input should not be null";
        List<String> matches = trie.findWithPrefix(partial);
        assert matches != null : "Match results should not be null";
        return matches;
    }

    private static Trie buildTrie() {
        Trie t = new Trie();
        assert t != null : "Trie instance should not be null";
        for (CommandWord cmd : CommandWord.values()) {
            t.insert(cmd.getWord());
        }
        return t;
    }
}
