package hw4;

import exceptions.EmptyException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * Priority queue implemented as a binary heap with a ranked array representation.
 *
 * @param <T> Element type.
 */
public class BinaryHeapPriorityQueue<T extends Comparable<T>> implements PriorityQueue<T> {
  private final List<T> heap;
  private Comparator<T> cmp;

  /**
   * Make a BinaryHeapPriorityQueue.
   */
  public BinaryHeapPriorityQueue() {
    this(new DefaultComparator<>());
  }

  /**
   * Make a BinaryHeapPriorityQueue with a custom comparator.
   *
   * @param cmp Comparator to use.
   */
  public BinaryHeapPriorityQueue(Comparator<T> cmp) {
    this.cmp = cmp;
    heap = new ArrayList<>();
    heap.add(null); // Add a dummy element at index 0 to simplify arithmetic
  }

  //swaps the designated indices i and j
  private void swap(int i, int j) {
    T temp = heap.get(i);
    heap.set(i, heap.get(j));
    heap.set(j, temp);
  }

  @Override
  public void insert(T t) {
    heap.add(t);
    int currentIndex = heap.size() - 1;

    while (currentIndex > 1 && cmp.compare(t, heap.get(currentIndex / 2)) > 0) {
      swap(currentIndex, currentIndex / 2);
      currentIndex = currentIndex / 2;
    }
  }

  @Override
  public void remove() throws EmptyException {
    if (this.empty()) {
      throw new EmptyException();
    }
    if (heap.size() == 2) { //case only one node in queue
      heap.remove(1);
      return;
    } else {
      swap(1, heap.size() - 1);
      heap.remove(heap.size() - 1);
      bubbleDown(1);
    }
  }

  private void bubbleDown(int currentIndex) {
    while (currentIndex * 2 < heap.size()) {
      int leftChild = 2 * currentIndex;
      int rightChild = 2 * currentIndex + 1;
      int toBubbleUp = currentIndex;

      if (leftChild < heap.size() && cmp.compare(heap.get(leftChild), heap.get(toBubbleUp)) > 0) {
        toBubbleUp = leftChild;
      }

      if (rightChild < heap.size() && cmp.compare(heap.get(rightChild), heap.get(toBubbleUp)) > 0) {
        toBubbleUp = rightChild;
      }

      if (toBubbleUp == currentIndex) {
        break; //priority ordering is restored
      }
      swap(currentIndex, toBubbleUp);
      currentIndex = toBubbleUp;
    }
  }

  @Override
  public T best() throws EmptyException {
    if (this.empty()) {
      throw new EmptyException();
    }
    return heap.get(1);
  }

  @Override
  public boolean empty() {
    return heap.size() == 1;
  }

  @Override
  public Iterator<T> iterator() {
    return new HeapIterator();
  }

  private class HeapIterator implements Iterator<T> {
    private int currentIndex = 1;

    @Override
    public boolean hasNext() {
      return currentIndex < heap.size();
    }

    @Override
    public T next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      T value = heap.get(currentIndex);
      currentIndex++;
      return value;
    }
  }

  // Default comparator is the natural order of elements that are Comparable.
  private static class DefaultComparator<T extends Comparable<T>> implements Comparator<T> {
    public int compare(T t1, T t2) {
      return t1.compareTo(t2);
    }
  }
}
