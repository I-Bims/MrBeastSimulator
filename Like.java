import javax.swing.ImageIcon;
public class Like extends GameObject{

  public Like(float x, float y, ImageIcon image) {
    super(x, y, image);
  }

  public void givePoints() {
    Game.score += Game.likeValue;
  }
}
