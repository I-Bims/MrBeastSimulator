import java.util.ArrayList;
import java.awt.Image;
import java.io.*;
import java.util.stream.*;
import java.util.*;
import javax.sound.sampled.*;
import java.net.URL;
import java.util.jar.*;

import javax.swing.ImageIcon;

public class Util {

  public static ArrayList<Clip> audio = new ArrayList<Clip>();
  
  public static void loadTextures(
    ArrayList<ImageIcon> videos,
    ArrayList<ImageIcon> likes,
    ArrayList<ImageIcon> dislikes,
    HashMap<ImageIcon, Clip> audiohash,
    ArrayList<ImageIcon> player,
    ArrayList<ImageIcon> gameoverIcon
  ) 
  {

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
            player.add(resize(480,480,texturefolders.get(i) + "/" + item));
            System.out.println("playerIcon: " + item + " " + player.get(0));
          }
          if(item.startsWith("o_") && Boolean.parseBoolean(prop.getProperty("gameover.enabled"))) {
            gameoverIcon.add(new ImageIcon(texturefolders.get(i) + "/" + item));
            try {
              Clip clip = loadsound(texturefolders.get(i) + "/s_" + item + ".wav");
              System.out.println(texturefolders.get(i) + "/s_" + item + ".wav");
              audiohash.put(gameoverIcon.get(0), clip);
            }catch(Exception e) {};
          }
        }
      }
    }
  }

  public static void loadTexturesJar(
    ArrayList<ImageIcon> videos,
    ArrayList<ImageIcon> likes,
    ArrayList<ImageIcon> dislikes,
    HashMap<ImageIcon, Clip> audiohash,
    ArrayList<ImageIcon> player,
    ArrayList<ImageIcon> gameoverIcon
) {
    try {
        // Use ClassLoader to access resources inside JAR
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL texturesFolder = classLoader.getResource("textures/");

        if (texturesFolder == null) {
            System.out.println("Textures folder not found in JAR.");
            return;
        }

      // If you need to list files inside JAR, use JarFile approach
      String jarPath = texturesFolder.toURI().toString().split("!")[0].replace("jar:file:", "");
      try (JarFile jarFile = new JarFile(jarPath)) {
        Enumeration<JarEntry> entries = jarFile.entries();

        while (entries.hasMoreElements()) {
          JarEntry entry = entries.nextElement();
          String item = entry.getName();
          System.out.println(item);

          if (item.startsWith("textures/")) {
            if(item.startsWith("textures/v_")) 
            {
              if(item.endsWith(".gif") || item.endsWith(".png") || item.endsWith(".jpg")) {
                ImageIcon video = new ImageIcon(classLoader.getResource(item));
                videos.add(video);

                try {
                  Clip clip = loadsound(classLoader.getResource(item.replace("v_", "s_v_").replace(".gif", ".gif.wav")));
                  System.out.println(item + " SoundLoaded!");
                  audiohash.put(video, clip);
                }catch(Exception e) {};
              }
            }
            if(item.startsWith("textures/l_")) {
              likes.add(resize(120,120,classLoader.getResource(item)));
            }
            if(item.startsWith("textures/d_")) {
              dislikes.add(resize(120,120,classLoader.getResource(item)));
            }
            if(item.startsWith("textures/p_")) {
              player.add(resize(480,480,classLoader.getResource(item)));
              System.out.println("playerIcon: " + item + " " + player.get(0));
            }
            if(item.startsWith("textures/o_")) {
              gameoverIcon.add(new ImageIcon(classLoader.getResource(item)));
              try {
                Clip clip = loadsound(classLoader.getResource(item.replace("o_", "s_o_").replace(".gif", ".gif.wav")));
                System.out.println(item + "SoundLoaded");
                audiohash.put(gameoverIcon.get(0), clip);
              }catch(Exception e) {};
            }
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static Clip loadsound(String file) throws LineUnavailableException, IOException, UnsupportedAudioFileException{
    File soundFile = new File(file);
    AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
    Clip clip = AudioSystem.getClip();
    clip.open(audioStream);
    return clip;
  }
  public static Clip loadsound(URL file) throws LineUnavailableException, IOException, UnsupportedAudioFileException{
    AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
    Clip clip = AudioSystem.getClip();
    clip.open(audioStream);
    return clip;
  }
  public static void playSound(Clip clip) {
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
  


  public static void stopSounds() {
    for (int i = 0; i < audio.size(); i++) {
      audio.get(i).stop();
      audio.get(i).close();
    }
    audio.clear();
  }

  public static ImageIcon resize(int x, int y, String file){
    ImageIcon image = new ImageIcon(file);
    Image imagetmp = image.getImage(); // transform it 
    Image newimg = imagetmp.getScaledInstance(x, y,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
    image = new ImageIcon(newimg);  // transform it back
    return image;
  }
  public static ImageIcon resize(int x, int y, URL file){
    ImageIcon image = new ImageIcon(file);
    Image imagetmp = image.getImage(); // transform it 
    Image newimg = imagetmp.getScaledInstance(x, y,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
    image = new ImageIcon(newimg);  // transform it back
    return image;
  }


  public static int random(int from, int till) {
    return (int)Math.round((till - from)*Math.random()) + from;
  }



  public static boolean AABB(GameObject g1, GameObject g2) {
    if(g1.x + g1.width > g2.x && g1.y + g1.height > g2.y && g1.x < g2.x + g2.width && g1.y < g2.y + g2.height)
    return true;
    return false;
  }
  public static boolean isRunningInJar() {
    try {
      String location = Util.class.getProtectionDomain().getCodeSource().getLocation().toString();
      return location.endsWith(".jar");
    } catch (Exception e) {
      return false; // If detection fails, assume not inside a JAR
    }
  }
}
