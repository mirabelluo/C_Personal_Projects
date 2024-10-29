package hw3;

import exceptions.IndexException;
import hw3.sort.MeasuredIndexedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class MeasuredIndexedListTest {

  private static final int LENGTH = 15;
  private static final int DEFAULT_VALUE = 3;
  private static final int NONDEFAULT_VALUE = 0;

  private MeasuredIndexedList<Integer> measuredIndexedList;

  @BeforeEach
  void setup() {
    measuredIndexedList = new MeasuredIndexedList<>(LENGTH, DEFAULT_VALUE);
  }

  @Test
  @DisplayName("MeasuredIndexedList starts with zero reads")
  void zeroReadsStart() {
    assertEquals(0, measuredIndexedList.accesses());
  }

  @Test
  @DisplayName("MeasuredIndexedList starts with zero writes")
  void zeroWritesStart() {
    assertEquals(0, measuredIndexedList.mutations());
  }

  // TODO Add more tests
  @Test
  @DisplayName("Test reset() sets to zero reads and zero writes")
  void testResetSetToZero() {
    measuredIndexedList.reset();
    assertEquals(0, measuredIndexedList.accesses());
    assertEquals(0, measuredIndexedList.mutations());
  }

  @Test
  @DisplayName("accesses() properly increments per read")
  void testAccessesIncrement() {
    for (int i = 0; i < LENGTH; i++) {
      measuredIndexedList.get(i);
      assertEquals(i + 1, measuredIndexedList.accesses());
    }
  }

  @Test
  @DisplayName("mutations() properly increments per write")
  void testMutationsIncrement() {
    for (int i = 0; i < LENGTH; i++) {
      measuredIndexedList.put(i, 1);
      assertEquals(i + 1, measuredIndexedList.mutations());
    }
  }

  @Test
  @DisplayName("count() returns length when instantiated with all default values")
  void testCountDefaultValue() {
    assertEquals(LENGTH, measuredIndexedList.count(DEFAULT_VALUE));
  }

  @Test
  @DisplayName("count returns correct number of nondefault values")
  void testCountNonDefaultValue() {
    measuredIndexedList.put(0, NONDEFAULT_VALUE);
    measuredIndexedList.put(LENGTH - 1, NONDEFAULT_VALUE);

    assertEquals(2, measuredIndexedList.count(NONDEFAULT_VALUE));
  }

  @Test
  @DisplayName("count returns correct number if input value not found")
  void testCountValueNotFound() {
    assertEquals(0, measuredIndexedList.count(NONDEFAULT_VALUE));
  }

  @Test
  @DisplayName("count returns correct number if input value is null")
  void testCountNullValue() {
    measuredIndexedList.put(0, null);
    assertEquals(1, measuredIndexedList.count(null));
  }

  @Test
  @DisplayName("accesses() is not affected when get() throws an IndexException")
  void testAccessAfterIndexedException() {
    measuredIndexedList.get(0);
    assertEquals(1, measuredIndexedList.accesses());

    try {
      measuredIndexedList.get(LENGTH);
      fail("Expected IndexException");
    } catch (IndexException e) {
      System.out.println(e.getMessage());
    }
    assertEquals(1, measuredIndexedList.accesses());
  }

  @Test
  @DisplayName("mutations() is not affected when put() throws an IndexException")
  void testMutateAfterIndexedException() {
    measuredIndexedList.put(0, NONDEFAULT_VALUE);
    assertEquals(1, measuredIndexedList.mutations());

    try {
      measuredIndexedList.put(LENGTH, NONDEFAULT_VALUE);
      fail("Expected IndexException");
    } catch (IndexException e) {
    }
    assertEquals(1, measuredIndexedList.mutations());
  }
}
