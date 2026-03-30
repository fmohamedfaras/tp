package seedu.inventorybro.autocomplete;

import java.util.ArrayList;
import java.util.List;

class Trie {
    private final TrieNode root;

    Trie() {
        root = new TrieNode();
    }

    void insert(String word) {
        TrieNode current = root;
        for (char ch : word.toLowerCase().toCharArray()) {
            current = current.getOrCreateChild(ch);
        }
        current.setKeyword(word);
    }

    List<String> findWithPrefix(String prefix) {
        TrieNode prefixNode = navigateTo(prefix.toLowerCase());
        if (prefixNode == null) {
            return new ArrayList<>();
        }
        return collectWords(prefixNode);
    }

    private TrieNode navigateTo(String prefix) {
        TrieNode current = root;
        for (char ch : prefix.toCharArray()) {
            current = current.getChild(ch);
            if (current == null) {
                return null;
            }
        }
        return current;
    }

    private List<String> collectWords(TrieNode node) {
        List<String> results = new ArrayList<>();
        collectWordsRecursively(node, results);
        return results;
    }

    private void collectWordsRecursively(TrieNode node, List<String> results) {
        if (node.isEndOfWord()) {
            results.add(node.getKeyword());
        }
        for (TrieNode child : node.getChildNodes()) {
            collectWordsRecursively(child, results);
        }
    }
}
