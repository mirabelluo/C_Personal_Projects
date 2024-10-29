package hw1;

import java.awt.Color;

/**
 * A Box is a square Sprite with a fixed length.
 */
public class Box extends Sprite {

  /**
   * Construct a Box.
   *
   * @param x coordinate of the top left corner.
   * @param y coordinate of the top left corner.
   * @param color the color of this Box.
   */
  public Box(double x, double y, Color color) {
    super(x, y, GameConstant.BOX_LENGTH, GameConstant.BOX_LENGTH, color);
  }

  @Override
  public void draw() {
    StdDraw.setPenColor(getColor());
    double halfLength = GameConstant.BOX_LENGTH / 2.0;
    double centerX = getX() + halfLength;
    double centerY = getY() - halfLength;
    StdDraw.filledSquare(centerX, centerY, halfLength);
  }

  /**
   * Check if this Box intersects with another Box.
   *
   * @param other the other Box.
   * @return true if this Box intersects with the other Box.
   */
  public boolean intersects(Box other) {
    boolean xoverlap = (this.left() <= other.right()) && (this.right() >= other.left());
    boolean yoverlap = (this.top() >= other.bottom()) && (this.bottom() <= other.top());
    return xoverlap && yoverlap;
  }
}