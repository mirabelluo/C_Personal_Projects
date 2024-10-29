package hw4;

import java.util.Random;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * In addition to the tests in BinarySearchTreeMapTest (and in OrderedMapTest & MapTest),
 * we add tests specific to Treap.
 */
@SuppressWarnings("All")
public class TreapMapTest extends BinarySearchTreeMapTest {

  private void printSeed() { //to see what random seed values are needed - not a test
      Random rand = new Random(1234);

      for (int i = 0; i < 6; i++) {
          System.out.print(rand.nextInt() + " ");
      }
      System.out.println();
  }

  @Override
  protected Map<String, String> createMap() {
      return new TreapMap<>(1234);
  }

  //  (think about how you might write tests while randomness is involved in TreapMap implementation!)

  @Test
  @DisplayName("Tests if basic tree correctly rotates left after last insertion.")
  public void insertLeftRotation() {
      map.insert("3", "c");
      map.insert("5", "e"); //must do a left rotation here

      String[] expected = new String[]{
              "3:c:-1517918040",
              "null 5:e:1115789266"
      };
      assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("Tests if basic tree correctly rotates right after last insertion.")
  public void insertRightRotation() {
      map.insert("5", "e");
      map.insert("3", "c"); //must do a right rotation here

      String[] expected = new String[]{
              "5:e:-1517918040",
              "3:c:1115789266 null"

      };
      assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("Tests if basic tree does two rotations after last insert.")
  public void testMultRotationInsert() {
    map.insert("4", "4");
    map.insert("2", "2");
    map.insert("5", "5");
    map.insert("1", "1");
    map.insert("3", "3"); //causes multiple rotations

    String[] expected = new String[]{
            "4:4:-1517918040",
            "3:3:-611652875 5:5:-208917030",
            "1:1:1019800440 null null null",
            "null 2:2:1115789266 null null null null null null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("Tests if basic tree correctly rotates right after deletion.")
  public void removeRightRotation() {
      map.insert("7", "g");
      map.insert("5", "e");
      map.insert("3", "c");
      map.insert("1", "a");


      map.remove("5");

      String[] expected = new String[]{
              "7:g:-1517918040",
              "3:c:-208917030 null",
              "1:a:1019800440 null null null"
      };
      assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("Tests if basic tree correctly rotates left after deletion.")
  public void removeLeftRotation() {
      map.insert("7", "g");
      map.insert("5", "e");
      map.insert("3", "c");
      map.insert("8", "h");

      map.remove("5");

      String[] expected = new String[]{
              "7:g:-1517918040",
              "3:c:-208917030 8:h:1019800440"
      };
      assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("Tests if basic tree does two rotations after removal.")
  public void testMultRotationRemove() {
    map.insert("4", "4");
    map.insert("2", "2");
    map.insert("8", "8");
    map.insert("5", "5");
    map.insert("1", "1");
    map.insert("3", "3");
    map.remove("4"); //causes multiple rotations

    String[] expected = new String[]{
            "1:1:-611652875",
            "null 8:8:-208917030",
            "null null 5:5:1019800440 null",
            "null null null null 2:2:1115789266 null null null",
            "null null null null null null null null null 3:3:1362132786 null null null null null null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("Tests that IllegalArgumentException is thrown when remove is called on an empty tree.")
  public void testRemoveEmptyTree(){
      try {
          map.remove("1");
          fail("Should have thrown IllegalArgumentException");
      }
      catch (IllegalArgumentException e){
          return;
      }
  }

  @Test
  @DisplayName("Tests that IllegalArgumentException is thrown when attempting to insert a duplicate key.")
  public void testInsertDuplicateKeys() {
    map.insert("5", "a");
    try {
        map.insert("5", "b");
        fail("Should have thrown IllegalArgumentException");
    } catch (IllegalArgumentException e) {
        return;
    }
  }

  @Test
  @DisplayName("Tests insertion and removal responded correctly when repeatedly called.")
  public void testRepeatedInsertionAndRemoval() {
    for (int i = 0; i < 10; i++) {
      map.insert(String.valueOf(i), String.valueOf(i));
    }
    for (int i = 2; i < 10; i++) {
      map.remove(String.valueOf(i));
    }

    String[] expected = new String[]{
            "0:0:-1517918040",
            "null 1:1:1115789266"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }
}