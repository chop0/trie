package trie;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junitpioneer.jupiter.cartesian.CartesianTest;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(Parameterized.class)
public class TrieImplTest {
	@Parameterized.Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][]{
				{Trie.trie()},
				{Trie.concurrentTrie()}
		});

	}

	@ParameterizedTest
	@DisplayName("Testing insertion and contains")
	@MethodSource("data")
	void testInsertion(Trie trieImpl) {
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

	@ParameterizedTest
	@MethodSource("data")
	@DisplayName("Testing removal")
	void testRemoval(Trie trieImpl) {
		assertFalse(trieImpl.removeWord(""));

		assertFalse(trieImpl.removeWord("hello"));
		trieImpl.insertWord("hello");
		assertTrue(trieImpl.removeWord("hello"));
		assertFalse(trieImpl.containsWord("hello"));

		assertFalse(trieImpl.removeWord("helloa"));
		trieImpl.insertWord("hello");
		trieImpl.insertWord("hell");
		trieImpl.insertWord("helloabcdefg");
		trieImpl.insertWord("helloa");
		assertTrue(trieImpl.removeWord("helloa"));

		assertTrue(trieImpl.containsWord("hello"));
		assertTrue(trieImpl.containsWord("hell"));
		assertTrue(trieImpl.containsWord("helloabcdefg"));

		assertFalse(trieImpl.containsWord("helloa"));

		assertFalse(trieImpl.removeWord("h"));
		trieImpl.insertWord("h");
		assertTrue(trieImpl.removeWord("h"));
		assertFalse(trieImpl.containsWord("h"));

		assertFalse(trieImpl.removeWord(""));
	}

	@ParameterizedTest
	@MethodSource("data")
	void testEqualsReflexive(Trie trieImpl) {
		assertEquals(trieImpl, trieImpl);
		trieImpl.insertWord("hello");
		assertEquals(trieImpl, trieImpl);
		assertNotEquals(trieImpl, new Object());
	}

	@Test
	void testEquals() {
		var trie = Trie.trie();
		assertEquals(trie, trie);
		assertNotEquals(trie, new Object());
		trie.insertWord("hello");
		TrieImpl inner1 = new TrieImpl((TrieImpl) trie, 'c');
		TrieImpl inner2 = new TrieImpl((TrieImpl) trie, 'd');
		assertNotEquals(inner1, inner2);
		assertNotEquals(inner1, new Object());
		assertEquals(inner1, inner1);

		var ctree = new ConcurrentTrie(inner1);
		var ctree2 = new ConcurrentTrie(inner2);
		var ctree3 = new ConcurrentTrie(inner2);
		assertNotEquals(ctree, ctree2);
		assertEquals(ctree2, ctree3);

		assertEquals(inner1.compareTo(inner2), ctree.compareTo(inner2));

		assertEquals(inner1.hashCode(), ctree.hashCode());
		assertEquals(inner2.hashCode(), ctree2.hashCode());
	}

	@ParameterizedTest
	@MethodSource("data")
	void testGetAllWords(Trie trie) {
		assertIterableEquals(trie.getAllWords(), List.of());
		trie.insertWord("hello");
		assertIterableEquals(trie.getAllWords(), List.of("hello"));
		trie.insertWord("hellob");
		assertIterableEquals(trie.getAllWords(), List.of("hello", "hellob"));
	}

	@ParameterizedTest
	@MethodSource("data")
	void testGetSubtrie(Trie trie) {
		trie.insertWord("hello");
		assertEquals(((TrieImpl)(trie.getSubtrie("hello").get())).character().get().charValue(), 'o');
	}

	@ParameterizedTest
	@MethodSource("data")
	void testIsRoot(Trie trie) {
		assertTrue(trie.isRoot());
		assertTrue(trie.isLeaf());

		trie.insertWord("hello");

		assertTrue(trie.getSubtrie("hello").get().isLeaf());
		assertFalse(trie.getSubtrie("hell").get().isLeaf());
		assertFalse(trie.getSubtrie("hello").get().isRoot());
	}

	@ParameterizedTest
	@MethodSource("data")
	void testToString(Trie trie) {
		assertEquals(trie.toString(), "");
		trie.insertWord("hello");
		assertEquals(trie.toString(), "hello");
		trie.insertWord("hellob");
		assertEquals(trie.toString(),
				"""
						hello
							|-b""");
		trie.insertWord("hellobruh");
		assertEquals(trie.toString(),
				"""
						hello
							|-b
								|-ruh""");
		trie.insertWord("hellom");
		assertEquals(trie.toString(),
				"""
						hello
							|-b
								|-ruh
							|-m""");
		trie.insertWord("hellomruh");
		assertEquals(trie.toString(),
				"""
						hello
							|-b
								|-ruh
							|-m
								|-ruh""");


	}
}
