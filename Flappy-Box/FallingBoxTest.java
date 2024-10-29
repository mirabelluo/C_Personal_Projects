package hw1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

public class FallingBoxTest {
  private FallingBox fallingBox;
  private final double x = 100;
  private final double y = 200;
  private final Color color = Color.RED;

  @BeforeEach
  public void setUp() {
    fallingBox = new FallingBox(x, y, color);
  }

  @Test
  @DisplayName("Test constructor")
  public void testConstructor() {
    assertEquals(x, fallingBox.getX(), 0.001);
    assertEquals(y, fallingBox.getY(), 0.001);
    assertEquals(color, fallingBox.getColor());
    assertEquals(GameConstant.FALLING_SPEED, fallingBox.getFallingSpeed(), 0.001);
    assertEquals(GameConstant.GRAVITY, fallingBox.getFallingAcceleration(), 0.001);
  }

  @Test
  @DisplayName("Test getFallingSpeed() and setFallingSpeed()")
  public void testFallingSpeed() {
    assertEquals(GameConstant.FALLING_SPEED, fallingBox.getFallingSpeed(), 0.001);

    double newSpeed = -5.0; // Setting a new falling speed
    fallingBox.setFallingSpeed(newSpeed);
    assertEquals(newSpeed, fallingBox.getFallingSpeed(), 0.001);
  }

  @Test
  @DisplayName("Test getFallingAcceleration() and setFallingAcceleration()")
  public void testFallingAcceleration() {
    assertEquals(GameConstant.GRAVITY, fallingBox.getFallingAcceleration(), 0.001);

    double newAcceleration = 0.1;
    fallingBox.setFallingAcceleration(newAcceleration);
    assertEquals(newAcceleration, fallingBox.getFallingAcceleration(), 0.001);
  }

  @Test
  @DisplayName("Test move() method with initial settings")
  public void testMove() {
    double initialY = fallingBox.getY();
    double initialSpeed = fallingBox.getFallingSpeed();

    fallingBox.move();

    // After the first move, the Y position should decrease by initialSpeed
    assertEquals(initialY + initialSpeed, fallingBox.getY(), 0.001);

    // The speed should have increased by the acceleration due to gravity
    assertEquals(initialSpeed + GameConstant.GRAVITY, fallingBox.getFallingSpeed(), 0.001);
  }

  @Test
  @DisplayName("Test multiple moves")
  public void testMultipleMoves() {
    double initialY = fallingBox.getY();
    double initialSpeed = fallingBox.getFallingSpeed();
    int steps = 100;

    for (int i = 0; i < steps; i++) {
      fallingBox.move();

      // If the box has hit the ground, it should stop moving
      if (fallingBox.bottom() <= 0) {
        assertEquals(fallingBox.getHeight(), fallingBox.getY(), 0.001);
        assertEquals(0, fallingBox.getFallingSpeed(), 0.001);
        assertEquals(0, fallingBox.getFallingAcceleration(), 0.001);
        return;
      }
    }

    // If it hasn't hit the ground, it should still be falling
    assertTrue(fallingBox.getY() > initialY - steps * initialSpeed);
    assertTrue(fallingBox.getFallingSpeed() > initialSpeed);
  }

  @Test
  @DisplayName("Test fallingBox stops at ground level")
  public void testStopsAtGroundLevel() {
    // Drop the box until it should hit the ground
    while (fallingBox.bottom() > 0) {
      fallingBox.move();
    }

    // The box should stop at ground level
    assertEquals(fallingBox.getHeight(), fallingBox.getY(), 0.001);
    assertEquals(0, fallingBox.getFallingSpeed(), 0.001);
    assertEquals(0, fallingBox.getFallingAcceleration(), 0.001);
  }

  @Test
  @DisplayName("Test fallingBox moves in correct direction")
  public void testMoveDirection() {
    fallingBox.move();
    assertTrue(fallingBox.isMovingDown());
    assertFalse(fallingBox.isMovingUp());

    // Simulate a condition where velocity would reverse
    fallingBox.setFallingSpeed(10);  // Move upward
    fallingBox.move();
    assertFalse(fallingBox.isMovingDown());
    assertTrue(fallingBox.isMovingUp());
  }
}
