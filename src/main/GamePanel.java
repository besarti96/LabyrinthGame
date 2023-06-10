package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import tilesMap.tilesManager;
import entity.Player;
import entity.Player2;

public class GamePanel extends JPanel implements Runnable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// SCREEN EINSTELLUNGEN
    final int originalTileSize = 16; // 16*16 tile // Ursprüngliche Größe einer Kachel (16x16 Pixel)
    final int scale = 3; // Skalierungsfaktor

    // MapScreen
    public final int tileSize = originalTileSize * scale; // 48*48 Tile && Größe einer Kachel nach der Skalierung (48x48 Pixel)
    public final int maxScreenCol = 16; // columns 16x && Maximale Anzahl an Kacheln in einer Spalte
    public final int maxScreenRow = 12; // Row 12x && Maximale Anzahl an Kacheln in einer Zeile
    public final int screenWidth = tileSize * maxScreenCol; // 768 Pixels && Breite des Bildschirms in Pixeln
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixel && Höhe des Bildschirms in Pixeln

    // Punkteanzeige
    public int playerScore = 0; // Punktzahl des Spielers 1
    public int player2Score = 0; // Punktzahl des Spielers 2
    public String player1Name; // Name des Spielers 1  
    public String player2Name; // Name des Spielers 2

    // Menu
    private Menu menu; // Menu-Objekt
    private boolean showMenu; // Flag, ob das Menu angezeigt wird

    public void setPlayerNames(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public void showMenu() {
        showMenu = true;
        menu.setVisible(true);
        requestFocus();
    }

    public void hideMenu() {
        showMenu = false;
        menu.setVisible(false);
        requestFocus();
    }

    public void showGamePanel() {
        showMenu = false;
        requestFocus();
    }

    
    public void drawScore(Graphics2D g2) {
    	// Zeichnet die Punkteanzeige auf den Bildschirm
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 20)); // Größere und fettere Schrift
        g2.drawString("Player " + player1Name + " Score: " + playerScore, 175, 15);
        g2.drawString("Player " + player2Name + " Score: " + player2Score, 450, 15);
    }

    // FPS
    int FPS = 60;

    // System
    tilesManager tileM = new tilesManager(this); // Kachelmanager
    keyHandler keyH = new keyHandler(); // Tastatur-Handler
    Sound sound = new Sound(); // Sound-Objekt
    public CollisionChecker cChecker = new CollisionChecker(this); // Kollisionsüberprüfer
    Thread gameThread; // Thread für das Spiel

    // Entity
    public Player player = new Player(this, keyH); // Spieler 1
    public Player2 player2 = new Player2(this, keyH); // Spieler 2

    // Animation um dunkel zu erreichen.
    private BufferedImage fogOfWarImage; // Bild für den "Fog of War"
    private int visibleRadius = 60; // Radius der sichtbaren Umgebung um die Spieler

    public GamePanel() {
        // Konstruktor für das GamePanel
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.DARK_GRAY);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        playMusic(0);
        
        // Initialisierung des "Fog of War"-Bildes
        fogOfWarImage = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D fogGraphics = fogOfWarImage.createGraphics();
        fogGraphics.setColor(new Color(20, 20, 0, 128));  // Halbtransparentes Schwarz
        fogGraphics.fillRect(0, 0, screenWidth, screenHeight);
    }

    public void startGameThread() {
    	// Startet den Spielthread
        showMenu();
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run() {
    	// Haupt-Game-Loop
    	
        double drawInterval = 1000000000.0 / FPS; // Zeitintervall zwischen den Frames
        double delta = 0; // Differenzzeit seit dem letzten Frame
        long lastTime = System.nanoTime(); // Zeit des letzten Frames
        long currentTime; 
        long timer = 0; // Timer für FPS-Zählung
        int drawCount = 0; // Anzahl der gezeichneten Frames

        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update(); // Aktualisiert den Spielzustand
                repaint(); // Zeichnet den Bildschirm neu
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
    	// Aktualisiert den Spielzustand
        if (!showMenu) {
            player.update(); // Aktualisiert Spieler 1
            player2.update(); // Aktualisiert Spieler 2
        }
    }

        public void paintComponent(Graphics g) {
        	// Zeichnet den Bildschirm
        	
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            if (showMenu) {
                menu.setBounds(0, 0, screenWidth, screenHeight);
                menu.paint(g2); // Zeichnet das Menu
            } else {
                tileM.draw(g2); // Zeichnet die Kacheln
                player.draw(g2); // Zeichnet Spieler 1
                player2.draw(g2); // Zeichnet Spieler 2
                
                // Ab hier nur für den "Fog of War"
                
                // Zeichnen Sie den "Fog of War"
                Graphics2D fogGraphics = fogOfWarImage.createGraphics();

                // Setzt das "Fog of War"-Bild auf undurchsichtiges Schwarz zurück
                fogGraphics.setComposite(AlphaComposite.Src);
                fogGraphics.setColor(new Color(0, 0, 0, 255)); // Undurchsichtiges Schwarz
                fogGraphics.fillRect(0, 0, screenWidth, screenHeight);

                // Setzt den Zeichenmodus auf "SrcOut", um den sichtbaren Bereich um die Spieler zu erzeugen
                fogGraphics.setComposite(AlphaComposite.DstOut); //.DstOut = bei berührung wird das durchsichtige nicht mittgerechnet .SrcOut bei überschneidung bleibt es dunkel.

                // Zeichnen Sie einen Kreis um jeden Spieler
                int playerRadius = visibleRadius * 2;
               int playerOffset = (playerRadius - 35) / 2; //mitte des spielers 
               fogGraphics.fillOval(player.x - playerOffset, player.y - playerOffset, playerRadius, playerRadius);
               fogGraphics.fillOval(player2.x - playerOffset, player2.y - playerOffset, playerRadius, playerRadius);
               
                // Zeichnen Sie das "Fog of War"-Bild über dem gesamten GamePanel
                g2.drawImage(fogOfWarImage, 0, 0, null);
                
                // Zeichnen Sie das Score-Textfeld nach dem Fog of War
                drawScore(g2);
            }
            g2.dispose();
        }

        
        // Musik 
        
    public void playMusic(int i) {
    	// Spielt die Hintergrundmusik ab
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    public void stopMusic(int i) {
    	 // Stoppt die Hintergrundmusik
        sound.stop();
    }

    public void PlaySE(int i) {
    	// Spielt einen Soundeffekt ab
        sound.setFile(i);
        sound.play();
    }
}
