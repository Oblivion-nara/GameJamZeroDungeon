package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import utils.Block;
import utils.InputHandler;
import utils.NoiseGenerator;

public class Dungeon {

	private static final long serialVersionUID = 1L;
	private boolean[][] dungeon;
	private Block wall = new Block("stone/rock.png", 0, true, 16, 16);
	private Block floor = new Block("stone/stone.png", 0, false, 16, 16);
	private NoiseGenerator noise;
	private Player player;
	private float xDif, yDif;

	public Dungeon() {
		noise = new NoiseGenerator(0);
		player = new Player(1000,1000,100, 1, 1, 50);
		dungeon = noise.getCellularAutomataNoise(256, 256, 4, 4, 4);
	}

	private void movePlayer(float time){
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
	
	private void updateCamera(){
		xDif = -(player.xLocation - Main.width/2);
		yDif = -(player.yLocation - Main.height/2);
	}
	
	public void update(float time) {
		movePlayer(time);
		updateCamera();
	}

	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, 2048, 2048);
		g2d.setColor(Color.white);
		for (int i = 0; i < dungeon.length; i++) {
			for (int j = 0; j < dungeon[0].length; j++) {
				if (dungeon[i][j]) {
					floor.draw(g2d, i * 16 + (int) xDif, j * 16 + (int) yDif);
				} else {
					wall.draw(g2d, i * 16 + (int) xDif, j * 16 + (int) yDif);
				}
			}
		}
	}

}
