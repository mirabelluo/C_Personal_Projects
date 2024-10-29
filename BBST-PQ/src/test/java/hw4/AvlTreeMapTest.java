package hw4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * In addition to the tests in BinarySearchTreeMapTest (and in OrderedMapTest & MapTest),
 * we add tests specific to AVL Tree.
 */
@SuppressWarnings("All")
public class AvlTreeMapTest extends BinarySearchTreeMapTest {

  @Override
  protected Map<String, String> createMap() {
    return new AvlTreeMap<>();
  }

  @Test
  @DisplayName("Tests if basic tree correctly rotates left after last insertion.")
  public void insertLeftRotation() { //remember that all keys are strings!
    map.insert("1", "a");
    map.insert("2", "b");
    map.insert("3", "c");

    String[] expected = new String[]{
        "2:b",
        "1:a 3:c"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("Tests if basic tree correctly rotates right after last insertion.")
  public void insertRightRotation() {
    map.insert("3", "c");
    map.insert("2", "b");
    map.insert("1", "a");
    String[] expected = new String[]{
            "2:b",
            "1:a 3:c"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("Tests if basic tree correctly rotates right-left after last insertion.")
  public void insertRightLeftRotation() {
    map.insert("1", "a");
    map.insert("3", "c");
    map.insert("2", "b");

    String[] expected = new String[]{
            "2:b",
            "1:a 3:c"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("Tests if basic tree correctly rotates left-right after last insertion.")
  public void insertLeftRightRotation() {
    map.insert("3", "c");
    map.insert("1", "a");
    map.insert("2", "b");
    String[] expected = new String[]{
            "2:b",
            "1:a 3:c"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("Tests if basic tree correctly rotates right after removal.")
  public void removeRightRotation() {
    map.insert("7", "g");
    map.insert("1", "a");
    map.insert("5", "e");
    map.insert("3","c");


    map.remove("7");

    String[] expected = new String[]{
            "3:c",
            "1:a 5:e"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("Tests if basic tree correctly rotates left after removal.")
  public void removeLeftRotation() {
    map.insert("5", "e");
    map.insert("7", "g");
    map.insert("3","c");
    map.insert("8", "h");


    map.remove("3");

    String[] expected = new String[]{
            "7:g",
            "5:e 8:h"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("Tests if basic tree correctly rotates left-right after removal.")
  public void removeleftRightRotation() {
    map.insert("7", "g");
    map.insert("3","c");
    map.insert("5", "e");
    map.insert("4", "d");


    map.remove("7");
    String[] expected = new String[]{
            "4:d",
            "3:c 5:e"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("Tests if basic tree correctly rotates right-left after removal.")
  public void removerightLeftRotation() {
    map.insert("7", "g");
    map.insert("3","c");
    map.insert("5", "e");
    map.insert("6", "f");


    map.remove("3");
    String[] expected = new String[]{
            "6:f",
            "5:e 7:g"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("Tests if complex tree correctly right-left rotates after last insertion")
  public void testRightLeftInsertionMultipleSubTrees() {
    map.insert("e","e");
    map.insert("j","j");
    map.insert("c","c");
    map.insert("d","d");
    map.insert("a","a");
    map.insert("h","h");
    map.insert("k","k");
    map.insert("i","i");
    map.insert("g","g");
    map.insert("l","l");

    map.insert("f", "f"); //does a right-left

    String[] expected = new String[]{
            "h:h",
            "e:e j:j",
            "c:c g:g i:i k:k",
            "a:a d:d f:f null null null null l:l"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("Tests if complex tree correctly right-left rotates after deletion")
  public void testRightLeftDeletionMultipleSubTrees() {
    map.insert("e","e");
    map.insert("j","j");
    map.insert("c","c");
    map.insert("d","d");
    map.insert("a","a");
    map.insert("h","h");
    map.insert("k","k");
    map.insert("i","i");
    map.insert("l","l");
    map.insert("g","g");
    map.insert("b","b");
    map.insert("f","f");

    map.remove("b"); //does a right-left

    String[] expected = new String[]{
            "h:h",
            "e:e j:j",
            "c:c g:g i:i k:k",
            "a:a d:d f:f null null null null l:l"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("Tests if complex tree correctly left-right rotates after last insertion")
  public void testLeftRightInsertionMultipleSubTrees() {
    map.insert("h", "h");
    map.insert("c", "c");
    map.insert("b", "b");
    map.insert("k", "k");
    map.insert("e", "e");
    map.insert("i", "i");
    map.insert("a", "a");
    map.insert("l", "l");
    map.insert("d", "d");
    map.insert("f", "f");

    map.insert("g", "g"); //does a left-right

    String[] expected = new String[]{
            "e:e",
            "c:c h:h",
            "b:b d:d f:f k:k",
            "a:a null null null null g:g i:i l:l"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("Tests if complex tree correctly left-right rotates after deletion")
  public void testLeftRightDeletionMultipleSubTrees() {
    map.insert("h", "h");
    map.insert("c", "c");
    map.insert("b", "b");
    map.insert("k", "k");
    map.insert("e", "e");
    map.insert("i", "i");
    map.insert("a", "a");
    map.insert("l", "l");
    map.insert("d", "d");
    map.insert("j","j");
    map.insert("f", "f");
    map.insert("g", "g");

    map.remove("j"); //does a left-right

    String[] expected = new String[]{
            "e:e",
            "c:c h:h",
            "b:b d:d f:f k:k",
            "a:a null null null null g:g i:i l:l"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }
  @Test
  @DisplayName("Tests IllegalArgumentException is thrown if removal is called on an empty tree.")
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
  @DisplayName("Tests IllegalArgumentException is thrown if a duplicate key is inserted.")
  public void testInsertDuplicateKeys() {
    map.insert("5", "a");
    try {
      map.insert("5", "b");
      fail("Should have thrown IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      return;
    }
  }
}
