package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		// Erstellt das Hauptfenster des Spiels und initialisiert Komponenten
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("B/R MAZE Update 4");

		// Erstellt ein GamePanel und ein Menu-Objekt
		GamePanel gamePanel = new GamePanel();
		Menu menu = new Menu(gamePanel);
		
		// Setzt das Menu-Objekt im GamePanel
		gamePanel.setMenu(menu);
		
		// Fügt das Menu-Objekt und das GamePanel dem Fenster hinzu
		window.add(menu);
		window.add(gamePanel);

		// Fenstergröße anpassen und zentrieren
		window.pack();
		window.setLocationRelativeTo(null);
		
		// Fenster sichtbar machen
		window.setVisible(true);

		// Startet den Spielthread im GamePanel
		gamePanel.startGameThread();
	}

}