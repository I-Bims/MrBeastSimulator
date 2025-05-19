import java.util.ArrayList;
import java.util.HashMap;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

class Game {
  public static float fallspeed = 0.25f;
  public static float playerspeed = 20;
  public static int width = 2560, height = 1440;
  public static boolean inshop, canjump;

  public static int likeValue = 10, dislikeValue = -50;


  public static int spawnrate = 50;
  public static int score = 1000000;
  public static int likeratio = 2;
  
  GamePanel gamePanel;
  public static ArrayList<ImageIcon> videos, likes, dislikes, playerIcon;
  public static HashMap<ImageIcon, Clip> audio;

  public static ArrayList<Meme> Memes;
  ArrayList<GameObject> objects;
  public int timer;

  Player player;
  Ui ui;

  public static void main(String[] args) {
    Game game = new Game();
  }

  public Game() {
    Memes = new ArrayList<Meme>();
    objects = new ArrayList<GameObject>();
    player = new Player(0, 0, null);
    objects.add(player);
    gamePanel = new GamePanel(objects, Memes);
    ui = new Ui(gamePanel, this);
    videos = new ArrayList<ImageIcon>();
    likes = new ArrayList<ImageIcon>();
    dislikes = new ArrayList<ImageIcon>();
    playerIcon = new ArrayList<ImageIcon>();
    audio = new HashMap<ImageIcon, Clip>();
    if(Util.isRunningInJar())
      Util.loadTexturesJar(videos, likes, dislikes, audio, playerIcon, ui.gameoverIcon);
    else
      Util.loadTextures(videos, likes, dislikes, audio, playerIcon, ui.gameoverIcon);
    player.setImage(playerIcon.get(0));
    System.out.println(player.image);
    System.out.println(playerIcon.get(0));
    System.out.println(videos.get(0));

  }

  public void start(String difficulty, Ui ui) throws InterruptedException{
    System.out.println("LOL");
    selectDifficulty(difficulty);
    while(score >= 0) {
      long start = System.nanoTime();
      if(score < 0)
      break;

      ui.setShop(inshop);
      if(!inshop) {
        tick();
        gamePanel.repaint();
      }
      long end;
      do {
        end = System.nanoTime();
      } while (end - start <= 10_000_000);
      String fpsstring = String.format("%.2f",1.0f/(((float)(end - start))/1000000000.0f));
      ui.fpsLabel.setText("fps: " + fpsstring);
      boolean change = false;
      if(width != ui.frame.getWidth()) {
        change = true;
        width = ui.frame.getWidth();
      }
      if(height != ui.frame.getHeight()) {
        change = true;
        height = ui.frame.getHeight();
      }
      if(change && inshop) {
        ui.resizeshop();
      }
    } 
    ui.showGameOver();
  }
  public void tick() {
    timer++;
    if(timer > spawnrate) {
      if(Util.random(1, likeratio) % likeratio == 0)
        objects.add(new Dislike(
          Util.random(0, width - 120),
          -120,
          dislikes.get(Util.random(0, dislikes.size() - 1))
        ));
      else
        objects.add(new Like(
          Util.random(0, width - 120),
          -120,
          likes.get(Util.random(0, dislikes.size() - 1))
        ));
      timer = 0;

    }
    if(objects.size() != 0) { 
      for(int i = 0; i < objects.size(); i++) {
        objects.get(i).tick();
        if(i == 0) continue; // objects[0] == player , so we skip
        if(objects.get(i).y > height)
          objects.remove(i);
        if(Util.AABB(objects.get(i), objects.get(0))){
          objects.get(i).givePoints();
          ui.scoreLabel.setText(Integer.toString(score));
          objects.remove(i);
        }
      }
    }
    if(player.x < 0)
      player.x = 0;
    if(player.x > width - 480)
      player.x = width - 480;
    if(player.y < height - 480)
      player.speedy += 1;
    else {
      player.y = height-480;
      player.speedy = 0;		
      canjump = true;
    }
  }

  private void selectDifficulty(String difficulty) {
    switch (difficulty) {
      case "Easy":
        likeratio = 6;
        fallspeed = 0.05f;
        playerspeed = 20;
        spawnrate = 30;
        break;
      case "Medium":
        likeratio = 4;
        fallspeed = 0.05f;
        playerspeed = 15;
        spawnrate = 20;
        break;
      case "Hard":
        likeratio = 2;
        fallspeed = 0.1f;
        playerspeed = 10;
        spawnrate = 20;
        break;

      default:
        break;
    }
  }
}
