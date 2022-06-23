package com.andreaseisele.rackerhank.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

// Trie for latin lowercase alphabet words (a-z)
// not thread safe
public class Trie {

    private final Node root = new Node();

    public void add(String word) {
        var current = root;
        for (char c : word.toCharArray()) {
            current = current.get(c);
        }
        current.word = true;
    }

    public List<String> findByPrefix(String prefix) {
        var current = root;
        for (char c : prefix.toCharArray()) {
            final var maybeNode = current.find(c);
            if (maybeNode.isPresent()) {
                current = maybeNode.get();
            } else {
                return Collections.emptyList();
            }
        }

        final List<String> words = new ArrayList<>();
        dfsWithPrefix(current, prefix, words);
        return words;
    }

    private void dfsWithPrefix(Node node, String word, List<String> result) {
        if (node.word) {
            result.add(word);
        }
        for (char c = 'a'; c < 'z'; c++) {
            final var maybeNode = node.find(c);
            if (maybeNode.isPresent()) {
                dfsWithPrefix(maybeNode.get(), word + c, result);
            }
        }
    }

    public static class Node {
        private final Node[] children = new Node[26];
        private boolean word;

        public Node get(char c) {
            final var code = align(c);
            if (children[code] == null) {
                children[code] = new Node();
            }
            return children[code];
        }

        public Optional<Node> find(char c) {
            return Optional.ofNullable(children[align(c)]);
        }

        private static int align(char c) {
            return c - 'a';
        }
    }

}
