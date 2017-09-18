import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.format.TextStyle;
import java.util.ArrayList;

/**
 * Graphical User Interface that the user will be interacting with.
 */
public class GUI extends JPanel
{
	private final JFrame frame;

	public GUI()
	{
		VoiceRecorder voiceRecorder = new VoiceRecorder();
		Thread voiceRecorderThread = new Thread(voiceRecorder);

		frame = new JFrame("VoiceReader");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(3, 3));

		JButton startButton = newButton("Start Recording");
		startButton.addActionListener(event ->
		{
			voiceRecorderThread.start();

		});

		JButton stopButton = newButton("Stop Recording");
		stopButton.addActionListener(event ->
		{
			voiceRecorder.finish();
		});

		JButton importTextButton = newButton("Import Text");
		importTextButton.addActionListener(event ->
		{
			FileDialog fileDialog = new FileDialog(frame);
			fileDialog.setVisible(true);
			String directoryPath = fileDialog.getDirectory();
			String filePath = fileDialog.getFile();
			String absFilePath = directoryPath + filePath;

			System.out.println(absFilePath);

			if ( absFilePath != null )
			{
				try
				{
					ArrayList<String> listOfWords = TextFileParser.parseText
							(absFilePath);

					AudioParser audioParser = new AudioParser();

					JFrame newImageFrame = new JFrame();
					JLabel imageHolder = new JLabel(new ImageIcon
							("DejagerMaistro.jpg"));
					newImageFrame.add(imageHolder);
					newImageFrame.setSize(imageHolder.getPreferredSize());
					newImageFrame.setVisible(true);

					for ( String word : listOfWords )
					{
						for ( File file : Pronunciation.wordToFile(word) )
						{
							try
							{
								audioParser.addAudio(file);
							}
							catch (UnsupportedAudioFileException e)
							{
								e.printStackTrace();
							}
						}
					}

					Clip clip;

					try
					{
						clip = audioParser.getAudio();
						clip.start();
					}
					catch (LineUnavailableException e)
					{
						e.printStackTrace();
					}



				}
				catch (IOException ex)
				{
					JOptionPane.showMessageDialog(null, "You idiot!" + ex
							.getMessage());
				}
			}


		});

		JButton runProgramButton = newButton("Dejagerify!");
		runProgramButton.addActionListener(event ->
		{

		});

		JLabel welcomeLabel = new JLabel("Welcome to the best app youll ever "
				+ "use!");
		welcomeLabel.setFont(new Font("Times new roman", Font.BOLD, 12));

		frame.add(welcomeLabel);
		frame.add(startButton);
		frame.add(stopButton);
		frame.add(importTextButton);
		frame.add(runProgramButton);
		frame.setSize(350, 350);

		SwingUtilities.invokeLater(() -> frame.setVisible(true));
	}

	public static JButton newButton (String title)
	{
		JLabel buttonLabel = new JLabel(title);
		JButton button = new JButton(title);
		button.setPreferredSize(buttonLabel.getPreferredSize());

		return button;
	}

	public static void main (String[] args)
	{
		GUI gui = new GUI();
	}

}
