package tilesMap;

import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import javax.imageio.ImageIO;
import main.GamePanel;

public class tilesManager {

	GamePanel gp;
	public tilesMap[] tile; // Array zur Speicherung der Kacheln
	public int mapTileNum[][]; // Matrix zur Speicherung der Karteninformationen
	
	public tilesManager(GamePanel gp) {
		
		this.gp = gp;
		
		tile = new tilesMap[20]; // Initialisierung des Kachel-Arrays mit einer Größe von 20
		mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow]; // Initialisierung der Kartenmatrix basierend auf den maximalen Spalten- und Zeilenwerten
		
		getTileImage(); // Methode zum Laden der Kachelbilder aufrufen
		loadMap("/maps/Map01.txt"); // Methode zum Laden der Karte "Map01.txt" aufrufen
	}
	
	
	//Definierung der Bilder (In einem Array auf das man zugreift)
	public void getTileImage() {
		
		try {
			
			tile[0] = new tilesMap();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tilesMap/wall05.png"));
			
			tile[1] = new tilesMap();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tilesMap/wall01.png"));
			tile[1].collision = true;
			
			tile[2] = new tilesMap();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tilesMap/water02.png"));
			
			tile[3] = new tilesMap();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tilesMap/wall02.png"));
			tile[3].collision = true;
			
			tile[4] = new tilesMap();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tilesMap/wall06.png"));
			tile[4].collision = true;
			
			tile[5] = new tilesMap();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tilesMap/wall07.png"));
			tile[5].collision = true;
			
			tile[6] = new tilesMap();
			tile[6].image = ImageIO.read(getClass().getResourceAsStream("/tilesMap/wall08.png"));
			tile[6].collision = true;
			
			tile[7] = new tilesMap();
			tile[7].image = ImageIO.read(getClass().getResourceAsStream("/tilesMap/wall09.png"));
			tile[7].collision = true;
			
			tile[8] = new tilesMap();
			tile[8].image = ImageIO.read(getClass().getResourceAsStream("/tilesMap/wall10.png"));
			tile[8].collision = true;
			
			tile[9] = new tilesMap();
			tile[9].image = ImageIO.read(getClass().getResourceAsStream("/tilesMap/grass02.png"));
			
			tile[10] = new tilesMap();
			tile[10].image = ImageIO.read(getClass().getResourceAsStream("/tilesMap/DoorExit1.png"));
			
			tile[11] = new tilesMap();
			tile[11].image = ImageIO.read(getClass().getResourceAsStream("/tilesMap/grass01.png"));
			
			tile[12] = new tilesMap();
			tile[12].image = ImageIO.read(getClass().getResourceAsStream("/tilesMap/wall03.png"));
			tile[12].collision = true;
	
			tile[13] = new tilesMap();
			tile[13].image = ImageIO.read(getClass().getResourceAsStream("/tilesMap/water01.png"));
			
			tile[14] = new tilesMap();
			tile[14].image = ImageIO.read(getClass().getResourceAsStream("/tilesMap/poisenWater.png"));
			
			tile[15] = new tilesMap();
			tile[15].image = ImageIO.read(getClass().getResourceAsStream("/tilesMap/wallMetall.png"));
			tile[15].collision = true;
			
			tile[16] = new tilesMap();
			tile[16].image = ImageIO.read(getClass().getResourceAsStream("/tilesMap/Ground.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	 // Versucht, die angegebene Datei zu öffnen und die Map zu laden
	public void loadMap(String FilePath) {
		
		try {
			// Erstellt einen InputStream aus der Datei
			InputStream is = getClass().getResourceAsStream(FilePath);
			// Erstellt einen BufferedReader zum Lesen der Datei
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			 // Initialisiert die Spalten- und Zeilenvariablen
			int col = 0;
			int row = 0;
			
			// Schleife zum Einlesen der Karte
			while(col < gp.maxScreenCol && row < gp.maxScreenRow) {
				// Liest eine Zeile aus der Datei
				String line = br.readLine();
				
				// Schleife zum Verarbeiten der Spalten in der aktuellen Zeile
				while(col < gp.maxScreenCol) {
					 // Trennt die Zeile anhand der Leerzeichen, um die einzelnen Zahlen zu erhalten
					String numbers[] = line.split(" ");
					// Konvertiert die aktuelle Zahl in einen Integer
					int num = Integer.parseInt(numbers[col]);
					 // Speichert die Zahl in der entsprechenden Position der Kartenmatrix
					mapTileNum[col][row] = num;
					
					// Inkrementiert die Spaltenvariable
					col++;
				}
				  // Überprüft, ob die maximale Spaltenanzahl erreicht wurde
				if(col == gp.maxScreenCol) {
					// Setzt die Spaltenvariable zurück und inkrementiert die Zeilenvariable
					col = 0;
					row++;
			        // Behandelt jegliche auftretende Ausnahme hier
			        // (z. B. Datei nicht gefunden, Dateiformatfehler usw.)
				}
			}
					
		}catch(Exception e) {
			
		}
		
	}
	
	
	public void draw(Graphics2D g2) {

		int col = 0;
		int row = 0;
		int x = 0;
		int y = 0;
		
		while(col < gp.maxScreenCol && row < gp.maxScreenRow) {
			
			int tileNum = mapTileNum[col][row];
			
			g2.drawImage (tile[tileNum].image, x,y, gp.tileSize, gp.tileSize, null);
			col++;
			x += gp.tileSize;
			
			if(col == gp.maxScreenCol) {
				col = 0;
				x = 0;
				row++;
				y += gp.tileSize;
			}
		}
		
		
	}
	
}
