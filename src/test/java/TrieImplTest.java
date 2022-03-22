import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import trie.Trie;

import static org.junit.jupiter.api.Assertions.*;

public class TrieImplTest {
    private Trie trieImpl;

    @BeforeEach
    void setup() {
        trieImpl = Trie.trie();
    }

    @Test
    @DisplayName("Testing insertion and contains")
    void testInsertion() {
        assertTrue(trieImpl.containsWord(""));
        trieImpl.insertWord("");
        assertTrue(trieImpl.containsWord(""));

        assertFalse(trieImpl.containsWord("hello"));
        trieImpl.insertWord("hello");
        assertTrue(trieImpl.containsWord("hello"));

        assertFalse(trieImpl.containsWord("helloa"));
        trieImpl.insertWord("helloa");
        assertTrue(trieImpl.containsWord("helloa"));

        assertFalse(trieImpl.containsWord("h"));
        trieImpl.insertWord("h");
        assertTrue(trieImpl.containsWord("h"));

        assertTrue(trieImpl.containsWord(""));
    }

    @Test
    @DisplayName("Testing removal")
    void testRemoval() {
        assertFalse(trieImpl.removeWord(""));

        assertFalse(trieImpl.removeWord("hello"));
        trieImpl.insertWord("hello");
        assertTrue(trieImpl.removeWord("hello"));
        assertFalse(trieImpl.containsWord("hello"));

        assertFalse(trieImpl.removeWord("helloa"));
        trieImpl.insertWord("helloa");
        assertTrue(trieImpl.removeWord("helloa"));
        assertFalse(trieImpl.containsWord("helloa"));

        assertFalse(trieImpl.removeWord("h"));
        trieImpl.insertWord("h");
        assertTrue(trieImpl.removeWord("h"));
        assertFalse(trieImpl.containsWord("h"));

        assertFalse(trieImpl.removeWord(""));
    }
}
