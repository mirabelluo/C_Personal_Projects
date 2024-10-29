package hw3.search;

/**
 * Set implemented using plain Java arrays and transpose-sequential-search heuristic.
 *
 * @param <T> Element type.
 */
public class TransposeArraySet<T> extends ArraySet<T> {

  @Override
  protected int find(T t) {
    int idx = super.find(t);
    if (idx == -1) {
      return -1;
    }

    if (idx > 0) {
      T temp = data[idx];
      data[idx] = data[idx - 1];
      data[idx - 1] = temp;
      return idx - 1;
    }
    return idx;
  }

  /**
   * Demonstrates the transpose sequential search heuristic
   * and how various operations behave under this heuristic.
   * @param args - command-line arguments (unused)
   */
  public static void main(String[] args) {
    TransposeArraySet<Integer> set = new TransposeArraySet<>();

    for (int i = 0; i < 10; i++) {
      set.insert(i);
    }
    System.out.println("Initial set: ");
    set.printSet();

    set.has(9);
    System.out.println("After finding 9 (9 is shifted left): ");
    set.printSet();

    set.insert(10);
    System.out.println("After inserting 10: ");
    set.printSet();

    set.remove(3);
    System.out.println("After removing 3: ");
    set.printSet();

    set.has(9);
    System.out.println("After finding 9 (accessed faster and 9 is shifted left once more): ");
    set.printSet();
  }

  private void printSet() {
    for (int i = 0; i < numElements; i++) {
      System.out.print(data[i] + " ");
    }
    System.out.println();
    System.out.println();
  }
}
