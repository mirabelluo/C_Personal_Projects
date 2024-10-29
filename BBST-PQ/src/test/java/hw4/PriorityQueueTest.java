package hw4;

import exceptions.EmptyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public abstract class PriorityQueueTest {

  private PriorityQueue<Integer> pq;

  private static class absComparator implements Comparator<Integer> {
    @Override
    public int compare(Integer i1, Integer i2) {
      return Integer.compare(Math.abs(i1), Math.abs(i2)); // Compare based on absolute values
    }
  }

  private static class reverseComparator implements Comparator<Integer> {
    @Override
    public int compare(Integer i1, Integer i2) {
      return i2.compareTo(i1); // Reverse order
    }
  };

  @BeforeEach
  void setUp() {
    pq = this.createQueue();
  }

  abstract protected PriorityQueue<Integer> createQueue();

  @Test
  @DisplayName("PriorityQueue is empty after construction")
  void newQueueEmpty() {
    assertTrue(pq.empty());
  }

  @Test
  @DisplayName("Tests best() returns largest value for max heap aka best value")
  public void testInsertBubblesBestToTop() {
    pq.insert(3);
    pq.insert(5);
    pq.insert(7);
    pq.insert(0);
    pq.insert(1);

    assertEquals(pq.best(), 7);
  }

  @Test
  @DisplayName("Tests remove() returns and removes best value for basic case.")
  public void testRemoveReturnsBest() {
    pq.insert(3);
    pq.insert(5);
    pq.insert(2);
    pq.insert(7);

    assertEquals(7, pq.best());
    pq.remove();
    assertEquals(5, pq.best());
    pq.remove();
    assertEquals(3, pq.best());
    pq.remove();
    assertEquals(2, pq.best());
  }

  @Test
  @DisplayName("Tests calling remove() on all elements results in an empty queue.")
  public void testEmptyQueueAfterAllAreRemoved() {
    pq.insert(3);
    pq.insert(5);
    pq.insert(2);
    pq.insert(7);

    assertEquals(7, pq.best());
    pq.remove();
    assertEquals(5, pq.best());
    pq.remove();
    assertEquals(3, pq.best());
    pq.remove();
    assertEquals(2, pq.best());
    pq.remove();

    assertTrue(pq.empty());
  }

  @Test
  @DisplayName("Test EmptyException thrown if remove() is called on an empty queue.")
  public void testRemoveOnEmptyQueueThrowsEmptyException() {
    try {
      pq.remove();
      fail("Remove did not throw EmptyException");
    } catch (EmptyException e) {
      //passes!
    }
  }

  @Test
  @DisplayName("Test EmptyException thrown if best() is called on an empty queue.")
  public void testBestOnEmptyQueueThrowsEmptyException() {
    try {
      pq.best();
      fail("Best did not throw EmptyException");
    } catch (EmptyException e) {
      //passes!
    }
  }

  @Test
  @DisplayName("Test custom comparator absolute values returns correct best element and also removes correct best element.")
  public void testAbsoluteValueComparator() {
    Comparator<Integer> absCompare = new absComparator();
    BinaryHeapPriorityQueue<Integer> customQueue = new BinaryHeapPriorityQueue<>(absCompare);

    customQueue.insert(-10);
    customQueue.insert(7);
    customQueue.insert(-3);
    customQueue.insert(5);

    assertEquals(-10, customQueue.best()); // -10 is largest absolute value
    customQueue.remove();
    assertEquals(7, customQueue.best());
  }

  @Test
  @DisplayName("Test custom aboslute value comparator returns correct best element when two elements have equal absolute values.")
  public void testAbsoluteValueComparatorWithEqualAbsoluteValues() {
    Comparator<Integer> absCompare = new absComparator();
    BinaryHeapPriorityQueue<Integer> customQueue = new BinaryHeapPriorityQueue<>(absCompare);

    customQueue.insert(-10);
    customQueue.insert(10);
    customQueue.insert(-5);

    assertEquals(-10, customQueue.best()); // -10 and 10 are the same by abs, so should return -10 b/c insert first
    customQueue.remove();
    assertEquals(10, customQueue.best()); //assertion to check that 10 is now best
  }


  @Test
  @DisplayName("Test custom reverse comparator constructs queue in reverse order.")
  public void testInsertWithCustomComparator() throws EmptyException {

    Comparator<Integer> reverse = new reverseComparator();
    BinaryHeapPriorityQueue<Integer> customQueue = new BinaryHeapPriorityQueue<>(reverse);

    customQueue.insert(3);
    customQueue.insert(1);
    customQueue.insert(5);
    customQueue.insert(0);

    assertEquals(0, customQueue.best()); //should reverse order so 0 is best
    customQueue.remove();
    assertEquals(1, customQueue.best());
  }

  @Test
  @DisplayName("Tests iterator properly moves to next element and returns current element.")
  public void testIterator() {
    pq.insert(3);
    pq.insert(1);
    pq.insert(4);

    Iterator<Integer> iterator = pq.iterator();

    assertTrue(iterator.hasNext());
    assertEquals(4, iterator.next());
    assertTrue(iterator.hasNext());
    assertEquals(1, iterator.next());
    assertTrue(iterator.hasNext());
    assertEquals(3, iterator.next());
    assertFalse(iterator.hasNext());
  }

  @Test
  @DisplayName("Test duplicate value successfully inserted in queue and removal does not remove both keys.")
  public void testQueueWithDuplicates() {
    pq.insert(5);
    pq.insert(7);
    pq.insert(7); // Duplicate value
    pq.insert(3);

    assertEquals(7, pq.best());
    pq.remove();
    assertEquals(7, pq.best()); // Another 7 should still be present
    pq.remove();
    assertEquals(5, pq.best());
  }

  @Test
  @DisplayName("Test repeated removal and insert and insertion works properly once queue is empty.")
  public void testRemoveAllAndReinsert() {
    pq.insert(3);
    pq.insert(5);
    pq.insert(7);
    pq.remove();
    pq.remove();
    pq.remove();
    assertTrue(pq.empty());

    pq.insert(10);
    assertEquals(10, pq.best()); // Queue should behave normally after reinsertion
  }

  @Test
  @DisplayName("Test queue successfully handles insertion and ordering of negative values.")
  public void testNegativeValues() {
    pq.insert(-5);
    pq.insert(-1);
    pq.insert(3);
    assertEquals(3, pq.best()); // Should still return 3 as the largest
  }
}