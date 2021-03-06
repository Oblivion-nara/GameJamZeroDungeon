package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

import blocks.Block;
import generators.CellularAutomata;
import generators.MazeGenerator;
import handlers.ResourceHandler;

public class Dungeon {

	
	private int entityMultiplier, blockMultiplier;
	private float xDif, yDif;
	private boolean[][] dungeon;
	private boolean isMaze = false;
	
	private Rectangle[][] collisionBoxes;
	private BufferedImage mapPicture, mazePicture;
	private CellularAutomata noise;rkjnfierfn
	private MazeGenerator maze;
	private Player player;
	private Enemy[] imps, ghosts;
	private Rectangle[] impCollisionBoxes, ghostCollisionBoxes;

	public Dungeon() {
		// much work wow!!
		noise = new CellularAutomata();
		entityMultiplier = 4;
		blockMultiplier = 4;
		initDungeon();
		player = new Player(1000, 1000, 100, 1, 1, 200,
				ResourceHandler.getPlayerSprites("character_sprites_black", 16, 16));
		
		BufferedImage[][] impImage = ResourceHandler.getPlayerSprites("imp_sprites", 8, 8);
		BufferedImage[][] ghostImage = ResourceHandler.getPlayerSprites("ghost_sprites", 16, 16);
		int spawnWidth = mapPicture.getWidth() - 400;
		int spawnHeight = mapPicture.getHeight() - 400;
		int numOfImps = 10;
		int numOfGhosts = 10;
		imps = new Enemy[numOfImps];
		ghosts = new Enemy[numOfGhosts];
		impCollisionBoxes = new Rectangle[numOfImps];
		ghostCollisionBoxes = new Rectangle[numOfGhosts];
		
		for (int i = 0; i < numOfImps; i++) {
			int xLoc = Main.random.nextInt(spawnWidth) + 200, yLoc = Main.random.nextInt(spawnHeight) + 200;
			imps[i] = new Enemy(xLoc, yLoc, 100, 2, 1, 180f + (float) Main.random.nextGaussian() * 60, impImage);
			int width = imps[i].getSize().x * entityMultiplier, height = imps[i].getSize().y * entityMultiplier;
			impCollisionBoxes[i] = new Rectangle(xLoc - width / 2, yLoc - height / 2, width, height);
		}
		
		for (int i = 0; i < numOfGhosts; i++) {
			int xLoc = Main.random.nextInt(spawnWidth) + 200, yLoc = Main.random.nextInt(spawnHeight) + 200;
			ghosts[i] = new Enemy(xLoc, yLoc, 100, 2, 1, 200f + (float) Main.random.nextGaussian() * 40, ghostImage);
			int width = ghosts[i].getSize().x * entityMultiplier, height = ghosts[i].getSize().y * entityMultiplier;
			ghostCollisionBoxes[i] = new Rectangle(xLoc - width / 2, yLoc - height / 2, width, height);
		}
		initMaze();
	}

	private void initDungeon() {

		int x = 256;
		int y = 256;
		int tileSize = 16;

		dungeon = noise.getCellularAutomataNoise(x, y, 4, 4, 4);
		mapPicture = new BufferedImage(tileSize * x, tileSize * y, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) mapPicture.getGraphics();
		collisionBoxes = new Rectangle[dungeon.length][dungeon[0].length];
		for (int i = 0; i < dungeon.length; i++) {
			for (int j = 0; j < dungeon[0].length; j++) {
				if (dungeon[i][j]) {
					Block.stone.draw(g, i * tileSize * blockMultiplier, j * tileSize * blockMultiplier, blockMultiplier);
				} else {
					Block.rock.draw(g, i * tileSize * blockMultiplier, j * tileSize * blockMultiplier, blockMultiplier);
				}
				collisionBoxes[i][j] = new Rectangle(i * tileSize * blockMultiplier, j * tileSize * blockMultiplier,
						Block.rock.getWidth() * blockMultiplier, Block.rock.getHeight() * blockMultiplier);
			}
		}
	}

	private void initMaze() {
		int x = 32;
		int y = 32;
		int tileSize = 16;
		maze = new MazeGenerator(x, y, 0);
		maze.generate(1, 1);
		mazePicture = new BufferedImage(tileSize * x * tileSize, tileSize * y * tileSize, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) mazePicture.getGraphics();
		maze.draw(g, blockMultiplier);
	}

	private void movePlayer(float time) {
		if (Main.input.isKeyDown(KeyEvent.VK_W)) {
			player.moving();
			if (Main.input.isKeyDown(KeyEvent.VK_A)) {
				player.moveUL(time);
			} else if (Main.input.isKeyDown(KeyEvent.VK_D)) {
				player.moveUR(time);
			} else {
				player.moveUp(time);
			}
		} else if (Main.input.isKeyDown(KeyEvent.VK_S)) {
			player.moving();
			if (Main.input.isKeyDown(KeyEvent.VK_A)) {
				player.moveDL(time);
			} else if (Main.input.isKeyDown(KeyEvent.VK_D)) {
				player.moveDR(time);
			} else {
				player.moveDown(time);
			}
		} else if (Main.input.isKeyDown(KeyEvent.VK_A)) {
			player.moving();
			player.moveLeft(time);
		} else if (Main.input.isKeyDown(KeyEvent.VK_D)) {
			player.moving();
			player.moveRight(time);
		}
		if (Main.input.isKeyDown(KeyEvent.VK_SPACE)) {
			player.attack();
			Point att = player.attackVector;
			Line2D attackLine = new Line2D.Double(player.xLocation,player.yLocation,att.x,att.y);

			for (int i = 0; i < imps.length; i++) {
				if(attackLine.intersects(impCollisionBoxes[i])){
					imps[i].kill();
				}
			}
			for (int i = 0; i < ghosts.length; i++) {
				if(attackLine.intersects(ghostCollisionBoxes[i])){
					ghosts[i].kill();
				}
			}
		}
	}

//	private boolean collideUp() {
//		for (int i = 0; i < collisionBoxes.length; i++) {
//			for (int j = 0; j < collisionBoxes[0].length; j++) {
//				if (!dungeon[i][j] && player.collideUp(collisionBoxes[i][j], entityMultiplier)) {
//					return true;
//				}
//			}
//		}
//		return false;
//	}
//
//	private boolean collideLeft() {
//		for (int i = 0; i < collisionBoxes.length; i++) {
//			for (int j = 0; j < collisionBoxes[0].length; j++) {
//				if (!dungeon[i][j] && player.collideLeft(collisionBoxes[i][j], entityMultiplier)) {
//					return true;
//				}
//			}
//		}
//		return false;
//	}
//
//	private boolean collideDown() {
//		for (int i = 0; i < collisionBoxes.length; i++) {
//			for (int j = 0; j < collisionBoxes[0].length; j++) {
//				if (!dungeon[i][j] && player.collideDown(collisionBoxes[i][j], entityMultiplier)) {
//					return true;
//				}
//			}
//		}
//		return false;
//	}
//
//	private boolean collideRight() {
//		for (int i = 0; i < collisionBoxes.length; i++) {
//			for (int j = 0; j < collisionBoxes[0].length; j++) {
//				if (!dungeon[i][j] && player.collideRight(collisionBoxes[i][j], entityMultiplier)) {
//					return true;
//				}
//			}
//		}
//		return false;
//	}

	private void updateCamera() {
		xDif = -(player.xLocation - Main.width / 2);
		yDif = -(player.yLocation - Main.height / 2);
	}

	public void update(float time) {
		player.update(time);
		int aliveCount = 0;
		for (int i = 0; i < imps.length; i++) {
			imps[i].update(time, player.getLocation());
			if (imps[i].isAlive()) {
				aliveCount++;
			}
			impCollisionBoxes[i].setLocation((int)(imps[i].xLocation-imps[i].size.x/2), (int)(imps[i].yLocation-imps[i].size.y/2));
		}
		for (int i = 0; i < ghosts.length; i++) {
			ghosts[i].update(time, player.getLocation());
			if (ghosts[i].isAlive()) {
				aliveCount++;
			}
			ghostCollisionBoxes[i].setLocation((int)(ghosts[i].xLocation-ghosts[i].size.x/2), (int)(ghosts[i].yLocation-ghosts[i].size.y/2));
		}
		movePlayer(time);
		isMaze = true;
		updateCamera();
	}

	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, Main.width, Main.height);
		if (isMaze) {
			g2d.drawImage(mazePicture, (int) xDif, (int) yDif, mazePicture.getWidth(), mazePicture.getHeight(), null);
		} else {
			g2d.drawImage(mapPicture, (int) xDif, (int) yDif, mapPicture.getWidth(), mapPicture.getHeight(), null);
		}

		g2d.drawImage((Image) player.draw(), Main.width / 2 - (player.getSize().x * entityMultiplier) / 2,
				Main.height / 2 - (player.getSize().y * entityMultiplier) / 2, player.getSize().x * entityMultiplier,
				player.getSize().y * entityMultiplier, null);

		for (Enemy enemy : imps) {
			g2d.drawImage((Image) enemy.draw(),
					(int) (enemy.xLocation + xDif) - (enemy.getSize().x * entityMultiplier) / 2,
					(int) (enemy.yLocation + yDif) - (enemy.getSize().y * entityMultiplier) / 2,
					enemy.getSize().x * entityMultiplier, enemy.getSize().y * entityMultiplier, null);
		}
		for (Enemy enemy : ghosts) {
			g2d.drawImage((Image) enemy.draw(),
					(int) (enemy.xLocation + xDif) - (enemy.getSize().x * entityMultiplier) / 2,
					(int) (enemy.yLocation + yDif) - (enemy.getSize().y * entityMultiplier) / 2,
					enemy.getSize().x * entityMultiplier, enemy.getSize().y * entityMultiplier, null);
		}

	}

}
