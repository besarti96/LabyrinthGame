package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class keyHandler implements KeyListener{
	
	public boolean upPressed, downPressed, leftPressed, rightPressed;
	public boolean upArrowPressed, downArrowPressed, leftArrowPressed, rightArrowPressed;

	public void keyTyped(KeyEvent e) {
		// Nicht relevant für diese Implementierung
	}

	public void keyPressed(KeyEvent e) {
		// Behandelt das Drücken einer Taste
		
		int code = e.getKeyCode();
		
		// Überprüft den KeyCode der gedrückten Taste und setzt die entsprechenden Zustandsvariablen auf true
		
		// WASD-Tasten
		if(code == KeyEvent.VK_W){
			upPressed = true;
		}
		if(code == KeyEvent.VK_S){
			downPressed = true;
		}
		if(code == KeyEvent.VK_A){
			leftPressed = true;
		}
		if(code == KeyEvent.VK_D){
			rightPressed = true;
		}
		
		// Pfeiltasten
		if(code == KeyEvent.VK_UP){
			upArrowPressed = true;
		}
		if(code == KeyEvent.VK_DOWN){
			downArrowPressed = true;
		}
		if(code == KeyEvent.VK_LEFT){
			leftArrowPressed = true;
		}
		if(code == KeyEvent.VK_RIGHT){
			rightArrowPressed = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		// Behandelt das Loslassen einer Taste
		
		int code = e.getKeyCode();
		
		// Überprüft den KeyCode der losgelassenen Taste und setzt die entsprechenden Zustandsvariablen auf false
		
		// WASD-Tasten
		if(code == KeyEvent.VK_W){
			upPressed = false;
		}
		if(code == KeyEvent.VK_S){
			downPressed = false;
		}
		if(code == KeyEvent.VK_A){
			leftPressed = false;
		}
		if(code == KeyEvent.VK_D){
			rightPressed = false;
		}
		
		// Pfeiltasten
		if(code == KeyEvent.VK_UP){
			upArrowPressed = false;
		}
		if(code == KeyEvent.VK_DOWN){
			downArrowPressed = false;
		}
		if(code == KeyEvent.VK_LEFT){
			leftArrowPressed = false;
		}
		if(code == KeyEvent.VK_RIGHT){
			rightArrowPressed = false;
		}
	}

}
