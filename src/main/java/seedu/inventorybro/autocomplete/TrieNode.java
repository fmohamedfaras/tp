package seedu.inventorybro.autocomplete;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

//@@author kenpegrasio
class TrieNode {
    private final Map<Character, TrieNode> children;
    private String keyword;

    TrieNode() {
        children = new HashMap<>();
        keyword = null;
    }

    TrieNode getOrCreateChild(char ch) {
        children.putIfAbsent(ch, new TrieNode());
        return children.get(ch);
    }

    TrieNode getChild(char ch) {
        return children.get(ch);
    }

    Collection<TrieNode> getChildNodes() {
        return children.values();
    }

    boolean isEndOfWord() {
        return keyword != null;
    }

    void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    String getKeyword() {
        return keyword;
    }
}
