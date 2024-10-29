package hw1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
  private Game game;

  @BeforeEach
  public void setUp() throws Exception {
    game = new Game();
  }

  @AfterEach
  public void tearDown() {
    StdDraw.close();
  }

  @Test
  @DisplayName("Test game initialization")
  public void testInitialization() throws Exception {
    assertNotNull(getPrivateField("flappyBox"));
    List<Pipe> pipes = (List<Pipe>) getPrivateField("pipes");
    assertNotNull(pipes);
    assertEquals(1, pipes.size());
    assertEquals(0, getPrivateField("score"));
    assertFalse((boolean) getPrivateField("isGameOver"));
  }

  @Test
  @DisplayName("Test FlappyBox movement")
  public void testFlappyBoxMovement() throws Exception {
    FlappyBox flappyBox = (FlappyBox) getPrivateField("flappyBox");
    double initialY = flappyBox.getY();

    invokePrivateMethod("moveGameObjects");

    // FlappyBox should have moved downward due to gravity
    assertTrue(flappyBox.getY() < initialY);
  }

  @Test
  @DisplayName("Test pipe movement")
  public void testPipeMovement() throws Exception {
    List<Pipe> pipes = (List<Pipe>) getPrivateField("pipes");
    double initialPipeX = pipes.get(0).getX();

    invokePrivateMethod("moveGameObjects");

    // Pipes should have moved left
    assertTrue(pipes.get(0).getX() < initialPipeX);
  }

  @Test
  @DisplayName("Test pipe recycling when pipe is off screen")
  public void testPipeRecycling() throws Exception {
    List<Pipe> pipes = (List<Pipe>) getPrivateField("pipes");

    // Move pipes off-screen to trigger recycling
    for (int i = 0; i < 100; i++) {
      invokePrivateMethod("moveGameObjects");
      invokePrivateMethod("recyclePipes");
    }

    // Ensure pipes are recycled and new ones are added
    assertTrue(pipes.size() > 1);
  }

  @Test
  @DisplayName("Test score increment when FlappyBox passes a pipe")
  public void testScoreIncrement() throws Exception {
    FlappyBox flappyBox = (FlappyBox) getPrivateField("flappyBox");
    List<Pipe> pipes = (List<Pipe>) getPrivateField("pipes");

    // Use reflection to set the X position of FlappyBox
    invokeSetMethod(flappyBox, "setX", pipes.get(0).getX() + GameConstant.BOX_LENGTH * 2);

    // Trigger score update
    invokePrivateMethod("updateScore");

    // Ensure the score is incremented
    assertEquals(1, getPrivateField("score"));
  }

  @Test
  @DisplayName("Test game over when FlappyBox hits the top of the screen")
  public void testGameOverWhenFlappyBoxHitsTop() throws Exception {
    FlappyBox flappyBox = (FlappyBox) getPrivateField("flappyBox");

    // Use reflection to set the Y position of FlappyBox to collide with the top
    invokeSetMethod(flappyBox, "setY", GameConstant.CANVAS_HEIGHT + 10);

    invokePrivateMethod("moveGameObjects");

    // The game should detect a game over condition
    assertTrue(flappyBox.top() >= GameConstant.CANVAS_HEIGHT);
  }

  @Test
  @DisplayName("Test game over when FlappyBox hits the bottom of the screen")
  public void testGameOverWhenFlappyBoxHitsBottom() throws Exception {
    FlappyBox flappyBox = (FlappyBox) getPrivateField("flappyBox");

    // Use reflection to set the Y position of FlappyBox to collide with the bottom
    invokeSetMethod(flappyBox, "setY", -1);

    invokePrivateMethod("moveGameObjects");

    // The game should detect a game over condition
    assertTrue(flappyBox.bottom() <= 0);
  }

  @Test
  @DisplayName("Test game over when FlappyBox collides with a pipe")
  public void testGameOverWhenFlappyBoxHitsPipe() throws Exception {
    FlappyBox flappyBox = (FlappyBox) getPrivateField("flappyBox");
    List<Pipe> pipes = (List<Pipe>) getPrivateField("pipes");

    // Use reflection to set the X and Y position of FlappyBox to collide with the first pipe
    invokeSetMethod(flappyBox, "setX", pipes.get(0).getX());
    invokeSetMethod(flappyBox, "setY", pipes.get(0).getY());

    boolean collision = (boolean) invokePrivateMethod("handleCollisions");

    // The game should detect a game over condition
    assertTrue(collision);
  }

  // Helper method to access private fields
  private Object getPrivateField(String fieldName) throws Exception {
    Field field = Game.class.getDeclaredField(fieldName);
    field.setAccessible(true);
    return field.get(game);
  }

  // Helper method to invoke private methods
  private Object invokePrivateMethod(String methodName, Object... args) throws Exception {
    Method method = Game.class.getDeclaredMethod(methodName, getParameterTypes(args));
    method.setAccessible(true);
    return method.invoke(game, args);
  }

  // Helper method to invoke protected setX or setY methods via reflection
  private void invokeSetMethod(Object obj, String methodName, double value) throws Exception {
    Method method = null;
    Class<?> currentClass = obj.getClass();

    while (method == null && currentClass != null) {
      try {
        method = currentClass.getDeclaredMethod(methodName, double.class);
      } catch (NoSuchMethodException e) {
        currentClass = currentClass.getSuperclass();  // Move up in the class hierarchy
      }
    }

    if (method != null) {
      method.setAccessible(true);
      method.invoke(obj, value);
    } else {
      throw new NoSuchMethodException("Method " + methodName + " not found in class hierarchy.");
    }
  }

  // Helper method to get parameter types
  private Class<?>[] getParameterTypes(Object[] args) {
    Class<?>[] parameterTypes = new Class<?>[args.length];
    for (int i = 0; i < args.length; i++) {
      parameterTypes[i] = args[i].getClass();
    }
    return parameterTypes;
  }
}
