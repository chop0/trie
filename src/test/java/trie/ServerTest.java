package trie;

import com.despegar.sparkjava.test.SparkServer;
import lombok.SneakyThrows;
import org.junit.ClassRule;
import org.junit.jupiter.api.*;
import server.Server;
import spark.servlet.SparkApplication;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServerTest implements Trie {
	@ClassRule
	public static SparkServer<WebAppTestSparkApp> server;


	@BeforeEach
	void resetTrie() {
		server.getApplication().server.clearTrie();
	}

	@SneakyThrows
	@BeforeAll
	static void init() {
		server = new SparkServer<>(WebAppTestSparkApp.class, 1234);

		var m = server.getClass().getDeclaredMethod("before");
		m.setAccessible(true);
		m.invoke(server);
	}

	@AfterAll
	@SneakyThrows
	static void done() {
		var m = server.getClass().getDeclaredMethod("after");
		m.setAccessible(true);
		m.invoke(server);
	}

	@Override
	@SneakyThrows
	public void insertWord(String word) {
		server.execute(server.post("/" + word, "", true));
	}

	@Override
	@SneakyThrows
	public boolean containsWord(String word) {
		var status = server.execute(server.get("/" + word, true)).code();

		Assertions.assertTrue(status == 200 || status == 404);

		return status == 200;
	}

	@Override
	public Optional<Trie> getSubtrie(String word) {
		throw new UnsupportedOperationException();
	}

	@Override
	@SneakyThrows
	public boolean removeWord(String word) {
		var status = server.execute(server.delete("/" + word, true)).code();
		Assertions.assertTrue(status == 200 || status == 404);

		return status == 200;
	}

	@Override
	public ArrayList<String> getAllWords() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isLeaf() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isRoot() {
		return true;
	}

	@Override
	public int compareTo(TrieImpl o) {
		throw new UnsupportedOperationException();
	}

	@Override
	@SneakyThrows
	public String toString() {
		var res = server.execute(server.get("/", true));
		assertEquals(200, res.code());
		return new String(res.body(), StandardCharsets.UTF_8);
	}

	@Test
	void testInsertion() {
		new TrieImplTest().testInsertion(this);
	}

	@Test
	void testRemoval() {
		new TrieImplTest().testRemoval(this);
	}

	@Test
	@Disabled
	void testGetAllWords() {
		new TrieImplTest().testGetAllWords(this);
	}

	@Test
	void testToString() {
		new TrieImplTest().testToString(this);
	}

	public static class WebAppTestSparkApp implements SparkApplication {

		public Server server;
		@SneakyThrows
		public void init() {
			server = new Server();
		}
	}


}
