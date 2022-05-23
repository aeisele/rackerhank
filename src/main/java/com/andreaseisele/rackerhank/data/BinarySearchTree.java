package com.andreaseisele.rackerhank.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BinarySearchTree<E extends Comparable<E>> {

    private static final Logger LOG = LoggerFactory.getLogger(BinarySearchTree.class);

    private Node<E> root;

    public boolean contains(E value) {
        return contains(value, root);
    }

    public void insert(E value) {
        root = insert(value, root);
    }

    @SafeVarargs
    public final void insertAll(E... values) {
        for (E value : values) {
            insert(value);
        }
    }

    public void delete(E value) {
        root = delete(value, root);
    }

    public List<E> inorder() {
        final var result = new ArrayList<E>();
        inorder(root, result);
        return result;
    }

    private boolean contains(E value, Node<E> node) {
        LOG.debug("contains for {} in {}", value, node);
        if (node == null) {
            return false;
        }

        final var comparison = node.compareValue(value);
        if (comparison == 0) { // found
            return true;
        }

        if (comparison < 0) { // search value is greater than node value
            return contains(value, node.rightChild);
        }

        // search value is lesser than node value
        return contains(value, node.leftChild);
    }

    private Node<E> insert(E value, Node<E> parent) {
        LOG.debug("insert for {} in {}", value, parent);

        if (parent == null) {
            return new Node<>(value);
        }

        final var comparison = parent.compareValue(value);
        if (comparison == 0) {
            LOG.warn("duplicate value -> no insert");
            return parent;
        }

        if (comparison < 0) {
            parent.rightChild = insert(value, parent.rightChild);
            return parent;
        }

        parent.leftChild = insert(value, parent.leftChild);
        return parent;
    }

    private Node<E> delete(E value, Node<E> parent) {
        LOG.debug("delete for {} in {}", value, parent);

        if (parent == null) {
            LOG.warn("value {} not found -> no deletion", value);
            return null;
        }

        final var comparison = parent.compareValue(value);

        if (comparison < 0) {
            parent.rightChild = delete(value, parent.rightChild);
            return parent;
        }

        if (comparison > 0) {
            parent.leftChild = delete(value, parent.leftChild);
            return parent;
        }

        // value found
        if (parent.leftChild == null) {
            return parent.rightChild;
        } else if (parent.rightChild == null) {
            return parent.leftChild;
        } else {
            parent.rightChild = lift(parent.rightChild, parent);
            return parent;
        }
    }

    private Node<E> lift(Node<E> node, Node<E> nodeToDelete) {
        LOG.debug("lift for {} and {}", node, nodeToDelete);
        if (node.leftChild != null) {
            node.leftChild = lift(node.leftChild, nodeToDelete);
            return node;
        }

        nodeToDelete.value = node.value;
        return node.rightChild;
    }

    private void inorder(Node<E> node, List<E> result) {
        LOG.debug("inorder traversal visiting {} -> {}", node, result);
        if (node == null) {
            return;
        }

        inorder(node.leftChild, result);
        result.add(node.value);
        inorder(node.rightChild, result);
    }

    private static class Node<E extends Comparable<E>> {
        E value;
        Node<E> leftChild;
        Node<E> rightChild;

        Node(E value) {
            Objects.requireNonNull(value, "node value must not be null");
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node[" + value + "]";
        }

        int compareValue(E value) {
            return this.value.compareTo(value);
        }

    }

}
