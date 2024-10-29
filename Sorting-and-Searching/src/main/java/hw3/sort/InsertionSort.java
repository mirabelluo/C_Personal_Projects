package hw3.sort;


/**
 * The Insertion Sort algorithm, with minimizing swaps optimization.
 *
 * @param <T> Element type.
 */
public final class InsertionSort<T extends Comparable<T>>
    implements SortingAlgorithm<T> {

  @Override
  public void sort(IndexedList<T> indexedList) {
    for (int i = 1; i < indexedList.length(); i++) {
      T dataToSort = indexedList.get(i);
      int j = i - 1;

      while (j >= 0 && dataToSort.compareTo(indexedList.get(j)) < 0) {
        indexedList.put(j + 1, indexedList.get(j));
        j--;
      }
      indexedList.put(j + 1, dataToSort);
    }
  }

  @Override
  public String name() {
    return "Insertion Sort";
  }
}
