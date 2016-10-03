package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import utils.Block;
import utils.MathHelper;
import utils.MazeGenerator;
import utils.NoiseGenerator;

public class Dungeon {

	private boolean[][] dungeon;
	private Block wall = new Block("stone/rock.png", 0, true, 16, 16);
	private Block floor = new Block("stone/stone.png", 0, false, 16, 16);
	private NoiseGenerator noise;
	private MazeGenerator maze;
	private Player player;
	private float xDif, yDif;

	public Dungeon() {
		noise = new NoiseGenerator(MathHelper.random.nextLong());
		player = new Player(100, 1, 1);
		dungeon = noise.getCellularAutomataNoise(64, 64, 4, 100, 6);
		maze = new MazeGenerator(32, 32, MathHelper.random.nextLong());
		maze.generate(1, 1);
	}

	public void update(float time) {
		if (Main.input.isKeyDown(KeyEvent.VK_W)) {
			yDif += (player.getSpeed() * time);
		}
		if (Main.input.isKeyDown(KeyEvent.VK_A)) {
			xDif += (player.getSpeed() * time);
		}
		if (Main.input.isKeyDown(KeyEvent.VK_S)) {
			yDif -= (player.getSpeed() * time);
		}
		if (Main.input.isKeyDown(KeyEvent.VK_D)) {
			xDif -= (player.getSpeed() * time);
		}
	}

	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
//		g2d.setColor(Color.black);
//		g2d.fillRect(0, 0, 2048, 2048);
//		g2d.setColor(Color.white);
//		for (int i = 0; i < dungeon.length; i++) {
//			for (int j = 0; j < dungeon[0].length; j++) {
//				if (dungeon[i][j]) {
//					floor.draw(g2d, i * 16 + (int) xDif, j * 16 + (int) yDif);
//				} else {
//					wall.draw(g2d, i * 16 + (int) xDif, j * 16 + (int) yDif);
//				}
//			}
//		}
		maze.draw(g2d);
	}

}
