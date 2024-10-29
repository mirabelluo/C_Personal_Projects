package hw1;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * The main class for the Flappy Box game.
 */
public class Game {
  private FlappyBox flappyBox;
  private List<Pipe> pipes;
  private int score;
  private boolean isGameOver;

  /**
   * Public constructor to initialize the game.
   */
  public Game() {
    setUpCanvas();
    setUpGameObjects();
    score = 0;
    isGameOver = false;
  }

  // Set up the canvas for the game.
  private void setUpCanvas() {
    StdDraw.setCanvasSize(GameConstant.CANVAS_WIDTH, GameConstant.CANVAS_HEIGHT);
    StdDraw.setXscale(0, GameConstant.CANVAS_WIDTH);
    StdDraw.setYscale(0, GameConstant.CANVAS_HEIGHT);
    StdDraw.enableDoubleBuffering();
  }

  // Set up the game objects.
  private void setUpGameObjects() {
    //instantiates new flappybox object
    flappyBox = new FlappyBox(200, GameConstant.CANVAS_HEIGHT - 50);

    //instantiates new pipe object
    pipes = new ArrayList<>();
    pipes.add(new Pipe(GameConstant.CANVAS_WIDTH, GameConstant.CANVAS_HEIGHT - GameConstant.BOX_SPACE / 2.0));
  }

  /**
   * Start the game loop.
   */
  public void runGameLoop() {
    while (!isGameOver) {
      StdDraw.clear(GameConstant.CANVAS_COLOR);
      moveGameObjects();
      drawGameObjects();
      StdDraw.show();
      StdDraw.pause(GameConstant.FRAME_DELAY);
      //determines if the game needs to be terminated on each move
      if (handleCollisions() || (flappyBox.getY() <= 0) || (flappyBox.getY() >= GameConstant.CANVAS_HEIGHT)) {
        isGameOver = true;
      }
    }
    displayGameOver();
    // TODO: DELETE THIS LATER
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    StdDraw.close();
  }

  // Move the flappy box and the pipes.
  private void moveGameObjects() {
    //moves flappy box if space key is pressed
    if (StdDraw.isKeyPressed(KeyEvent.VK_SPACE)) {
      flappyBox.jump();
    }
    flappyBox.move();

    //moves pipes
    for (Pipe pipe : pipes) {
      pipe.move();
    }

    updateScore();
    recyclePipes();
  }

  // Return true if the flappy box collides with any pipe.
  private boolean handleCollisions() {
    //handling if box intersects pipe
    for (Pipe pipe : pipes) {
      if (pipe.intersects(flappyBox)) {
        return true;
      }
    }
    return false;
  }

  // Update the score if the flappy box passes a pipe.
  private void updateScore() {
    for (Pipe pipe : pipes) {
      if (pipe.right() < flappyBox.left() && !pipe.isFlappyPassedThisPipe()) {
        score++;
        pipe.setFlappyPassedThisPipe(true);
      }
    }
  }

  // Draw the flappy box and the pipes.
  private void drawGameObjects() {
    flappyBox.draw();
    for (Pipe pipe : pipes) {
      pipe.draw();
    }
    displayScore(); //generates score
  }

  // Remove the pipes that are out of the canvas and add new pipes.
  private void recyclePipes() {
    if (pipes.get(0).right() <= 0) {
      pipes.remove(0);
    }

    if (pipes.get(pipes.size() - 1).right() <= GameConstant.CANVAS_WIDTH / 2.0) {
      double x = pipes.get(pipes.size() - 1).right() + GameConstant.CANVAS_WIDTH / 2.0;
      pipes.add(new Pipe(x, GameConstant.CANVAS_HEIGHT - GameConstant.BOX_SPACE / 2.0));
    }
  }

  // Display the current score on the screen
  private void displayScore() {
    StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 32));
    StdDraw.setPenColor(GameConstant.TEXT_COLOR);
    StdDraw.textLeft(30, GameConstant.CANVAS_HEIGHT - 30, "Score: " + score);
  }

  // Display the game over screen.
  private void displayGameOver() {
    StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 32));
    StdDraw.setPenColor(GameConstant.TEXT_COLOR);
    StdDraw.text(GameConstant.CANVAS_WIDTH / 2.0, GameConstant.CANVAS_HEIGHT / 2.0, "Game Over!");
    StdDraw.show();
  }

  /**
   * Main method.
   *
   * @param args command line arguments.
   */
  public static void main(String[] args) {
    Game game = new Game();
    game.runGameLoop();
  }
}
