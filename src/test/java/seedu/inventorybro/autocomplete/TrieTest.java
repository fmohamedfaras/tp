package seedu.inventorybro.autocomplete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrieTest {
    private Trie trie;

    @BeforeEach
    void setUp() {
        trie = new Trie();
        trie.insert("apple");
        trie.insert("application");
        trie.insert("apply");
        trie.insert("banana");
    }

    @Test
    void findWithPrefix_exactWord_returnsThatWord() {
        List<String> result = trie.findWithPrefix("apple");
        assertEquals(1, result.size());
        assertEquals("apple", result.get(0));
    }

    @Test
    void findWithPrefix_sharedPrefix_returnsAllMatches() {
        List<String> result = trie.findWithPrefix("app");
        assertEquals(3, result.size());
        assertTrue(result.contains("apple"));
        assertTrue(result.contains("application"));
        assertTrue(result.contains("apply"));
    }

    @Test
    void findWithPrefix_noMatch_returnsEmpty() {
        List<String> result = trie.findWithPrefix("xyz");
        assertTrue(result.isEmpty());
    }

    @Test
    void findWithPrefix_emptyPrefix_returnsAllWords() {
        List<String> result = trie.findWithPrefix("");
        assertEquals(4, result.size());
    }

    @Test
    void findWithPrefix_caseInsensitive_matchesLowercasedPath() {
        trie.insert("Cherry");
        List<String> result = trie.findWithPrefix("cher");
        assertEquals(1, result.size());
        assertEquals("Cherry", result.get(0));
    }

    @Test
    void insert_preservesOriginalCase_returnedAsInserted() {
        trie.insert("TestWord");
        List<String> result = trie.findWithPrefix("test");
        assertEquals(1, result.size());
        assertEquals("TestWord", result.get(0));
    }
}
