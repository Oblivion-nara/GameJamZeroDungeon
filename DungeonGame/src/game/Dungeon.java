package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import utils.Block;
import utils.MazeGenerator;
import utils.NoiseGenerator;
import utils.ResourceLoader;

public class Dungeon {

	private static final long serialVersionUID = 1L;
	private float xDif, yDif;
	private boolean[][] dungeon;
	private BufferedImage mapPicture, mazePicture;
	private Block wall = new Block("stone/rock.png", 0, true, 16, 16);
	private Block floor = new Block("stone/stone.png", 0, false, 16, 16);
	private NoiseGenerator noise;
	private MazeGenerator maze;
	private Player player;
	private Enemy[] enemies;

	public Dungeon() {
		noise = new NoiseGenerator(0);
		initDungeon();
		player = new Player(1000, 1000, 100, 1, 1, 50, ResourceLoader.getBufferedImage("character_sprites_black"));
		;
		BufferedImage enemyImage = ResourceLoader.getBufferedImage("ghost_sprites");
		int spawnWidth = mapPicture.getWidth() - 400;
		int spawnHeight = mapPicture.getHeight() - 400;
		enemies = new Enemy[10];
		for (int i = 0; i < 10; i++) {
			enemies[i] = new Enemy(Main.random.nextInt(spawnWidth) + 200, Main.random.nextInt(spawnHeight) + 200, 100,
					2, 1, 15, enemyImage);
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

		for (int i = 0; i < dungeon.length; i++) {
			for (int j = 0; j < dungeon[0].length; j++) {
				if (dungeon[i][j]) {
					floor.draw(g, i * tileSize, j * tileSize);
				} else {
					wall.draw(g, i * tileSize, j * tileSize);
				}
			}
		}
	}
	
	private void initMaze(){
		int x = 32;
		int y = 32;
		int tileSize = 16;
		maze = new MazeGenerator(x, y, 0);
		maze.generate(1, 1);
		mazePicture = new BufferedImage(tileSize * x * tileSize, tileSize * y * tileSize, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) mazePicture.getGraphics();
		maze.draw(g);
	}

	private void movePlayer(float time) {
		if (Main.input.isKeyDown(KeyEvent.VK_W)) {
			if (Main.input.isKeyDown(KeyEvent.VK_A)) {
				player.moveUL(time);
			} else if (Main.input.isKeyDown(KeyEvent.VK_D)) {
				player.moveUR(time);
			} else {
				player.moveUp(time);
			}
		} else if (Main.input.isKeyDown(KeyEvent.VK_S)) {
			if (Main.input.isKeyDown(KeyEvent.VK_A)) {
				player.moveDL(time);
			} else if (Main.input.isKeyDown(KeyEvent.VK_D)) {
				player.moveDR(time);
			} else {
				player.moveDown(time);
			}
		} else if (Main.input.isKeyDown(KeyEvent.VK_A)) {
			player.moveLeft(time);
		} else if (Main.input.isKeyDown(KeyEvent.VK_D)) {
			player.moveRight(time);
		}
	}

	private void updateCamera() {
		xDif = -(player.xLocation - Main.width / 2);
		yDif = -(player.yLocation - Main.height / 2);
	}

	public void update(float time) {
		movePlayer(time);
		updateCamera();
	}

	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, Main.width, Main.height);
		g2d.drawImage(mapPicture, (int) xDif, (int) yDif, mapPicture.getWidth(), mapPicture.getHeight(), null);

		g2d.drawImage((Image) player.draw(), Main.width / 2 - player.getSize().x / 2,
				Main.height / 2 - player.getSize().y / 2, null);

		for (Enemy enemy : enemies) {
			g2d.drawImage((Image) enemy.draw(), (int) (enemy.xLocation + xDif) - enemy.getSize().x / 2,
					(int) (enemy.yLocation + yDif) - enemy.getSize().y / 2, null);
		}

	}

}
