package hw1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.awt.Color;
import static org.junit.jupiter.api.Assertions.*;

public class BlockTest {
  private Block block;
  private final double x = 10;
  private final double y = 20;
  private final int width = 30;
  private final int height = 40;
  private final Color color = Color.RED;

  private static class TestBlock extends Block {
    public TestBlock(double x, double y, int width, int height, Color color) {
      super(x, y, width, height, color);
    }

    @Override
    public void draw() {
      // Do nothing, this is just for testing
    }
  }

  @BeforeEach
  public void setUp() {
    block = new TestBlock(x, y, width, height, color);
  }

  @Test
  @DisplayName("Test getX() method")
  public void testGetX() {
    assertEquals(x, block.getX(), 0.001);
  }

  @Test
  @DisplayName("Test getY() method")
  public void testGetY() {
    assertEquals(y, block.getY(), 0.001);
  }

  @Test
  @DisplayName("Test getWidth() method")
  public void testGetWidth() {
    assertEquals(width, block.getWidth());
  }

  @Test
  @DisplayName("Test getHeight() method")
  public void testGetHeight() {
    assertEquals(height, block.getHeight());
  }

  @Test
  @DisplayName("Test getColor() method")
  public void testGetColor() {
    assertEquals(color, block.getColor());
  }

  @Test
  @DisplayName("Test top() method")
  public void testTop() {
    assertEquals(y, block.top(), 0.001);
  }

  @Test
  @DisplayName("Test bottom() method")
  public void testBottom() {
    assertEquals(y - height, block.bottom(), 0.001);
  }

  @Test
  @DisplayName("Test left() method")
  public void testLeft() {
    assertEquals(x, block.left(), 0.001);
  }

  @Test
  @DisplayName("Test right() method")
  public void testRight() {
    assertEquals(x + width, block.right(), 0.001);
  }
}