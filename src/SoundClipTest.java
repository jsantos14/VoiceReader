import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;
   
// To play sound using Clip, the process need to be alive.
// Yeah
// Hence, we use a Swing application.
@SuppressWarnings("serial")
public class SoundClipTest extends JFrame {
   
   // Constructor
   public SoundClipTest() {
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setTitle("Test Sound Clip");
      this.setSize(300, 200);
      this.setVisible(true);
   
      try {
         // Open an audio input stream.
         File soundFile = new File(
                              "C:\\Users\\Ryan\\Dropbox\\Junior Fall Semester"
                              + "\\Eclipse\\Audio\\DeJager.wav");
         AudioInputStream audio1 = AudioSystem.getAudioInputStream(soundFile);
         AudioInputStream audio2 = AudioSystem.getAudioInputStream(soundFile);
         
         // combine the WAV file 
         AudioInputStream appendedFiles = 
               new AudioInputStream(
                   new SequenceInputStream(audio1, audio2),     
                   audio1.getFormat(), 
                   audio1.getFrameLength() + audio2.getFrameLength());
         
         // Get a sound clip resource.
         Clip clip = AudioSystem.getClip();
         
         // Open audio clip and load samples from the audio input stream.
         clip.open(appendedFiles);
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