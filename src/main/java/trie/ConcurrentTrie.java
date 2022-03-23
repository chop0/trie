package trie;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentTrie implements Trie {
    private final Trie inner;
	// synchronized does not maintain the order of who gets the lock after it's done, so use a reentrantlock
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

    public Optional<Trie> getSubtrie(String word) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ConcurrentTrie that))
            return false;
        return that.inner.equals(inner);
    }

    @Override
    public String toString() {
        return inner.toString();
    }

    @Override
    public int hashCode() {
        return inner.hashCode();
    }
}
