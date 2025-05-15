import javax.swing.*;
import java.awt.*;

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
