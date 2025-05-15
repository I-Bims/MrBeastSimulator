import java.awt.event.*;
public class Input extends KeyAdapter {

  private Player player;

  public Input(Player player) {
    this.player = player;
  }


  @Override
  public void keyPressed(KeyEvent event) {
    int keyCode = event.getKeyCode();
    if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A)
    {
      player.move(-Game.playerspeed);
    }
    if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D)
    {
      player.move(Game.playerspeed);
    }
    if (keyCode == KeyEvent.VK_SPACE && Game.canjump)
    {
      player.jump();
      Game.canjump = false;
    }
    if (keyCode == KeyEvent.VK_B) 
    {
      Game.inshop = Game.inshop ? false : true;
    }
    // if (keyCode == event.VK_P) 
    // {
    //   score += 1000;
    // }
  }

  @Override
  public void keyReleased(KeyEvent event) {
    int keyCode = event.getKeyCode();
    if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A)
    {
      player.stop();
    }
    if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D)
    {
      player.stop();
    }
  }
}
