import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GamePanel extends JPanel{

  ArrayList<Meme> Memes;
  ArrayList<GameObject> objects;

  public GamePanel(ArrayList<GameObject> objects, ArrayList<Meme> Memes) {
    this.Memes = Memes;
    this.objects = objects;
  }

  @Override
  protected void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    // background.paintIcon(this, g, 0, 0);
    for(int i = 0; i < Memes.size(); i++) {
      Memes.get(i).video.paintIcon(this, g, Memes.get(i).x, Memes.get(i).y);
    }
    if(objects.size() == 0)
    return;
    for(GameObject obj : objects) {
      obj.paint(this, g);
    }
  }
}
