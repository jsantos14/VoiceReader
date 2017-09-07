import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;
   
// To play sound using Clip, the process need to be alive.
// Hence, we use a Swing application.
public class SoundClipTest extends JFrame {
   
   // Constructor
   public SoundClipTest() {
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setTitle("Test Sound Clip");
      this.setSize(300, 200);
      this.setVisible(true);

      AudioParser test = new AudioParser();

      try {
         // Open an audio input stream.
         File soundFile = new File("C:\\Users\\Ryan\\Music\\Sounds\\hello.wav");
         test.addAudio(soundFile);
         soundFile = new File("C:\\Users\\Ryan\\Music\\Sounds\\this.wav");
         test.addAudio(soundFile);
         soundFile = new File("C:\\Users\\Ryan\\Music\\Sounds\\is.wav");
         test.addAudio(soundFile);
         soundFile = new File("C:\\Users\\Ryan\\Music\\Sounds\\a.wav");
         test.addAudio(soundFile);
         soundFile = new File("C:\\Users\\Ryan\\Music\\Sounds\\test.wav");
         test.addAudio(soundFile);

         // Get a sound clip resource.
         Clip clip = test.getAudio();

         // Open audio clip and load samples from the audio input stream.
         //clip.open(appendedFiles);
         clip.start();
         
      } catch (UnsupportedAudioFileException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (LineUnavailableException e) {
         e.printStackTrace();
      }
   }
   
   public static void main(String[] args) {
      new SoundClipTest();
   }
}