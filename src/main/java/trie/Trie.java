package trie;

import java.util.ArrayList;
import java.util.Optional;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicBoolean;

public interface Trie extends Comparable<TrieImpl> {
    static Trie trie() {
        return new TrieImpl(new AtomicBoolean(), null, Optional.empty(), new TreeSet<>());
    }

    static Trie concurrentTrie() {
        return new ConcurrentTrie(trie());
    }

    void insertWord(String word);

    boolean containsWord(String word);

    Optional<TrieImpl> getSubtrie(String word);

    // didn't feel like making this method recursive
    boolean removeWord(String word);

    ArrayList<String> getAllWords();

    boolean isLeaf();

    boolean isRoot();
}
