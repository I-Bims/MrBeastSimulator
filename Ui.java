import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
class Ui {
  
  public JFrame frame;
  public JLabel scoreLabel,shopscore, fpsLabel;
  public ArrayList<ImageIcon> gameoverIcon;
  public JPanel shopoverlay, shop, shopPanel, main, gamePanel;
  public ShopItem video, movement, likeratioup;
  public String currScreen;
  public CardLayout layout;

  private ArrayList<Integer> allowedindecies;
  private Game game;


  public Ui(JPanel gamePanel, Game game) {
    this.game = game;
    this.gamePanel = gamePanel;
    
    gameoverIcon = new ArrayList<ImageIcon>();

    frame = new JFrame("Game");
    layout = new CardLayout();
    main = new JPanel(layout);

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

    gamePanel.setBackground(Color.DARK_GRAY);
    scoreLabel = new JLabel();
    //scoreLabel.setPreferredSize(new Dimension(500,100));
    scoreLabel.setText("100");
    scoreLabel.setFont(new Font("Arial", Font.PLAIN, 60));
    scoreLabel.setForeground(new Color(0xffffff));
    scoreLabel.setBounds(Game.width / 2 - 250, 10, 500, 100);
    scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);


    fpsLabel = new JLabel();
    //fpsLabel.setPreferredSize(new Dimension(500,100));
    fpsLabel.setText("0");
    fpsLabel.setFont(new Font("Arial", Font.PLAIN, 30));
    fpsLabel.setForeground(Color.lightGray);
    fpsLabel.setBounds(10, 10, 500, 50);
    //fpsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    gamePanel.setLayout(null);
    gamePanel.add(fpsLabel);
    gamePanel.add(scoreLabel);

    JPanel gameoverPanel = new JPanel() {
      @Override
      protected void paintComponent(Graphics g)
      {
        super.paintComponent(g);
        gameoverIcon.get(0).paintIcon(this, g, Game.width/2 - gameoverIcon.get(0).getIconWidth()/2, -400);
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
    shopoverlay.setBounds(0,0,Game.width,Game.height);
    // shopoverlay.setBounds(0,0,frame.getWidth(),frame.getHeight());
    shopoverlay.setOpaque(false);

    shopscore = new JLabel();
    shopscore.setText("100");
    shopscore.setFont(new Font("Arial", Font.PLAIN, 60));
    shopscore.setForeground(new Color(0xffffff));
    shopscore.setBounds(Game.width / 2 - 250, 10, 500, 100);
    shopscore.setHorizontalAlignment(SwingConstants.CENTER);

    shopoverlay.add(shopscore);

    shop = new JPanel(new GridLayout(3,0,100,50));
    shop.setBackground(Color.DARK_GRAY);
    // shop.setBounds(0,0,frame.getWidth(),frame.getHeight());
    shop.setBounds(0,0,Game.width,Game.height);

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
    main.add(gamePanel, "Game");
    main.add(gameoverPanel, "Gameover");
    main.add(shopPanel, "Shop");


    video.purchase.addActionListener((ActionEvent e) -> {
      if(Game.score <= video.price) {
        shop.requestFocus();
        return;
      }
      if(allowedindecies == null)
        allowedindecies = new ArrayList<Integer>();
      if(allowedindecies.size() == 0) {
        for (int i = 0; i < Game.videos.size(); i++) {
          allowedindecies.add(i);
        }
      }
      Game.score -= video.price;
      scoreLabel.setText(Integer.toString(Game.score));
      video.price = (int)(1.2 * (double)video.price);
      video.purchase.setText("Purchase for " + video.price + "Points");
      game.spawnrate = (int)(0.8 * (double)Game.spawnrate);
      int indexal = Util.random(0, allowedindecies.size() - 1);
      int index = allowedindecies.get(indexal);
      allowedindecies.remove(indexal);
      Game.Memes.add(new Meme(Game.videos.get(index), Game.Memes));
      Util.playSound(Game.audio.get(Game.videos.get(index)));
      layout.show(main, "Game");
      gamePanel.requestFocusInWindow();
      currScreen = "Game";
      game.inshop = false;
    });

    movement.purchase.addActionListener((ActionEvent e) -> {
      if(game.score <= movement.price) {
        shop.requestFocus();
        return;
      }
      game.score -= movement.price;
      scoreLabel.setText(Integer.toString(game.score));
      movement.price = (int)(1.2 * (double)movement.price);
      movement.purchase.setText("Purchase for " + movement.price + "Points");
      game.playerspeed *= 1.1;
      layout.show(main, "Game");
      gamePanel.requestFocusInWindow();
      currScreen = "Game";
      game.inshop = false;
    });

    likeratioup.purchase.addActionListener((ActionEvent e) -> {
      if(game.score <= likeratioup.price) {
        shop.requestFocus();
        return;
      }
      game.score -= likeratioup.price;
      scoreLabel.setText(Integer.toString(game.score));
      likeratioup.price = (int)(1.2 * (double)likeratioup.price);
      likeratioup.purchase.setText("Purchase for " + likeratioup.price + "Points");
      game.likeratio += 1;
      layout.show(main, "Game");
      gamePanel.requestFocusInWindow();
      currScreen = "Game";
      game.inshop = false;
    });

    startButton.addActionListener((ActionEvent e) -> {


      game.score = 100;
      scoreLabel.setText(Integer.toString(game.score));

      String selectedDifficulty = (String) difficultyBox.getSelectedItem();
      layout.show(main, "Game");
      gamePanel.requestFocusInWindow();
      currScreen = "Game";

      video.price = 500;
      video.purchase.setText("Purchase for 500 Points");
      movement.price = 200;
      movement.purchase.setText("Purchase for 200 Points");
      likeratioup.price = 450;
      likeratioup.purchase.setText("Purchase for 450 Points");
      new Thread(() -> {
        try {
          game.start(selectedDifficulty,this);
        } catch (InterruptedException ex) {
          ex.printStackTrace();
        }
      }).start();
    });

    retryButton.addActionListener((ActionEvent e) -> {
      game.Memes.clear();
      Util.stopSounds();
      layout.show(main, "Menu");
    });

    frame.setContentPane(main);

    gamePanel.addKeyListener(new Input(game.player));
    shop.addKeyListener(new Input(game.player));

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(Game.width,Game.height);
    frame.setVisible(true);
  }


  public void resizeshop() {
    shop.setBounds(0,0,Game.width, Game.height);    
    shopoverlay.setBounds(0,0,Game.width,Game.height);
    scoreLabel.setBounds(Game.width / 2 - 250, 10, 500, 100);
    ShopItem[] items = {video, movement, likeratioup};
    for (ShopItem shopItem : items) {
      shopItem.title.setBorder(BorderFactory.createEmptyBorder(
      (Game.height / 6), 0, 0, 0
      ));
      shopItem.title.setFont(new Font(
        "Arial", Font.PLAIN, Game.width / 30
      ));
      shopItem.purchase.setFont(new Font(
        "Arial", Font.PLAIN, Game.width / 50
      ));
      shopItem.description.setFont(new Font(
        "Arial", Font.PLAIN, Game.width / 70
      ));
      shopItem.purchase.setPreferredSize(
        new Dimension(Game.width / 4,Game.height / 4)
      );
      shopItem.purchasePanel.revalidate();
    }
    shopscore.setBounds(Game.width / 2 - 250, 10, 500, 100);
    shopoverlay.setDoubleBuffered(true);
    shopoverlay.revalidate();
    shop.revalidate();
    shopPanel.revalidate();
    shopoverlay.repaint();

  };
  public void setShop(boolean inshop) {
    if(inshop && !currScreen.equals("Shop")) {
      resizeshop();
      layout.show(main, "Shop");
      shop.requestFocus();
      currScreen = "Shop";
      shopscore.setText(Integer.toString(game.score));
      return;
    } else if(!inshop && !currScreen.equals("Game")){
      layout.show(main, "Game");
      gamePanel.requestFocus();
      currScreen = "Game";
    }
  }

  public void showGameOver() {
    try {
      Util.stopSounds();
      Util.playSound(Game.audio.get(gameoverIcon.get(0)));
    } catch(Exception e) {}
    layout.show(main, "Gameover");
  }
}
