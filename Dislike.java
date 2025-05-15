import javax.swing.ImageIcon;
public class Dislike extends GameObject{
 
  public Dislike(float x, float y, ImageIcon image) {
    super(x, y, image);
  }

  public void givePoints() {
    Game.score += Game.dislikeValue;
  }
  
}
