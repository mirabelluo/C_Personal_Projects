package hw3.sort;

import exceptions.IndexException;

/**
 * An ArrayIndexedList that is able to report the number of
 * accesses and mutations, as well as reset those statistics.
 *
 * @param <T> The type of the array.
 */
public class MeasuredIndexedList<T> extends ArrayIndexedList<T>
    implements Measured<T> {

  private int numAccess;
  private int numMutate;

  /**
   * Constructor for a MeasuredIndexedList.
   *
   * @param size         The size of the array.
   * @param defaultValue The initial value to set every object to in the array.
   */
  public MeasuredIndexedList(int size, T defaultValue) {
    super(size, defaultValue);
    this.numAccess = 0;
    this.numMutate = 0;
  }

  @Override
  public int length() {
    return super.length();
  }

  @Override
  public T get(int index) throws IndexException {
    T value = super.get(index);
    numAccess++;
    return value;
  }

  @Override
  public void put(int index, T value) throws IndexException {
    super.put(index, value);
    numMutate++;
  }

  @Override
  public void reset() {
    numAccess = 0;
    numMutate = 0;
  }

  @Override
  public int accesses() {
    return numAccess;
  }

  @Override
  public int mutations() {
    return numMutate;
  }

  @Override
  public int count(T value) {
    int counter = 0;
    for (int i = 0; i < length(); i++) {
      T valAtIndex = get(i);
      if (valAtIndex == value || valAtIndex.equals(value)) {
        counter++;
      }
    }
    return counter;
  }
}
