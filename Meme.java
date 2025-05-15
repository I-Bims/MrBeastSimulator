import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Meme {
    ImageIcon video;
    int x, y;
    int inner = 300;
    public Meme(ImageIcon video, ArrayList<Meme> Memes) {
      this.video = video;
      boolean tooclose = false;
      int maxiteration = 10;
      int iterations = 0;
      do {
        iterations++;
        x = Util.random(0, Game.width - video.getIconWidth());
        y = Util.random(0, Game.height - video.getIconHeight());
        if(Memes.size() == 0) return;
        int maxoverlap = 200;
        for(Meme v : Memes) {
          if(
          v.x + v.video.getIconWidth() - maxoverlap > this.x && 
          v.y + v.video.getIconHeight() - maxoverlap > this.y && 
          v.x < this.x + video.getIconWidth() - maxoverlap &&
          v.y < this.y + video.getIconHeight() - maxoverlap
          )
          {
            tooclose = true;
            break;
          }

        }
      }while(tooclose && iterations < maxiteration);


    }
  }
