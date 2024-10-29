package hw1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.awt.Color;
import static org.junit.jupiter.api.Assertions.*;

public class BoxTest {
  private Box box;
  private final double x = 10;
  private final double y = 20;
  private final Color color = Color.RED;

  @BeforeEach
  public void setUp() {
    box = new Box(x, y, color);
  }

  @Test
  @DisplayName("Test constructor")
  public void testConstructor() {
    assertEquals(x, box.getX(), 0.001);
    assertEquals(y, box.getY(), 0.001);
    assertEquals(GameConstant.BOX_LENGTH, box.getWidth());
    assertEquals(GameConstant.BOX_LENGTH, box.getHeight());
    assertEquals(color, box.getColor());
  }

  @Test
  @DisplayName("Test intersects() method with overlapping boxes")
  public void testIntersectsOverlapping() {
    Box other = new Box(x + GameConstant.BOX_LENGTH / 2.0, y, Color.BLUE);
    assertTrue(box.intersects(other));
  }

  @Test
  @DisplayName("Test intersects() method with non-overlapping boxes")
  public void testIntersectsNonOverlapping() {
    Box other = new Box(x + GameConstant.BOX_LENGTH * 2, y, Color.BLUE);
    assertFalse(box.intersects(other));
  }

  @Test
  @DisplayName("Test intersects() method with touching boxes")
  public void testIntersectsTouching() {
    Box other = new Box(x + GameConstant.BOX_LENGTH, y, Color.BLUE);
    assertTrue(box.intersects(other));
  }

  @Test
  @DisplayName("Test intersects() method with vertically aligned boxes")
  public void testIntersectsVerticallyAligned() {
    Box other = new Box(x, y + GameConstant.BOX_LENGTH / 2.0, Color.BLUE);
    assertTrue(box.intersects(other));
  }

  @Test
  @DisplayName("Test intersects() method with diagonally positioned boxes")
  public void testIntersectsDiagonal() {
    Box other = new Box(x + GameConstant.BOX_LENGTH / 2.0, y + GameConstant.BOX_LENGTH / 2.0, Color.BLUE);
    assertTrue(box.intersects(other));
  }
}