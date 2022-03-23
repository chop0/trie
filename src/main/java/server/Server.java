package server;

import trie.Trie;

import java.util.List;

import static spark.Spark.*;

public class Server {
	private Trie trie;
	public void clearTrie() {
		trie = Trie.concurrentTrie();
	}

	public Server() {
		this.trie = Trie.concurrentTrie();

		delete("/:word", (req, res) -> {
			var word = req.params("word");
			if (trie.removeWord(word))
				return "Removed word.";
			else
				return null;

		});

		get("/:word", (req, res) -> {
					var word = req.params("word");
					if (!trie.containsWord(word)) {
						res.status(404);
						return "Exact match for {%s} not found.".formatted(word);
					} else
						return "Exact match for {%s} present.".formatted(word);
				}
		);

		get("/:word/completions", (req, res) -> {
					var word = req.params("word");
					var subtrie = trie.getSubtrie(word);

					if (subtrie.isPresent())
						return subtrie.get().getAllWords().stream().map(n -> n.substring(1)).toList();
					else
						return List.of();
				}
		);

		get("/", (req, res) -> trie.toString());


		post("/:word", (req, res) -> {
			var word = req.params("word");

			var containsBefore = trie.containsWord(word);
			trie.insertWord(word);
			res.status(containsBefore ? 204 : 201);
			return (containsBefore ? "{%s} already present." : "Added {%s}").formatted(word);
		});
	}

    public static void main(String[] argv) {
        new Server();
    }


}
