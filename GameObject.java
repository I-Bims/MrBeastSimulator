import java.awt.Component;
import java.awt.Graphics;

import javax.swing.*;
class GameObject {
  protected float x, y;
  protected float speedx = 0, speedy = 0;
  protected ImageIcon image;
  protected int width, height;

  public GameObject(float x, float y, ImageIcon image) {
    this.x = x;
    this.y = y;
    this.image = image;
 
    if(image == null)
      return;
    width = image.getIconWidth();
    height = image.getIconHeight();
  }

  public void setImage(ImageIcon image) {
    this.image = image;
    if(image == null)
      return;
    width = image.getIconWidth();
    height = image.getIconHeight();
  }

  public void fall() { //call each tick
    speedy += Game.fallspeed;
    y += speedy; 
  }

  public void tick() {
    fall();
    x += speedx;
  }

  public ImageIcon getImage() {
    return image;
  }

  public void paint(Component c, Graphics g) {
    image.paintIcon(c, g, (int)x, (int)y);
  }

  public void givePoints() {

  }

}
