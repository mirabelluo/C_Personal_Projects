package hw1;

import java.awt.Color;

/**
 * A FallingBox is a Box that falls with acceleration due to gravity.
 */
public class FallingBox extends Box {

  /**
   * Constructs a FallingBox.
   *
   * @param x coordinate of the top left corner.
   * @param y coordinate of the top left corner.
   * @param color of this FallingBox.
   */
  public FallingBox(double x, double y, Color color) {
    super(x, y, color);
    super.setVelocity(0, GameConstant.FALLING_SPEED);
    super.setAcceleration(0, GameConstant.GRAVITY);
  }

  /**
   * Get the falling speed of this FallingBox.
   * @return the falling speed.
   */
  public double getFallingSpeed() {
    return super.getVelocityY();
  }

  /**
   * Get the falling acceleration of this FallingBox.
   * @return the falling acceleration.
   */
  public double getFallingAcceleration() {
    return super.getAccelerationY();
  }

  /**
   * Set the falling speed of this FallingBox.
   * @param fallingSpeed the falling speed to set.
   */
  public void setFallingSpeed(double fallingSpeed) {
    super.setVelocity(0, fallingSpeed);
  }

  /**
   * Set the falling acceleration of this FallingBox.
   * @param fallingAcceleration the falling acceleration to set.
   */
  public void setFallingAcceleration(double fallingAcceleration) {
    super.setAcceleration(0, fallingAcceleration);
  }

  @Override
  public void move() {
    super.move();
    if (this.bottom() <= 0) {
      super.setVelocity(0,0);
      super.setAcceleration(0,0);
      super.setY(super.getHeight());
    }
  }
}