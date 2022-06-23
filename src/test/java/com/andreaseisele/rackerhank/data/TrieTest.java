package com.andreaseisele.rackerhank.data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


import java.util.List;
import org.junit.jupiter.api.Test;

class TrieTest {

    @Test
    void testAddAndFind() {
        String word = "test";
        Trie trie = new Trie();

        assertDoesNotThrow(() -> trie.add(word));

        final List<String> found = trie.findByPrefix("te");
        assertThat(found).containsOnly(word);
    }

}