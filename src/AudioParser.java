import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;

/**
 * manages the audio parsing by combining different wave files
 */
public class AudioParser {
    private AudioInputStream _clip;

    /**
     * Initializes an AudioParser
     */
    public AudioParser() {
        _clip = null;
    }

    /**
     * Adds the given wave file to stored audio clip
     *
     * @param file
     *      A valid wave file
     */
    public void addAudio(File file) throws IOException, UnsupportedAudioFileException {
        AudioInputStream audio2;

        // if the clip does not already exist set it
        if (_clip == null)
            _clip = AudioSystem.getAudioInputStream(file);

        // otherwise append the new clip to the old clip
        else {
            audio2 = AudioSystem.getAudioInputStream(file);
            _clip = new AudioInputStream(new SequenceInputStream(_clip, audio2), _clip.getFormat(),
                                                            _clip.getFrameLength() + audio2.getFrameLength());
        }
    }

    /**
     * Gets the current audio clip
     *
     * @return
     *      The current audio clip ready to be played
     * @throws IOException
     * @throws LineUnavailableException
     */
    public Clip getAudio() throws IOException, LineUnavailableException {
        Clip clip = AudioSystem.getClip();

        // Open audio clip and load samples from the audio input stream.
        clip.open(_clip);

        return clip;
    }
}
