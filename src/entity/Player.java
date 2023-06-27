package entity;


import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.keyHandler;

public class Player extends Entity{
	
	GamePanel gp;
	keyHandler keyH;
	
	public Player(GamePanel gp, keyHandler keyH) {
		
		this.gp = gp;
		this.keyH = keyH;
		
		solidArea = new Rectangle(); //Wenn man die Hitbox auf den Spieler so gross machen möchte wie das Tiles dann nimmt schreibt man das so:
									 //New Rectangle(0, 0, 48, 48) oder (0, 0, gp.tileSize, gp.tileSize)
		solidArea.x = 8;
		solidArea.y = 16;
		solidArea.width = 32;
		solidArea.height = 32;
		
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setDefaultValues() {
		
		x = 50;
		y = 44;
		speed = 4;
		direction = "down";
		
	}
	
	public void getPlayerImage() {
		
		try {
			
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/rinor_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/rinor_up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/rinor_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/rinor_down_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/rinor_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/rinor_left_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/rinor_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/rinor_right_2.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//update funktion was passiert wenn (?funktion?) taste gedrückt isz
	public void update() {
		
		if(keyH.upPressed == true || keyH.downPressed == true || 
				keyH.leftPressed == true || keyH.rightPressed == true) {
		
		
		if(keyH.upPressed == true) {
			direction = "up";
		}
		else if(keyH.downPressed == true) {
			direction = "down";
		}
		else if(keyH.leftPressed == true) {
			direction = "left";
		}
		else if(keyH.rightPressed == true) {
			direction = "right";
		}
		//dadurch dass wir update 60mal pro sekunde aufrufen-
		//jede pro frame countet++ spriteCounter! dadurch dass das so schnell geschieht müüsen wir das-
		//-runterbrechen auf alle 11 frames. alles down bellow. 
		//dadurch das diess non stop geschah mussten wir funktion UPDATE if elsen. so dass nuuuur wenn keyH(up, down. left, right)Tastegedrückt-
		// erst dann soll es counten und Update updaten!
		
		//CHECK TILE COLLISION
		collisionOn = false;
		gp.cChecker.checkTile(this);
		
		//IF COLLISION IS FALSE, PLAYER CAN MOVE
		if(collisionOn == false) {
			
			switch(direction) {
			case "up":
				y -= speed;
				break;
			case "down":
				y += speed;
				break;
			case "left":
				x -= speed;
				break;
			case "right":
				x += speed;
				break;
			
			}
		}
		
		spriteCounter++;
		if(spriteCounter > 10) {
			if(spriteNum == 1) {
				spriteNum = 2;
			}
			else if(spriteNum == 2) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}
	}
}

		public void draw(Graphics2D g2) {
//			g2.setColor(Color.white);
//			g2.fillRect(x, y, gp.tileSize, gp.tileSize);
			
			BufferedImage image = null;
			
			switch(direction) {
			case "up":
				if(spriteNum == 1) {
					image = up1;
				}
				if(spriteNum == 2) {
					image = up2;
				}
				break;
			case "down":
				if(spriteNum == 1) {
					image = down1;
				}
				if(spriteNum == 2) {
					image = down2;
				}
				break;
			case "left":
				if(spriteNum == 1) {
					image = left1;
				}
				if(spriteNum == 2) {
					image = left2;
				}
				break;
			case "right":
				if(spriteNum == 1) {
					image = right1;
				}
				if(spriteNum == 2) {
					image = right2;
				}
				break;
			}
			g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
			
		}
	}
