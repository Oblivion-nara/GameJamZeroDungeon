package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import utils.Block;
import utils.MazeGenerator;
import utils.NoiseGenerator;
import utils.ResourceLoader;

public class Dungeon {

	private int entityMultiplier, blockMultiplier;
	private float xDif, yDif;
	private boolean[][] dungeon;
	private boolean isMaze = false;
	private Rectangle[][] collisionBoxes;
	private BufferedImage mapPicture, mazePicture;
	private Block wall = new Block("stone/rock.png", 0, true, 16, 16);
	private Block floor = new Block("stone/stone.png", 0, false, 16, 16);
	private NoiseGenerator noise;
	private MazeGenerator maze;
	private Player player;
	private Enemy[] enemies;
	private Rectangle[] enemyCollisionBoxes;

	public Dungeon() {
		noise = new NoiseGenerator(0);
		entityMultiplier = 4;
		blockMultiplier = 4;
		initDungeon();
		player = new Player(1000, 1000, 100, 1, 1, 200,
				ResourceLoader.getPlayerSprites("character_sprites_black", 16, 16));
		;
		BufferedImage[][] enemyImage = ResourceLoader.getPlayerSprites("ghost_sprites", 16, 16);
		int spawnWidth = mapPicture.getWidth() - 400;
		int spawnHeight = mapPicture.getHeight() - 400;
		int numOfEnemies = 10;
		enemies = new Enemy[numOfEnemies];
		enemyCollisionBoxes = new Rectangle[numOfEnemies];
		for (int i = 0; i < numOfEnemies; i++) {
			int xLoc = Main.random.nextInt(spawnWidth) + 200, yLoc = Main.random.nextInt(spawnHeight) + 200;
			enemies[i] = new Enemy(xLoc, yLoc, 100, 2, 1, 3.5f + (float) Main.random.nextGaussian() * 2, enemyImage);
			int width = enemies[i].getSize().x * entityMultiplier, height = enemies[i].getSize().y * entityMultiplier;
			enemyCollisionBoxes[i] = new Rectangle(xLoc - width / 2, yLoc - height / 2, width, height);
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
					floor.draw(g, i * tileSize * blockMultiplier, j * tileSize * blockMultiplier, blockMultiplier);
				} else {
					wall.draw(g, i * tileSize * blockMultiplier, j * tileSize * blockMultiplier, blockMultiplier);
				}
				collisionBoxes[i][j] = new Rectangle(i * tileSize * blockMultiplier, j * tileSize * blockMultiplier,
						wall.getWidth() * blockMultiplier, wall.getHeight() * blockMultiplier);
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
		if (Main.input.isKeyDown(KeyEvent.VK_W) && !collideUp()) {
			player.moving();
			if (Main.input.isKeyDown(KeyEvent.VK_A)) {
				player.moveUL(time);
			} else if (Main.input.isKeyDown(KeyEvent.VK_D)) {
				player.moveUR(time);
			} else {
				player.moveUp(time);
			}
		} else if (Main.input.isKeyDown(KeyEvent.VK_S) && !collideDown()) {
			player.moving();
			if (Main.input.isKeyDown(KeyEvent.VK_A)) {
				player.moveDL(time);
			} else if (Main.input.isKeyDown(KeyEvent.VK_D)) {
				player.moveDR(time);
			} else {
				player.moveDown(time);
			}
		} else if (Main.input.isKeyDown(KeyEvent.VK_A) && !collideLeft()) {
			player.moving();
			player.moveLeft(time);
		} else if (Main.input.isKeyDown(KeyEvent.VK_D) && !collideRight()) {
			player.moving();
			player.moveRight(time);
		}
		if (Main.input.isKeyDown(KeyEvent.VK_SPACE)) {
			player.attack();
		}
	}

	private boolean collideUp() {
		for (int i = 0; i < collisionBoxes.length; i++) {
			for (int j = 0; j < collisionBoxes[0].length; j++) {
				if (!dungeon[i][j] && player.collideUp(collisionBoxes[i][j], entityMultiplier)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean collideLeft() {
		for (int i = 0; i < collisionBoxes.length; i++) {
			for (int j = 0; j < collisionBoxes[0].length; j++) {
				if (!dungeon[i][j] && player.collideLeft(collisionBoxes[i][j], entityMultiplier)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean collideDown() {
		for (int i = 0; i < collisionBoxes.length; i++) {
			for (int j = 0; j < collisionBoxes[0].length; j++) {
				if (!dungeon[i][j] && player.collideDown(collisionBoxes[i][j], entityMultiplier)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean collideRight() {
		for (int i = 0; i < collisionBoxes.length; i++) {
			for (int j = 0; j < collisionBoxes[0].length; j++) {
				if (!dungeon[i][j] && player.collideRight(collisionBoxes[i][j], entityMultiplier)) {
					return true;
				}
			}
		}
		return false;
	}

	private void updateCamera() {
		xDif = -(player.xLocation - Main.width / 2);
		yDif = -(player.yLocation - Main.height / 2);
	}

	public void update(float time) {
		movePlayer(time);
		player.update(time);
		int aliveCount = 0;
		for (Enemy enemy : enemies) {
			enemy.update(time, player.getLocation());
<<<<<<< HEAD
			if (enemy.isAlive()) {
				aliveCount++;
			}
=======
			
>>>>>>> branch 'master' of https://github.com/Oblivion-nara/GameJamZeroDungeon.git
		}
		isMaze = aliveCount == 0;
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

		for (Enemy enemy : enemies) {
			g2d.drawImage((Image) enemy.draw(),
					(int) (enemy.xLocation + xDif) - (enemy.getSize().x * entityMultiplier) / 2,
					(int) (enemy.yLocation + yDif) - (enemy.getSize().y * entityMultiplier) / 2,
					enemy.getSize().x * entityMultiplier, enemy.getSize().y * entityMultiplier, null);
		}

	}

}
