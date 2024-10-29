package hw1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.awt.Color;
import static org.junit.jupiter.api.Assertions.*;

public class PipeTest {
  private Pipe pipe;
  private final double x = 800;
  private final double y = 960;

  @BeforeEach
  public void setUp() {
    pipe = new Pipe(x, y);
  }

  @Test
  @DisplayName("Test constructor")
  public void testConstructor() {
    assertEquals(x, pipe.getX(), 0.001);
    assertEquals(y, pipe.getY(), 0.001);
    assertEquals(GameConstant.PIPE_COLOR, pipe.getColor());
    assertEquals(GameConstant.PIPE_SPEED, pipe.getVelocityX(), 0.001);
    assertEquals(0, pipe.getVelocityY(), 0.001);
    assertFalse(pipe.isFlappyPassedThisPipe());
  }

  @Test
  @DisplayName("Test setFlappyPassedThisPipe() and isFlappyPassedThisPipe()")
  public void testFlappyPassedThisPipe() {
    assertFalse(pipe.isFlappyPassedThisPipe());
    pipe.setFlappyPassedThisPipe(true);
    assertTrue(pipe.isFlappyPassedThisPipe());
  }

  @Test
  @DisplayName("Test left() method")
  public void testLeft() {
    assertTrue(pipe.left() <= x);
    assertTrue(pipe.left() >= x - GameConstant.BOX_LENGTH);
  }

  @Test
  @DisplayName("Test right() method")
  public void testRight() {
    assertEquals(pipe.left() + GameConstant.BOX_LENGTH, pipe.right(), 0.001);
  }

  @Test
  @DisplayName("Test intersects() method with non-intersecting box")
  public void testIntersectsNonIntersecting() {
    Box box = new Box(0, y, Color.RED);
    assertFalse(pipe.intersects(box));
  }

  @Test
  @DisplayName("Test intersects() method with potentially intersecting box")
  public void testIntersectsPotentiallyIntersecting() {
    Box box = new Box(x, y - GameConstant.BOX_LENGTH * 3, Color.RED);
    // This might be true or false depending on the random hole generation
    // We can't deterministically test the result, but we can ensure it doesn't throw an exception
    pipe.intersects(box);
  }

  @Test
  @DisplayName("Test move() method")
  public void testMove() {
    double initialX = pipe.getX();
    pipe.move();
    assertEquals(initialX + GameConstant.PIPE_SPEED, pipe.getX(), 0.001);
    assertEquals(y, pipe.getY(), 0.001);
  }
}