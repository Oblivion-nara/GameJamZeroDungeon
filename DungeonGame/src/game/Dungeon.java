package game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import utils.Block;
import utils.NoiseGenerator;

public class Dungeon extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private boolean[][] dungeon;
	private Block wall = new Block("stone/rock.png", 0, true, 16, 16);
	private Block floor = new Block("stone/stone.png", 0, false, 16, 16);
	private BufferedImage dungeonImage;
	private NoiseGenerator noise;
	private Player player;
	private float xDif, yDif;
	
	public Dungeon(Image image){
		noise = new NoiseGenerator(0);
		player = new Player(100, 1, 1);
		dungeon = noise.getCellularAutomataNoise(64, 64, 4, 4, 1);
		dungeonImage = new BufferedImage(Main.width, Main.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) dungeonImage.getGraphics();
		for(int i = 0; i < dungeon.length; i++){
			for(int j = 0; j < dungeon[0].length; j++){
				if(dungeon[i][j]){
					floor.draw(g, i * 16, j * 16);
				}else{
					wall.draw(g, i * 16, j * 16);
				}
			}
		}
	}
	
	public void update(float time){
		if(Main.input.isKeyDown(KeyEvent.VK_W)){
			yDif += (player.getSpeed() * time);
		}
		if(Main.input.isKeyDown(KeyEvent.VK_A)){
			xDif += (player.getSpeed() * time);
		}
		if(Main.input.isKeyDown(KeyEvent.VK_S)){
			yDif -= (player.getSpeed() * time);
		}
		if(Main.input.isKeyDown(KeyEvent.VK_D)){
			xDif -= (player.getSpeed() * time);
		}
		System.out.println("moving by " + xDif + ", " + yDif);
	}
	
	@Override
	public void paintComponent(Graphics g){
		g.fillRect(0, 0, Main.width, Main.height);
		g.drawImage(dungeonImage, (int) xDif, (int) yDif, null);
	}

}
