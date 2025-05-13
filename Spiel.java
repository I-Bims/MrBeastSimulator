import javax.swing.*;

import javax.sound.sampled.*;


import java.util.*;
import java.io.*;
import java.util.stream.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
public class Spiel extends JPanel {
  int width = 2560, height = 1440;
  JFrame frame;
  JLabel scoreLabel;
  JLabel fpsLabel;
  JLabel shopscore;
  JPanel shopoverlay;
  JPanel shop;
  JPanel shopPanel;
  ShopItem video;
  ShopItem movement;
  ShopItem likeratioup;
  ArrayList<Stuff> objs = new ArrayList<Stuff>();
  ArrayList<ImageIcon> likes;
  ArrayList<ImageIcon> dislikes;
  int timer = 0;
  Player player;
  ImageIcon background;
  int values[] = {10,-10};
  HashMap<ImageIcon, Clip> audiohash;
  ArrayList<ImageIcon> videos; 
  ArrayList<Integer> allowedindecies;
  String currScreen;

  ImageIcon gameoverIcon;
  ArrayList<Clip> audio;

  ArrayList<Video> Memes;
  int score = 100000;
  boolean canjump;
  int[] speedupsAt = {500,1000,Integer.MAX_VALUE};
  int speeduptotal = 0;
  int countvideos = 0;
  boolean inshop = false;

  // upgradable
  int spawnrate = 20;
  float fallspeed = 0.1f;
  int speed = 10;	
  int likeratio = 2;

  public class Stuff {
    float x, y, speedy = 0, score;
    ImageIcon image;
    public Stuff(int x,int y,ImageIcon image, int score) {
      this.image = image;
      this.x = x;
      this.y = y;	
      this.score = score;
    }
  }

  public class Video {
    ImageIcon video;
    int x, y;
    int inner = 300;
    public Video(ImageIcon video) {
      this.video = video;
      boolean tooclose = false;
      int maxiteration = 10;
      int iterations = 0;
      do {
        iterations++;
        x = random(0, width - video.getIconWidth());
        y = random(0, height - video.getIconHeight());
        if(Memes.size() == 0) return;
        int maxoverlap = 200;
        for(Video v : Memes) {
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

  public class ShopItem {
    JLabel title;
    RoundedButton purchase;
    JLabel description;
    JPanel titlePanel;
    JPanel purchasePanel;
    JPanel descriptionPanel;
    int price;
    public ShopItem(JLabel title, RoundedButton purchase, JLabel description, int price) {
      this.price = price;
      this.title = title;
      this.purchase = purchase;
      this.description = description;


      this.purchase.setPreferredSize(new Dimension(500,200));

      this.title.setAlignmentY(Component.TOP_ALIGNMENT);     
      this.title.setBorder(BorderFactory.createEmptyBorder(150, 0, 0, 0));

      titlePanel = new JPanel(new FlowLayout());
      purchasePanel = new JPanel(new FlowLayout());
      descriptionPanel = new JPanel(new FlowLayout());

      titlePanel.setBackground(Color.DARK_GRAY);
      purchasePanel.setBackground(Color.DARK_GRAY);
      descriptionPanel.setBackground(Color.DARK_GRAY);

      titlePanel.add(title);
      purchasePanel.add(purchase);
      descriptionPanel.add(description);

      this.title.setForeground(Color.white);
      this.description.setForeground(Color.white);

      this.title.setFont(new Font("Arial", Font.PLAIN, 50));
      this.purchase.setFont(new Font("Arial", Font.PLAIN, 30));
      this.description.setFont(new Font("Arial", Font.PLAIN, 20));
    }

    public void addto(JPanel panel) {
      panel.add(title);
      panel.add(purchase);
      panel.add(description);    

    }
  }


  public Spiel(){
    player = new Player(0, 0, resize(480,480,"MrBeast.png"));
    frame = new JFrame("Game");
    //background = new ImageIcon("Subway.gif");
    background = resize(width, height,"Background.png");
    CardLayout layout = new CardLayout();
    JPanel main = new JPanel(layout);

    JPanel menuPanel = new JPanel();
    //menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

    GridBagConstraints gbc = new GridBagConstraints();
    menuPanel.setLayout(new GridBagLayout());

    menuPanel.setBackground(Color.DARK_GRAY); // für besseren Look
    gbc.insets = new Insets(15, 15, 15, 15); // Abstand zwischen Elementen
    gbc.gridx = 0;
    gbc.fill = GridBagConstraints.NONE;
    gbc.anchor = GridBagConstraints.CENTER;
    menuPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

    RoundedButton startButton = new RoundedButton("Start Game",
    new Color(0x3b7be3), 100);
    startButton.setPreferredSize(new Dimension(300,100));
    startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    startButton.setFont(new Font("Arial", Font.PLAIN, 30));


    JLabel title = new JLabel("  Mr Beast Simulator");
    title.setAlignmentX(Component.CENTER_ALIGNMENT);
    title.setPreferredSize(new Dimension(600,200));
    title.setFont(new Font("Arial", Font.PLAIN, 60));
    title.setForeground(new Color(0xffffff));

    String[] difficulties = {"Easy", "Medium", "Hard"};
    JComboBox<String> difficultyBox = new JComboBox<>(difficulties);
    difficultyBox.setAlignmentX(Component.CENTER_ALIGNMENT);

    menuPanel.add(title, gbc);
    menuPanel.add(startButton,gbc);
    menuPanel.add(difficultyBox,gbc);
    
    this.setBackground(Color.DARK_GRAY);
    scoreLabel = new JLabel();
    //scoreLabel.setPreferredSize(new Dimension(500,100));
    scoreLabel.setText("100");
    scoreLabel.setFont(new Font("Arial", Font.PLAIN, 60));
    scoreLabel.setForeground(new Color(0xffffff));
    scoreLabel.setBounds(width / 2 - 250, 10, 500, 100);
    scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);


    fpsLabel = new JLabel();
    //fpsLabel.setPreferredSize(new Dimension(500,100));
    fpsLabel.setText("0");
    fpsLabel.setFont(new Font("Arial", Font.PLAIN, 30));
    fpsLabel.setForeground(Color.lightGray);
    fpsLabel.setBounds(10, 10, 500, 50);
    //fpsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    setLayout(null);
    this.add(fpsLabel);
    this.add(scoreLabel);

    JPanel gameoverPanel = new JPanel() {
      @Override
      protected void paintComponent(Graphics g)
      {
        super.paintComponent(g);
        gameoverIcon.paintIcon(this, g, width/2 - gameoverIcon.getIconWidth()/2, -400);
      }
    };
    gameoverPanel.setLayout(new BoxLayout(gameoverPanel, BoxLayout.Y_AXIS));
    gameoverPanel.setBackground(Color.DARK_GRAY);

    JLabel gameoverLabel = new JLabel("Gameover!");
    RoundedButton retryButton = new RoundedButton("Retry?",
    new Color(0x3b7be3), 100);
    retryButton.setPreferredSize(new Dimension(300,100));
    retryButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    //retryButton.setLocation(500,300);
    retryButton.setFont(new Font("Arial", Font.PLAIN, 30));
    // retryButton.setBounds(new Rectangle(1,1,1,1));

    gameoverLabel.setPreferredSize(new Dimension(600,200));
    gameoverLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    gameoverLabel.setFont(new Font("Arial", Font.PLAIN, 60));
    gameoverLabel.setForeground(Color.WHITE);

    gameoverPanel.add(gameoverLabel);
    gameoverPanel.add(retryButton);
    
    shopoverlay = new JPanel();
    shopoverlay.setLayout(null);
    shopoverlay.setBounds(0,0,width,height);
    // shopoverlay.setBounds(0,0,frame.getWidth(),frame.getHeight());
    shopoverlay.setOpaque(false);
        
    shopscore = new JLabel();
    shopscore.setText("100");
    shopscore.setFont(new Font("Arial", Font.PLAIN, 60));
    shopscore.setForeground(new Color(0xffffff));
    shopscore.setBounds(width / 2 - 250, 10, 500, 100);
    shopscore.setHorizontalAlignment(SwingConstants.CENTER);

    shopoverlay.add(shopscore);

    shop = new JPanel(new GridLayout(3,0,100,50));
    shop.setBackground(Color.DARK_GRAY);
    // shop.setBounds(0,0,frame.getWidth(),frame.getHeight());
    shop.setBounds(0,0,width,height);

    shopPanel = new JPanel();
    shopPanel.setLayout(null);
    
    shopPanel.add(shopoverlay);
    shopPanel.add(shop);
    
    video = new ShopItem(
      new JLabel("Video"),
      new RoundedButton("Purchase for 500 Points",new Color(0x3b7be3), 30),
      new JLabel("<html>Videos will get more engagement<br/> and will make likes fall more often</html>"),
      500
    );

    movement = new ShopItem(
      new JLabel("Movement Speed"),
      new RoundedButton("Purchase for 200 Points",new Color(0x3b7be3), 30),
      new JLabel("Makes you move faster"),
      200
    );

    likeratioup = new ShopItem(
      new JLabel("Like ratio"),
      new RoundedButton("Purchase for 450 Points",new Color(0x3b7be3), 30),
      new JLabel("<html>A better like ratio means<br/> more likes and less dislikes</html>"),
      450
    );


    shop.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
    shop.add(video.titlePanel);
    shop.add(movement.titlePanel);
    shop.add(likeratioup.titlePanel);
    shop.add(video.purchasePanel);
    shop.add(movement.purchasePanel);
    shop.add(likeratioup.purchasePanel);
    shop.add(video.descriptionPanel);
    shop.add(movement.descriptionPanel);
    shop.add(likeratioup.descriptionPanel);



    main.add(menuPanel, "Menu");
    main.add(this, "Game");
    main.add(gameoverPanel, "Gameover");
    main.add(shopPanel, "Shop");


    video.purchase.addActionListener((ActionEvent e) -> {
      if(score <= video.price) {
        shop.requestFocus();
        return;
      }
      score -= video.price;
      scoreLabel.setText(Integer.toString(score));
      video.price = (int)(1.2 * (double)video.price);
      video.purchase.setText("Purchase for " + video.price + "Points");
      spawnrate = (int)(0.8 * (double)spawnrate);
      int indexal = random(0, allowedindecies.size() - 1);
      int index = allowedindecies.get(indexal);
      allowedindecies.remove(indexal);
      if(allowedindecies.size() == 0) {
        for (int i = 0; i < videos.size(); i++) {
          allowedindecies.add(i);
        }
      }
      Memes.add(new Video(videos.get(index)));
      countvideos++;
      playSound(audiohash.get(videos.get(index)));
      layout.show(main, "Game");
      this.requestFocusInWindow();
      currScreen = "Game";
      inshop = false;
    });

    movement.purchase.addActionListener((ActionEvent e) -> {
      if(score <= movement.price) {
        shop.requestFocus();
        return;
      }
      score -= movement.price;
      scoreLabel.setText(Integer.toString(score));
      movement.price = (int)(1.5 * (double)movement.price);
      movement.purchase.setText("Purchase for " + movement.price + "Points");
      speed *= 1.05;
      layout.show(main, "Game");
      this.requestFocusInWindow();
      currScreen = "Game";
      inshop = false;
    });

    likeratioup.purchase.addActionListener((ActionEvent e) -> {
      if(score <= likeratioup.price) {
        shop.requestFocus();
        return;
      }
      score -= likeratioup.price;
      scoreLabel.setText(Integer.toString(score));
      likeratioup.price = (int)(1.2 * (double)likeratioup.price);
      likeratioup.purchase.setText("Purchase for " + likeratioup.price + "Points");
      likeratio += 1;
      layout.show(main, "Game");
      this.requestFocusInWindow();
      currScreen = "Game";
      inshop = false;
    });
    
    startButton.addActionListener((ActionEvent e) -> {

      
      score = 100;
      scoreLabel.setText(Integer.toString(score));

      String selectedDifficulty = (String) difficultyBox.getSelectedItem();
      System.out.println("Starte Spiel mit Schwierigkeit: " + selectedDifficulty);
      layout.show(main, "Game");
      this.requestFocusInWindow();
      currScreen = "Game";

      video.price = 500;
      video.purchase.setText("Purchase for 500 Points");
      movement.price = 200;
      movement.purchase.setText("Purchase for 200 Points");
      likeratioup.price = 450;
      likeratioup.purchase.setText("Purchase for 450 Points");
      new Thread(() -> {
        try {
            start(selectedDifficulty, layout, main, shop);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
      }).start();
    });

    retryButton.addActionListener((ActionEvent e) -> {
      Memes.clear();
      stopSounds();
      layout.show(main, "Menu");
    });

    frame.setContentPane(main);

    this.addKeyListener(new AL());
    shop.addKeyListener(new AL());

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(width,height);
    frame.setVisible(true);

    audiohash = new HashMap<ImageIcon, Clip>();

    loadTextures();
    System.out.println(videos.toString());
    audio = new ArrayList<Clip>();

    Memes = new ArrayList<Video>();
    allowedindecies = new ArrayList<Integer>();
    for(int i = 0; i < videos.size(); i++) {
      allowedindecies.add(i);
    }

    currScreen = "Menu";



  }

  public void playSound(String fileName) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    File soundFile = new File(fileName);
    AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
    Clip clip = AudioSystem.getClip();

    clip.open(audioStream);
    FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
    volumeControl.setValue(-10.0f); // Reduce volume
    clip.start(); // Start playback
    clip.loop(Clip.LOOP_CONTINUOUSLY);
    audio.add(clip);
  }

  public void playSound(Clip clip) {
    if(clip == null) {
      System.out.println("no soundfile to video");
      return;
    }
    FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
    volumeControl.setValue(-30.0f); // Reduce volume
    clip.start();
    clip.loop(Clip.LOOP_CONTINUOUSLY);
    audio.add(clip);
  }

  public Clip loadsound(String file) throws LineUnavailableException, IOException, UnsupportedAudioFileException{
    File soundFile = new File(file);
    AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
    Clip clip = AudioSystem.getClip();
    clip.open(audioStream);
    return clip;
  }

  public void stopSounds() {
    for (int i = 0; i < audio.size(); i++) {
      audio.get(i).stop();
      audio.get(i).close();
    }
    audio.clear();
  }

  public void loadTextures() {
    videos = new ArrayList<ImageIcon>();
    likes = new ArrayList<ImageIcon>();
    dislikes = new ArrayList<ImageIcon>();
    ArrayList<String> texturefolders = new ArrayList<String>(Stream.of(new File(".").listFiles())
      .filter(file -> file.isDirectory())
      .map(File::getName)
      .collect(Collectors.toList()));

    for(int i = 0; i < texturefolders.size(); i++) {
      ArrayList<String> skins =  new ArrayList<String>(Stream.of(
        new File(texturefolders.get(i)).listFiles()
      )
        .filter(file -> !file.isDirectory())
        .map(File::getName)
        .collect(Collectors.toList()));   

      System.out.println(skins.toString());

      if(skins.contains("index.conf")) {
        Properties prop = new Properties();
        try (
          FileInputStream fis = new FileInputStream(texturefolders.get(i) + "/" + "index.conf")
        ) { prop.load(fis); } 
        catch (IOException ex) {}
        
        if(!Boolean.parseBoolean(prop.getProperty("enabled")))
          return;

        for(String item : skins) {
          if(item.startsWith("v_") && Boolean.parseBoolean(prop.getProperty("video.enabled"))) 
          {
            ImageIcon video = new ImageIcon(texturefolders.get(i) + "/" + item);
            videos.add(video);
            try {
              Clip clip = loadsound(texturefolders.get(i) + "/s_" + item + ".wav");
              System.out.println(texturefolders.get(i) + "/s_" + item + ".wav");
              audiohash.put(video, clip);
            }catch(Exception e) {};
          }
          if(item.startsWith("l_") && Boolean.parseBoolean(prop.getProperty("likes.enabled"))) {
            likes.add(resize(120,120,texturefolders.get(i) + "/" + item));
          }
          if(item.startsWith("d_") && Boolean.parseBoolean(prop.getProperty("dislikes.enabled"))) {
            dislikes.add(resize(120,120,texturefolders.get(i) + "/" + item));
          }
          if(item.startsWith("p_") && Boolean.parseBoolean(prop.getProperty("player.enabled"))) {
            player.image = (resize(480,480,texturefolders.get(i) + "/" + item));
          }
          if(item.startsWith("o_") && Boolean.parseBoolean(prop.getProperty("gameover.enabled"))) {
            gameoverIcon = new ImageIcon(texturefolders.get(i) + "/" + item);
            try {
              Clip clip = loadsound(texturefolders.get(i) + "/s_" + item + ".wav");
              System.out.println(texturefolders.get(i) + "/s_" + item + ".wav");
              audiohash.put(gameoverIcon, clip);
            }catch(Exception e) {};
          }
        }
      }
    }
  }

  public ImageIcon resize(int x, int y, String file){
    ImageIcon image = new ImageIcon(file);
    Image imagetmp = image.getImage(); // transform it 
    Image newimg = imagetmp.getScaledInstance(x, y,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
    image = new ImageIcon(newimg);  // transform it back
    return image;
  }



  public static void main(String[] args) throws InterruptedException{
    System.out.println("LOL");
    Spiel spiel = new Spiel();
  }

  public void start(String difficulty, CardLayout layout, JPanel main, JPanel shop) throws InterruptedException{
    switch (difficulty) {
      case "Easy":
        likeratio = 6;
        fallspeed = 0.05f;
        speed = 20;
        spawnrate = 30;
        break;
      case "Medium":
        likeratio = 4;
        fallspeed = 0.05f;
        speed = 15;
        spawnrate = 20;
        break;
      case "Hard":
        likeratio = 2;
        fallspeed = 0.1f;
        speed = 10;
        spawnrate = 20;
        break;

      default:
        break;
    }
    player.x = 0;
    player.y = 0;
    player.speedx = 0;
    player.speedy = 0;
    objs.clear();
    countvideos = 0;
    resizeshop();
    while(true) {
      long start = System.nanoTime();
      if(score < 0)
        break;
      // System.out.println(player.x);
      //spiel.x -= 1;
      //Thread.sleep(10);
      if(inshop && !currScreen.equals("Shop")) {
        resizeshop();
        layout.show(main, "Shop");
        shop.requestFocus();
        currScreen = "Shop";
        shopscore.setText(Integer.toString(score));
        continue;
      } else if(!inshop && !currScreen.equals("Game")) {
        layout.show(main, "Game");
        this.requestFocus();
        currScreen = "Game";
      }
      if(!inshop) {
        tick();
        repaint();
      }
      long end;
      do {
        end = System.nanoTime();
      } while (end - start <= 10_000_000);
      String fpsstring = String.format("%.2f",1.0f/(((float)(end - start))/1000000000.0f));
      fpsLabel.setText("fps: " + fpsstring);
      boolean change = false;
      if(width != frame.getWidth()) {
        change = true;
        width = frame.getWidth();
      }
      if(height != frame.getHeight()) {
        change = true;
        height = frame.getHeight();
      }
      shop.setBounds(0,0,width, height);    
      shopoverlay.setBounds(0,0,width,height);
      scoreLabel.setBounds(width / 2 - 250, 10, 500, 100);
      if(inshop && change)
        resizeshop();
    } 
    try {
      stopSounds();
      playSound(audiohash.get(gameoverIcon));
    } catch(Exception e) {}
    layout.show(main, "Gameover");
  }

  private void resizeshop() {
    ShopItem[] items = {video, movement, likeratioup};
    for (ShopItem shopItem : items) {
      shopItem.title.setBorder(BorderFactory.createEmptyBorder(
        (height / 6), 0, 0, 0
      ));
      shopItem.title.setFont(new Font(
        "Arial", Font.PLAIN, width / 30
      ));
      shopItem.purchase.setFont(new Font(
        "Arial", Font.PLAIN, width / 50
      ));
      shopItem.description.setFont(new Font(
        "Arial", Font.PLAIN, width / 70
      ));
      shopItem.purchase.setPreferredSize(
        new Dimension(width / 4,height / 4)
      );
      shopItem.purchasePanel.revalidate();
    }
    shopscore.setBounds(width / 2 - 250, 10, 500, 100);
    shopoverlay.setDoubleBuffered(true);
    shopoverlay.revalidate();
    shop.revalidate();
    shopPanel.revalidate();
    shopoverlay.repaint();

  };

  @Override
  protected void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    // background.paintIcon(this, g, 0, 0);
    for(int i = 0; i < countvideos; i++) {
      Memes.get(i).video.paintIcon(this, g, Memes.get(i).x, Memes.get(i).y);
    }
    player.image.paintIcon(this, g, player.x, player.y);
    if(objs.size() == 0)
      return;
    for(Stuff stuff : objs) {
      stuff.image.paintIcon(this, g,(int)stuff.x,(int)stuff.y);
    }
  }

  public int random(int from, int till) {
    return (int)Math.round((till - from)*Math.random()) + from;
  }

  public void tick() {
    timer++;
    if(timer > spawnrate) {
      if(random(1, likeratio) % likeratio == 0)
        objs.add(new Stuff(
          random(0, width - 120),
          -120,
          dislikes.get(random(0, dislikes.size() - 1)),
          -50
        ));
      else
        objs.add(new Stuff(
          random(0, width - 120),
          -120,
          likes.get(random(0, dislikes.size() - 1)),
          10
        ));
      timer = 0;

    }
    if(objs.size() != 0) {
      for(int i = 0; i < objs.size(); i++) {
        objs.get(i).speedy += fallspeed;
        objs.get(i).y += objs.get(i).speedy;
        if(objs.get(i).y > height)
          objs.remove(i);
        if(AABB(objs.get(i))){
          score += objs.get(i).score;
          scoreLabel.setText(Integer.toString(score));
          if(score >= speedupsAt[speeduptotal]) {
            fallspeed *= 2.0f;
            speeduptotal++;
          }
          objs.remove(i);
        }
        
      }
    }
    player.x += player.speedx;
    player.y += player.speedy;
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

  public boolean AABB(Stuff stuff) {
    if(stuff.x + 120 > player.x && stuff.y + 120 > player.y && stuff.x < player.x + 480 && stuff.y < player.y + 480)
      return true;
    return false;
  }

  public class AL extends KeyAdapter {

    @Override
    public void keyPressed(KeyEvent event) {
      int keyCode = event.getKeyCode();
      if (keyCode == event.VK_LEFT || keyCode == event.VK_A)
      {
        player.speedx = -speed;
      }
      if (keyCode == event.VK_RIGHT || keyCode == event.VK_D)
      {
        player.speedx = speed;
      }
      if (keyCode == event.VK_SPACE && canjump)
      {
        player.speedy = -20;
        canjump = false;
      }
      if (keyCode == event.VK_B) 
      {
        inshop = inshop ? false : true;
      }
      if (keyCode == event.VK_P) 
      {
        score += 1000;
      }
    }

    @Override
    public void keyReleased(KeyEvent event) {
      int keyCode = event.getKeyCode();
      if (keyCode == event.VK_LEFT || keyCode == event.VK_A)
      {
        player.speedx = 0;
      }
      if (keyCode == event.VK_RIGHT || keyCode == event.VK_D)
      {
        player.speedx = 0;
      }
    }
  }
}

