package com.andreaseisele.rackerhank.data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


import org.junit.jupiter.api.Test;

class BinarySearchTreeTest {

    @Test
    void testInsertAndContains() {
        final var tree = new BinarySearchTree<Integer>();
        tree.insert(1);
        tree.insert(3);
        tree.insert(2);

        assertThat(tree.contains(1)).isTrue();
        assertThat(tree.contains(5)).isFalse();
    }

    @Test
    void testDeleteWithTwoChildren() {
        final var tree = new BinarySearchTree<Integer>();
        tree.insertAll(50, 25, 75, 10, 33, 56, 89, 4, 11, 30, 40, 52, 61, 82, 95);

        tree.delete(56);

        assertThat(tree.contains(56)).isFalse();
        assertThat(tree.contains(52)).isTrue();
        assertThat(tree.contains(61)).isTrue();
    }

    @Test
    void testDeleteLeaf() {
        final var tree = new BinarySearchTree<Integer>();
        tree.insertAll(50, 25, 75, 10, 33, 56, 89, 4, 11, 30, 40, 52, 61, 82, 95);

        tree.delete(95);

        assertThat(tree.contains(95)).isFalse();
    }

    @Test
    void testDeleteNonExisting() {
        final var tree = new BinarySearchTree<Integer>();
        tree.insertAll(3, 2, 1);

        assertDoesNotThrow(() -> tree.delete(4));
    }

    @Test
    void testInorderTraversal() {
        final var tree = new BinarySearchTree<Integer>();
        tree.insertAll(50, 25, 75, 10, 33, 56, 89, 4, 11, 30, 40, 52, 61, 82, 95);

        final var listInOrder = tree.inorder();

        assertThat(listInOrder)
            .hasSize(15)
            .isSorted();
    }

}