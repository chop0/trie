package trie;

import java.util.ArrayList;
import java.util.Optional;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicBoolean;

public interface Trie extends Comparable<TrieImpl> {
    static Trie trie() {
        return new TrieImpl(new AtomicBoolean(), null, Optional.empty(), new TreeSet<>());
    }

	/**
	 * @return a concurrency-safe trie (regular tree but with locks)
	 */
    static Trie concurrentTrie() {
        return new ConcurrentTrie(trie());
    }

	/**
	 * @param word word to insert
	 */
    void insertWord(String word);

	/**
	 * @return whether `word` is in the trie as a keyword
	 */
    boolean containsWord(String word);

	/**
	 * @return a subtrie starting at the end of `word`
	 */
    Optional<Trie> getSubtrie(String word);

    // didn't feel like making this method recursive

	/**
	 * @param word to remove
	 * @return whether the removal was successful (false if not present)
	 */
    boolean removeWord(String word);

	/**
	 * @return all full words contained in the tree
	 */
    ArrayList<String> getAllWords();

	/**
	 * @return whether this tree has no children
	 */
    boolean isLeaf();

	/**
	 * @return whether this datum is a tree root or not
	 */
    boolean isRoot();
}
