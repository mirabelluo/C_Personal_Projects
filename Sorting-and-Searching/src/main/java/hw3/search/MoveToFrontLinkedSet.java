package hw3.search;

/**
 * Set implemented using a doubly linked list and move-to-front heuristic.
 *
 * @param <T> Element type.
 */
public class MoveToFrontLinkedSet<T> extends LinkedSet<T> {
  @Override
  protected Node<T> find(T t) {
    Node<T> foundNode = super.find(t);

    if (foundNode == null || foundNode == head) {
      return foundNode;
    }

    tail = (foundNode == tail) ? foundNode.prev : tail;

    if (foundNode.prev != null) {
      foundNode.prev.next = foundNode.next;
    }

    if (foundNode.next != null) {
      foundNode.next.prev = foundNode.prev;
    }

    foundNode.next = head;
    foundNode.prev = null;
    head.prev = foundNode;
    head = foundNode;

    tail = (tail == null) ? head : tail;

    return foundNode;
  }

  /**
   * Demonstrates the transpose sequential search heuristic
   * and how various operations behave under this heuristic.
   * @param args - command-line arguments (unused)
   */
  public static void main(String[] args) {
    MoveToFrontLinkedSet<Integer> set = new MoveToFrontLinkedSet<>();

    for (int i = 0; i < 10; i++) {
      set.insert(i);
    }

    System.out.println("Initial set: ");
    set.printSet();

    set.has(9);
    System.out.println("After finding 9 (9 is shifted to front): ");
    set.printSet();

    set.insert(10);
    System.out.println("After inserting 10: ");
    set.printSet();

    set.remove(3);
    System.out.println("After removing 3: ");
    set.printSet();

    set.has(9);
    System.out.println("After finding 9 (remains unchanged but 9 is found much faster): ");
    set.printSet();
  }

  private void printSet() {
    for (T t: this) {
      System.out.print(t + " ");
    }
    System.out.println();
    System.out.println();
  }
}
