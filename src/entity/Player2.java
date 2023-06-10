package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.keyHandler;

public class Player2 extends Entity{
	
	GamePanel gp;
	keyHandler keyH;
	
	public Player2(GamePanel gp, keyHandler keyH) {
		
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
		
		x = 670;
		y = 475;
		speed = 3;
		direction = "down";
		
	}
	
	public void getPlayerImage() {
		
		try {
			
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/besart_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/besart_up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/besart_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/besart_down_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/besart_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/besart_left_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/besart_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/besart_right_2.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void update() {
		
		if(keyH.upArrowPressed || keyH.downArrowPressed || 
				keyH.leftArrowPressed || keyH.rightArrowPressed) {
		
			if(keyH.upArrowPressed) {
				direction = "up";

			}
			else if(keyH.downArrowPressed) {
				direction = "down";

			}
			else if(keyH.leftArrowPressed) {
				direction = "left";

			}
			else if(keyH.rightArrowPressed) {
				direction = "right";

			}

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