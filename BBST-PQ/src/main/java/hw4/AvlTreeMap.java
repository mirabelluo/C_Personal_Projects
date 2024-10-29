package hw4;

import java.util.Iterator;
import java.util.Stack;

/**
 * Map implemented as an AVL Tree.
 *
 * @param <K> Type for keys.
 * @param <V> Type for values.
 */
public class AvlTreeMap<K extends Comparable<K>, V> implements OrderedMap<K, V> {

  /*** Do not change variable name of 'root'. ***/
  private Node<K, V> root;
  private int size;

  @Override
  public void insert(K k, V v) throws IllegalArgumentException {
    if (k == null) {
      throw new IllegalArgumentException("cannot handle null key");
    }
    root = insert(root, k, v);
    size++;
  }

  // Insert given key and value into subtree rooted at given node;
  // return changed subtree with a new node added.
  private AvlTreeMap.Node<K, V> insert(AvlTreeMap.Node<K, V> n, K k, V v) {
    if (n == null) {
      return new AvlTreeMap.Node<>(k, v);
    }

    int cmp = k.compareTo(n.key);
    if (cmp < 0) {
      n.left = insert(n.left, k, v);
    } else if (cmp > 0) {
      n.right = insert(n.right, k, v);
    } else {
      throw new IllegalArgumentException("duplicate key " + k);
    }

    n.height = height(n);
    return balanced(n);
  }

  private int height(AvlTreeMap.Node<K, V> n) {
    if (n.left == null && n.right == null) {
      return 0;
    } else if (n.left == null) {
      return n.right.height + 1;
    } else if (n.right == null) {
      return n.left.height + 1;
    }
    return Math.max(n.left.height, n.right.height) + 1;
  }

  @Override
  public V remove(K k) throws IllegalArgumentException {
    AvlTreeMap.Node<K, V> node = findForSure(k);
    V value = node.value;
    root = remove(root, node);
    size--;
    return value;
  }

  // Remove node with given key from subtree rooted at given node;
  // Return changed subtree with given key missing.
  private AvlTreeMap.Node<K, V> remove(AvlTreeMap.Node<K, V> subtreeRoot, AvlTreeMap.Node<K, V> toRemove) {
    int cmp = subtreeRoot.key.compareTo(toRemove.key);
    if (cmp == 0) {
      return remove(subtreeRoot);
    } else if (cmp > 0) {
      subtreeRoot.left = remove(subtreeRoot.left, toRemove);
    } else {
      subtreeRoot.right = remove(subtreeRoot.right, toRemove);
    }
    subtreeRoot.height = height(subtreeRoot);

    return balanced(subtreeRoot);
  }

  // Remove given node and return the remaining tree (structural change).
  private AvlTreeMap.Node<K, V> remove(AvlTreeMap.Node<K, V> node) {
    // Easy if the node has 0 or 1 child.
    if (node.right == null) {
      return node.left;
    } else if (node.left == null) {
      return node.right;
    }

    // If it has two children, find the predecessor (max in left subtree),
    AvlTreeMap.Node<K, V> toReplaceWith = max(node);
    // then copy its data to the given node (value change),
    node.key = toReplaceWith.key;
    node.value = toReplaceWith.value;
    // then remove the predecessor node (structural change).
    node.left = remove(node.left, toReplaceWith);

    return balanced(node);
  }

  // Return a node with maximum key in subtree rooted at given node.
  private AvlTreeMap.Node<K, V> max(AvlTreeMap.Node<K, V> node) {
    AvlTreeMap.Node<K, V> curr = node.left;
    while (curr.right != null) {
      curr = curr.right;
    }
    return curr;
  }

  // Returns balance factor of a given node.
  private int balancefactor(AvlTreeMap.Node<K, V> node) {
    if (node.left == null && node.right == null) {
      return 0;
    } else if (node.left == null) {
      return -1 - node.right.height;
    } else if (node.right == null) {
      return node.left.height + 1;
    }
    return node.left.height - node.right.height;
  }

  private Node<K,V> balanced(AvlTreeMap.Node<K, V> node) {
    int bf = balancefactor(node);

    if (bf > 1) { //left heavy
      if (balancefactor(node.left) < 0) {
        node = leftRightRotation(node);
      } else {
        node = rightRotation(node);
      }
    } else if (bf < -1) {
      if (balancefactor(node.right) > 0) {
        node = rightLeftRotation(node);
      } else {
        node = leftRotation(node);
      }
    }
    return node;
  }

  private Node<K,V> leftRotation(AvlTreeMap.Node<K, V> node) {
    Node<K, V> child = node.right;
    Node<K, V> left = child.left;
    child.left = node;
    node.right = left;

    node.height = height(node);
    child.height = height(child);
    return child;
  }

  private Node<K,V> rightRotation(AvlTreeMap.Node<K, V> node) {
    Node<K, V> child = node.left;
    Node<K, V> right = child.right;
    child.right = node;
    node.left = right;

    node.height = height(node);
    child.height = height(child);
    return child;
  }

  private Node<K,V> leftRightRotation(AvlTreeMap.Node<K,V> node) {
    node.left = leftRotation(node.left);
    return rightRotation(node);
  }

  private Node<K,V> rightLeftRotation(AvlTreeMap.Node<K,V> node) {
    node.right = rightRotation(node.right);
    return leftRotation(node);
  }

  @Override
  public void put(K k, V v) throws IllegalArgumentException {
    AvlTreeMap.Node<K, V> n = findForSure(k);
    n.value = v;
  }

  // Return node for given key,
  // throw an exception if the key is not in the tree.
  private AvlTreeMap.Node<K, V> findForSure(K k) {
    AvlTreeMap.Node<K, V> n = find(k);
    if (n == null) {
      throw new IllegalArgumentException("cannot find key " + k);
    }
    return n;
  }

  @Override
  public V get(K k) throws IllegalArgumentException {
    AvlTreeMap.Node<K, V> n = findForSure(k);
    return n.value;
  }

  @Override
  public boolean has(K k) {
    if (k == null) {
      return false;
    }
    return find(k) != null;
  }

  @Override
  public int size() {
    return size;
  }

  // Return node for given key.
  private AvlTreeMap.Node<K, V> find(K k) {
    if (k == null) {
      throw new IllegalArgumentException("cannot handle null key");
    }
    AvlTreeMap.Node<K, V> n = root;
    while (n != null) {
      int cmp = k.compareTo(n.key);
      if (cmp < 0) {
        n = n.left;
      } else if (cmp > 0) {
        n = n.right;
      } else {
        return n;
      }
    }
    return null;
  }

  @Override
  public Iterator<K> iterator() {
    return new InorderIterator();
  }

  // Iterative in-order traversal over the keys
  private class InorderIterator implements Iterator<K> {
    private final Stack<AvlTreeMap.Node<K, V>> stack;

    InorderIterator() {
      stack = new Stack<>();
      pushLeft(root);
    }

    private void pushLeft(AvlTreeMap.Node<K, V> curr) {
      while (curr != null) {
        stack.push(curr);
        curr = curr.left;
      }
    }

    @Override
    public boolean hasNext() {
      return !stack.isEmpty();
    }

    @Override
    public K next() {
      AvlTreeMap.Node<K, V> top = stack.pop();
      pushLeft(top.right);
      return top.key;
    }
  }

  /*** Do not change this function's name or modify its code. ***/
  @Override
  public String toString() {
    return BinaryTreePrinter.printBinaryTree(root);
  }

  /**
   * Inner node class, each holds a key (which is what we sort the
   * BST by) as well as a value. We don't need a parent pointer as
   * long as we use recursive insert/remove helpers.
   *
   * @param <K> Type for keys.
   * @param <V> Type for values.
   **/
  private static class Node<K, V> implements BinaryTreeNode {
    Node<K, V> left;
    Node<K, V> right;
    K key;
    V value;
    int height;

    // Constructor to make node creation easier to read.
    Node(K k, V v) {
      // left and right default to null
      key = k;
      value = v;
      height = 0;
    }

    @Override
    public String toString() {
      return key + ":" + value;
    }

    @Override
    public BinaryTreeNode getLeftChild() {
      return left;
    }

    @Override
    public BinaryTreeNode getRightChild() {
      return right;
    }

    // Feel free to add whatever you want to the Node class (e.g. new fields).
    // Just avoid changing any existing names, deleting any existing variables, or modifying the overriding methods.
  }
}
