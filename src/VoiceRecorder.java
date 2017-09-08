import javax.sound.sampled.*;
import javax.swing.*;
import java.io.*;

/**
 * A sample program is to demonstrate how to record sound in Java author:
 * www.codejava.net
 */
public class VoiceRecorder implements Runnable
{
	// record duration, in milliseconds
	static final long RECORD_TIME = 60000;  // 1 minute

	String filePath = System.getProperty("user.home") +
			"/IdeaProjects/VoiceReader/src/Sounds";

	// path of the wav file
	File wavFile = new File(filePath + "/RecordedAudio.wav");

	// format of audio file
	AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;

	// the line from which audio data is captured
	TargetDataLine line;

	/**
	 * Defines an audio format
	 */
	AudioFormat getAudioFormat()
	{
		float sampleRate = 16000;
		int sampleSizeInBits = 8;
		int channels = 2;
		boolean signed = true;
		boolean bigEndian = true;
		AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
				channels, signed, bigEndian);
		return format;
	}

	/**
	 * Captures the sound and record into a WAV file
	 */
	public void run()
	{
		try
		{
			AudioFormat format = getAudioFormat();
			DataLine.Info info =
					new DataLine.Info(TargetDataLine.class, format);

			// checks if system supports the data line
			if (!AudioSystem.isLineSupported(info))
			{
				JOptionPane.showMessageDialog(null, "Line is not supported."
						+ " ");
			}
			line = (TargetDataLine) AudioSystem.getLine(info);
			line.open(format);
			line.start();   // start capturing

			System.out.println("Start capturing...");

			AudioInputStream ais = new AudioInputStream(line);

			System.out.println("Start recording...");

			// start recording
			AudioSystem.write(ais, fileType, wavFile);
		}
		catch (LineUnavailableException ex)
		{
			ex.printStackTrace();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
	}

	/**
	 * Closes the target data line to finish capturing and recording
	 */
	public void finish()
	{
		System.out.println("Stopped recording");
		line.stop();
		line.close();
		Thread.currentThread().interrupt();
	}

	/**
	 * Entry to run the program
	 */
	public static void main( String[] args )
	{
		String filePath = System.getProperty("user.home") +
				"\\IdeaProjects\\VoiceReader\\src";

		System.out.println(filePath);

		/*final VoiceRecorder recorder = new VoiceRecorder();

		// creates a new thread that waits for a specified
		// of time before stopping
		Thread stopper = new Thread(() ->
		{
			try
			{
				Thread.sleep(RECORD_TIME);
			}
			catch (InterruptedException ex)
			{
				ex.printStackTrace();
			}
			recorder.finish();
		});

		stopper.start();

		// start recording
		recorder.start();*/
	}
}