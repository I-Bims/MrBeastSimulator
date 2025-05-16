# Mr Beast Simulator
Just a silly little game about content farming with MrBeast.<br/>
It also has texture packs if you can figure it out.

## Building from Source
1. Go to the main branch then download it using git or just as a zip.<br/>
`git clone https://github.com/I-Bims/MrBeastSimulator.git`
2. You need to download the textures seperatly unfortunally because the files are too big.<br/>
   To do that you need to go to this link: [Textures](https://drive.google.com/file/d/1FSBybNQ0Ml9k5qRybyvk65wR-m6pqJnL/view?usp=sharing)<br/>
   then place the unzipped folder with all the other files like this:

```
  ├── build.sh
  ├── default
  │   ├── d_dislike.png
  │   ├── index.conf
  .   ├── l_like.png
  .   ├── o_hopeless.gif
  .
  ├── Dislike.java
  ├── Game.java
  ├── GameObject.java
  ├── GamePanel.java
  ├── Input.java
  ├── Like.java
  ├── Meme.java
  ├── Player.java
  ├── README.md
  ├── RoundedButton.java
  ├── ShopItem.java
  ├── Ui.java
  └── Util.java
   ```
4. Then install java I haven't testet many versions but 21 and 24 work fine
5. Next run these commands:
   ```
   javac *.java
   java -cp . Game
   ```
6. Your done! Hope it works well :)

## Installation
Or you could just download `MrBeast.jar` from the releases. Then execute it with java. Textures are included in jar file!
