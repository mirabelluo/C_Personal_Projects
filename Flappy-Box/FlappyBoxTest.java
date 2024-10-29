package hw1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FlappyBoxTest {
  private FlappyBox flappyBox;
  private final double x = 100;
  private final double y = 200;

  @BeforeEach
  public void setUp() {
    flappyBox = new FlappyBox(x, y);
  }

  @Test
  @DisplayName("Test constructor")
  public void testConstructor() {
    assertEquals(x, flappyBox.getX(), 0.001);
    assertEquals(y, flappyBox.getY(), 0.001);
    assertEquals(GameConstant.FLAPPY_BOX_COLOR, flappyBox.getColor());
    assertEquals(GameConstant.FALLING_SPEED, flappyBox.getFallingSpeed(), 0.001);
    assertEquals(GameConstant.JUMP_VELOCITY, flappyBox.getJumpVelocity(), 0.001);
  }

  @Test
  @DisplayName("Test getJumpVelocity() and setJumpVelocity()")
  public void testJumpVelocity() {
    assertEquals(GameConstant.JUMP_VELOCITY, flappyBox.getJumpVelocity(), 0.001);

    double newJumpVelocity = 15.0;
    flappyBox.setJumpVelocity(newJumpVelocity);
    assertEquals(newJumpVelocity, flappyBox.getJumpVelocity(), 0.001);
  }

  @Test
  @DisplayName("Test jump() method")
  public void testJump() {
    double initialY = flappyBox.getY();
    flappyBox.jump();

    // After jump, the Y coordinate should increase by the jump velocity
    assertEquals(initialY + GameConstant.JUMP_VELOCITY, flappyBox.getY(), 0.001);

    // The falling speed should reset to the initial falling speed
    assertEquals(GameConstant.FALLING_SPEED, flappyBox.getFallingSpeed(), 0.001);
  }

  @Test
  @DisplayName("Test jump() followed by move()")
  public void testJumpThenMove() {
    flappyBox.jump();
    double yAfterJump = flappyBox.getY();

    flappyBox.move();

    // After move, the Y coordinate should decrease due to falling speed
    assertTrue(flappyBox.getY() < yAfterJump);

    // The falling speed should increase due to gravity
    assertTrue(Math.abs(flappyBox.getFallingSpeed()) > Math.abs(GameConstant.FALLING_SPEED));
  }

  @Test
  @DisplayName("Test multiple jumps")
  public void testMultipleJumps() {
    double initialY = flappyBox.getY();

    for (int i = 0; i < 3; i++) {
      flappyBox.jump();

      // After each jump, the Y coordinate should increase by the jump velocity
      assertEquals(initialY + GameConstant.JUMP_VELOCITY, flappyBox.getY(), 0.001);

      // The falling speed should reset to the initial falling speed after each jump
      assertEquals(GameConstant.FALLING_SPEED, flappyBox.getFallingSpeed(), 0.001);

      initialY = flappyBox.getY();
    }
  }

  @Test
  @DisplayName("Test jump velocity adjustment")
  public void testJumpVelocityAdjustment() {
    double customJumpVelocity = 20.0;
    flappyBox.setJumpVelocity(customJumpVelocity);

    double initialY = flappyBox.getY();
    flappyBox.jump();

    // The Y coordinate should increase by the custom jump velocity
    assertEquals(initialY + customJumpVelocity, flappyBox.getY(), 0.001);
  }

  @Test
  @DisplayName("Test jump does not alter horizontal position")
  public void testJumpDoesNotAlterX() {
    double initialX = flappyBox.getX();
    flappyBox.jump();
    assertEquals(initialX, flappyBox.getX(), 0.001);
  }
}
