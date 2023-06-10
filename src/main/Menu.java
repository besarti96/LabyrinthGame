package main;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Menu extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField player1NameField;
    private JTextField player2NameField;
    private BufferedImage backgroundImage;
    
    // Erstellt und platziert Swing-Komponenten auf dem JPanel

    // Label und Textfeld für Spieler 1
    public Menu(final GamePanel gamePanel) {
        setLayout(null);

        JLabel player1Label = new JLabel("Player 1:");
        player1Label.setBounds(90, 90, 100, 20);
        add(player1Label);

        player1NameField = new JTextField();
        player1NameField.setBounds(160, 90, 150, 20);
        add(player1NameField);

        // Label und Textfeld für Spieler 2
        JLabel player2Label = new JLabel("Player 2:");
        player2Label.setBounds(460, 90, 100, 20);
        add(player2Label);

        player2NameField = new JTextField();
        player2NameField.setBounds(530, 90, 150, 20);
        add(player2NameField);

        // Animationselemente

        // JLabels für Spielerbilder
        final JLabel player1ImageLabel = new JLabel();
        player1ImageLabel.setBounds(180, 300, 240, 240);
        add(player1ImageLabel);

        final JLabel player2ImageLabel = new JLabel();
        player2ImageLabel.setBounds(560, 300, 240, 240);
        add(player2ImageLabel);
        
        final JLabel player3ImageLabel = new JLabel();
       player3ImageLabel.setBounds(0,0,775,580);
       add(player3ImageLabel);

       // Bilder laden

       // Bilder für Spieler 1
        final ImageIcon player1Image1 = new ImageIcon(resizeImage(loadImage("/player/rinor_down_1.png"), 60, 60));
        final ImageIcon player1Image2 = new ImageIcon(resizeImage(loadImage("/player/rinor_down_2.png"), 60, 60));

        // Bilder für Spieler 2
        final ImageIcon player2Image1 = new ImageIcon(resizeImage(loadImage("/player/besart_down_1.png"), 60, 60));
        final ImageIcon player2Image2 = new ImageIcon(resizeImage(loadImage("/player/besart_down_2.png"), 60, 60));
        
        // Hintergrundbilder
        final ImageIcon player3Image1 = new ImageIcon(resizeImage(loadImage("/tilesMap/background.png"), 775, 580));
        final ImageIcon player3Image2 = new ImageIcon(resizeImage(loadImage("/tilesMap/background2.png"), 775, 580));

        // Bilder in den Labels anzeigen und abwechselnd wechseln
        player1ImageLabel.setIcon(player1Image1);
        player2ImageLabel.setIcon(player2Image1);
        player3ImageLabel.setIcon(player3Image1);

        
        // Timer für die Animation
        final Timer timer = new Timer(500, new ActionListener() {
            boolean isImage1 = true;

            public void actionPerformed(ActionEvent e) {
                if (isImage1) {
                    player1ImageLabel.setIcon(player1Image2);
                    player2ImageLabel.setIcon(player2Image2);
                    player3ImageLabel.setIcon(player3Image2);
                } else {
                    player1ImageLabel.setIcon(player1Image1);
                    player2ImageLabel.setIcon(player2Image1);
                    player3ImageLabel.setIcon(player3Image1);
                }
                isImage1 = !isImage1;
            }
        });
        timer.start();

        // Button zum Starten des Spiels
        final JButton playButton = new JButton("PLAY!");
        playButton.setBounds(345, 250, 105, 51);
        add(playButton);

        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	 // Auslesen der Spieler-Namen aus den Textfeldern
                String player1Name = player1NameField.getText();
                String player2Name = player2NameField.getText();

                // Übergabe der Spieler-Namen an das GamePanel
                gamePanel.setPlayerNames(player1Name, player2Name);
                gamePanel.showGamePanel();
                
                // Timer stoppen
                timer.stop(); 

                // Fokus vom Button entfernen
                playButton.setFocusable(false); // Fokus entfernen
            }
        });
    }

    private BufferedImage loadImage(String path) {
        try {
        	 // Lädt ein Bild von der angegebenen Pfad-URL
            return ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
    	// Skaliert ein Bild auf die angegebenen Dimensionen
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }
    
    protected void paintComponent(Graphics g) {
    	// Methode zum Zeichnen des Hintergrundbilds
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, null);
        }
    }
}
