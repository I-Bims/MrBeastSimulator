import javax.swing.ImageIcon;

class Player extends GameObject {
  

  public Player(float x, float y, ImageIcon image) {
    super(x, y, image);
  }
  public Player(float x, float y, ImageIcon image, int width,int height) {
    super(x, y, image);
    this.width = width;
    this.height = height;
  }

  public void move(float speed) {
    speedx = speed;  
    if(x < 0)
      x = 0;
    if(x + width > Game.width)
      x = Game.width - width;
  }

  public void stop() {
    speedx = 0;
  }

  public void jump() {
    speedy = -20;
  }

  public void fall() {
    speedy += Game.fallspeed;
    y += speedy;
    if(y > Game.height - image.getIconHeight()) {
      speedy = 0;
      y = Game.height - image.getIconHeight();
    }
  }

  public void tick() {
    fall();
    x += speedx;
    // System.out.println(speedx);
  }
}
