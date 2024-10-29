package hw1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.awt.Color;
import static org.junit.jupiter.api.Assertions.*;

public class SpriteTest {
  private Sprite sprite;
  private final double x = 10;
  private final double y = 20;
  private final int width = 30;
  private final int height = 40;
  private final Color color = Color.BLUE;

  private static class TestSprite extends Sprite {
    public TestSprite(double x, double y, int width, int height, Color color) {
      super(x, y, width, height, color);
    }

    @Override
    public void draw() {
      // Do nothing, this is just for testing
    }
  }

  @BeforeEach
  public void setUp() {
    sprite = new TestSprite(x, y, width, height, color);
  }

  @Test
  @DisplayName("Test constructor")
  public void testConstructor() {
    assertEquals(x, sprite.getX(), 0.001);
    assertEquals(y, sprite.getY(), 0.001);
    assertEquals(width, sprite.getWidth());
    assertEquals(height, sprite.getHeight());
    assertEquals(color, sprite.getColor());
  }

  @Test
  @DisplayName("Test initial speed is zero")
  public void testInitialSpeed() {
    assertEquals(0, sprite.getVelocityX(), 0.001);
    assertEquals(0, sprite.getVelocityY(), 0.001);
  }

  @Test
  @DisplayName("Test initial acceleration is zero")
  public void testInitialAcceleration() {
    assertEquals(0, sprite.getAccelerationX(), 0.001);
    assertEquals(0, sprite.getAccelerationY(), 0.001);
  }

  @Test
  @DisplayName("Test setVelocity() method")
  public void testSetVelocity() {
    double dx = 5;
    double dy = -3;
    sprite.setVelocity(dx, dy);
    assertEquals(dx, sprite.getVelocityX(), 0.001);
    assertEquals(dy, sprite.getVelocityY(), 0.001);
  }

  @Test
  @DisplayName("Test setAcceleration() method")
  public void testSetAcceleration() {
    double ax = 0.5;
    double ay = -0.5;
    sprite.setAcceleration(ax, ay);
    assertEquals(ax, sprite.getAccelerationX(), 0.001);
    assertEquals(ay, sprite.getAccelerationY(), 0.001);
  }

  @Test
  @DisplayName("Test move() method with velocity only")
  public void testMove() {
    double dx = 5;
    double dy = -3;
    sprite.setVelocity(dx, dy);
    sprite.move();
    assertEquals(x + dx, sprite.getX(), 0.001);
    assertEquals(y + dy, sprite.getY(), 0.001);
  }

  @Test
  @DisplayName("Test move() method with acceleration")
  public void testMoveWithAcceleration() {
    double ax = 0.5;
    double ay = 0.5;
    sprite.setAcceleration(ax, ay);

    // Move once
    sprite.move();
    // Position should not change yet because velocity is still zero
    assertEquals(x + 0, sprite.getX(), 0.001);
    assertEquals(y + 0, sprite.getY(), 0.001);
    // Velocity should be updated by acceleration
    assertEquals(0 + ax, sprite.getVelocityX(), 0.001);
    assertEquals(0 + ay, sprite.getVelocityY(), 0.001);

    // Move again
    sprite.move();
    // Position should now reflect this velocity
    assertEquals(x + ax, sprite.getX(), 0.001);
    assertEquals(y + ay, sprite.getY(), 0.001);
    // Velocity should increase further
    assertEquals(ax + ax, sprite.getVelocityX(), 0.001);
    assertEquals(ay + ay, sprite.getVelocityY(), 0.001);
  }

  @Test
  @DisplayName("Test isMovingLeft() method")
  public void testIsMovingLeft() {
    sprite.setVelocity(-1, 0);
    sprite.move();
    assertTrue(sprite.isMovingLeft());
    sprite.setVelocity(1, 0);
    sprite.move();
    assertFalse(sprite.isMovingLeft());
  }

  @Test
  @DisplayName("Test isMovingRight() method")
  public void testIsMovingRight() {
    sprite.setVelocity(1, 0);
    sprite.move();
    assertTrue(sprite.isMovingRight());
    sprite.setVelocity(-1, 0);
    sprite.move();
    assertFalse(sprite.isMovingRight());
  }

  @Test
  @DisplayName("Test isMovingUp() method")
  public void testIsMovingUp() {
    sprite.setVelocity(0, 1);
    sprite.move();
    assertTrue(sprite.isMovingUp());
    sprite.setVelocity(0, -1);
    sprite.move();
    assertFalse(sprite.isMovingUp());
  }

  @Test
  @DisplayName("Test isMovingDown() method")
  public void testIsMovingDown() {
    sprite.setVelocity(0, -1);
    sprite.move();
    assertTrue(sprite.isMovingDown());
    sprite.setVelocity(0, 1);
    sprite.move();
    assertFalse(sprite.isMovingDown());
  }
}