package trie;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentTrie implements Trie {
    private final Trie inner;
    private final ReentrantLock reentrantLock;

    public ConcurrentTrie(Trie inner) {
        this.inner = inner;
        this.reentrantLock = new ReentrantLock(true);
    }

    public void insertWord(String word) {
        reentrantLock.lock();
        inner.insertWord(word);
        reentrantLock.unlock();
    }

    public boolean containsWord(String word) {
        reentrantLock.lock();
        var result = inner.containsWord(word);
        reentrantLock.unlock();
        return result;
    }

    public Optional<TrieImpl> getSubtrie(String word) {
        reentrantLock.lock();
        var result = inner.getSubtrie(word);
        reentrantLock.unlock();
        return result;
    }

    public boolean removeWord(String word) {
        reentrantLock.lock();
        var result = inner.removeWord(word);
        reentrantLock.unlock();
        return result;
    }

    public ArrayList<String> getAllWords() {
        reentrantLock.lock();
        var result = inner.getAllWords();
        reentrantLock.unlock();
        return result;
    }

    public boolean isLeaf() {
        reentrantLock.lock();
        var result = inner.isLeaf();
        reentrantLock.unlock();
        return result;
    }

    public boolean isRoot() {
        reentrantLock.lock();
        var result = inner.isRoot();
        reentrantLock.unlock();
        return result;
    }

    public int compareTo(TrieImpl o) {
        reentrantLock.lock();
        var result = inner.compareTo(o);
        reentrantLock.unlock();
        return result;
    }
}
