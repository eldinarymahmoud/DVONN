package eg.edu.guc.dvonn.gui;

import javax.swing.*;
import java.io.*;
import javax.sound.sampled.*;

@SuppressWarnings("serial")
public class soundApplet extends JFrame {
	AudioFormat audioFormat;
	AudioInputStream audioInputStream;
	SourceDataLine sourceDataLine;
	boolean stopPlayback = false;
	final JButton stopBtn = new JButton("Stop");
	final JButton playBtn = new JButton("Play");
	String sound1;

	public soundApplet(String sound) {
		sound1 = sound;
	}

	public void playAudio() {
		try {
			File soundFile = new File(sound1);
			audioInputStream = AudioSystem.getAudioInputStream(soundFile);
			audioFormat = audioInputStream.getFormat();
			System.out.println(audioFormat);

			DataLine.Info dataLineInfo = new DataLine.Info(
					SourceDataLine.class, audioFormat);

			sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);

			new PlayThread().start();// call the PlayThread class
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}// end catch
	}

	class PlayThread extends Thread {
		byte tempBuffer[] = new byte[10000];

		public void run() {
			try {
				sourceDataLine.open(audioFormat);
				sourceDataLine.start();
				int cnt;

				while ((cnt = audioInputStream.read(tempBuffer, 0,
						tempBuffer.length)) != -1 && stopPlayback == false) {
					if (cnt > 0) {

						sourceDataLine.write(tempBuffer, 0, cnt);
					}// end if
				}
				sourceDataLine.drain();
				sourceDataLine.close();
				stopBtn.setEnabled(false);
				playBtn.setEnabled(true);
				stopPlayback = false;
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			}// end catch
		}// end run
	}// end inner class PlayThread
}