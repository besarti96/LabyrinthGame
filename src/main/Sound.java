package main;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

	Clip clip; // Der Clip, der den Sound abspielt
	URL soundURL[] = new URL[30]; // Array zur Speicherung der Sound-URLs
	
	public Sound() {
		// Initialisierung der Sound-URLs
		soundURL[0] = getClass().getResource ("/sound/2DGameMusic.wav");
		soundURL[1] = getClass().getResource ("/sound/VerlorenSound.wav");
		soundURL[2] = getClass().getResource ("/sound/happy-14585.wav");
		soundURL[3] = getClass().getResource ("/sound/GewonnenSound.wav");
	}
	
		public void setFile(int i) {
			
			try {
				 // Lädt den Sound von der entsprechenden URL
				AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
				clip = AudioSystem.getClip();
				clip.open(ais);
				
			}catch(Exception e) {
				// Behandelt jegliche auftretende Ausnahme hier
			}
	}
		public void play() {
			// Startet das Abspielen des Sounds
			clip.start();
			
		}
		public void loop() {
			// Startet das Looping des Sounds
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			
		}
		
		public void stop() {
			// Stoppt das Abspielen des Sounds
			clip.stop();
			
		}

}

