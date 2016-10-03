package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import utils.Block;
import utils.InputHandler;
import utils.NoiseGenerator;

public class Dungeon extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private boolean[][] dungeon;
	private Block wall = new Block("stone/rock.png", 0, true, 16, 16);
	private Block floor = new Block("stone/stone.png", 0, false, 16, 16);
	private NoiseGenerator noise;
	private InputHandler input;
	private Player player;
	private float xDif, yDif;
	
	public Dungeon(){
		input = new InputHandler(this);
		noise = new NoiseGenerator(0);
		player = new Player(100, 1, 1);
		dungeon = noise.getCellularAutomataNoise(256, 256, 4, 4, 4);
	}
	
	public void update(long time){
		if(input.isKeyDown(KeyEvent.VK_W)){
			yDif += player.getSpeed() * time / Math.sqrt(2);
		}
		if(input.isKeyDown(KeyEvent.VK_A)){
			xDif += player.getSpeed() * time / Math.sqrt(2);
		}
		if(input.isKeyDown(KeyEvent.VK_S)){
			yDif -= player.getSpeed() * time / Math.sqrt(2);
		}
		if(input.isKeyDown(KeyEvent.VK_D)){
			xDif -= player.getSpeed() * time / Math.sqrt(2);
		}
	}
	
	@Override
	public void paintComponent(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, 2048, 2048);
		g2d.setColor(Color.white);
		for(int i = 0; i < dungeon.length; i++){
			for(int j = 0; j < dungeon[0].length; j++){
				if(dungeon[i][j]){
					floor.draw(g2d, i * 16 + (int) xDif, j * 16 + (int) yDif);
				}else{
					wall.draw(g2d, i * 16 + (int) xDif, j * 16 + (int) yDif);
				}
			}
		}
	}

}
