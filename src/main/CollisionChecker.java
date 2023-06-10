package main;

import entity.Entity;
import entity.Player;
import entity.Player2;


public class CollisionChecker {

    GamePanel gp; // Referenz auf das GamePanel

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
        
    }
    public int mapzahlen = 1; // Zähler für die Kartennummer
 
    int tileNum1, tileNum2; // Variablen zur Speicherung der Kachelnummern
    public void checkTile(Entity entity) {
    	// Überprüft die Kollision zwischen einer Entität und den Kacheln

        int entityLeftx = entity.x + entity.solidArea.x;
        int entityRightx = entity.x + entity.solidArea.x + entity.solidArea.width;
        int entityTopy = entity.y + entity.solidArea.y;
        int entityBottomy = entity.y + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftx/gp.tileSize;
        int entityRightCol = entityRightx/gp.tileSize;
        int entityTopRow = entityTopy/gp.tileSize;
        int entityBottomRow = entityBottomy/gp.tileSize;

        switch(entity.direction) {
            case "up":
                entityTopRow = (entityTopy - entity.speed) /gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomy + entity.speed) /gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftx - entity.speed) /gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightx + entity.speed) /gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
        }
        
        	// Überprüfung der spezifischen Kachelnummern und Ausführung entsprechender Aktionen          
        if (tileNum1 == 10 || tileNum2 == 10) {
        	// Wenn ein Spieler eine Punktekachel berührt
        	
            gp.PlaySE(3); // Spielt den Soundeffekt für das Punktesammeln ab
            if (entity instanceof Player) {
                gp.playerScore++; // Erhöht den Punktestand des Spielers
            } else if (entity instanceof Player2) {
                gp.player2Score++; // Erhöht den Punktestand von Spieler 2
            }
            
            // Setzt die Standardwerte für beide Spieler zurück
            gp.player.setDefaultValues();
            gp.player2.setDefaultValues();

            // Lädt die nächste Karte
            mapzahlen++;
            gp.tileM.loadMap("/maps/Map0" + mapzahlen + ".txt");

            // Gibt die aktuelle Kartennummer aus
            System.out.println("mapzahlen: " + mapzahlen);
        }

        if (tileNum1 == 2 || tileNum1 == 13 || tileNum1 == 14) {
        	// Wenn ein Spieler ins Wasser läuft
        	
            gp.PlaySE(1);
            if (entity instanceof Player) {
            // Setzt die Standardwerte für spieler 1
            gp.player.setDefaultValues();
            }
        }
        
        if (tileNum2 == 2 || tileNum2 == 13 || tileNum2 == 14) {
        	// Wenn ein Spieler ins Wasser läuft
        	
            gp.PlaySE(1);
            if (entity instanceof Player2) {
            // Setzt die Standardwerte für spieler 2
            gp.player2.setDefaultValues();
            }
        }
    }
}