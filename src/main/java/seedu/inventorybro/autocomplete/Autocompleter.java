package seedu.inventorybro.autocomplete;

import java.util.List;

public class Autocompleter {
    private static final List<String> KEYWORDS = List.of(
            "addItem", "deleteItem", "editItem", "transact", "listItems", "help", "exit"
    );
    private final Trie trie;

    public Autocompleter() {
        trie = buildTrie();
    }

    public List<String> getMatches(String partial) {
        return trie.findWithPrefix(partial);
    }

    private static Trie buildTrie() {
        Trie t = new Trie();
        for (String keyword : KEYWORDS) {
            t.insert(keyword);
        }
        return t;
    }
}
