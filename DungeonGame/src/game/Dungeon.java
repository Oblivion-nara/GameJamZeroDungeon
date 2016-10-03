package game;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import utils.NoiseGenerator;

public class Dungeon extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private boolean[][] dungeon;
	private NoiseGenerator noise = new NoiseGenerator(0);
	private int xDif = 0, yDif = 0;
	
	public Dungeon(){
		dungeon = noise.getCellularAutomataNoise(256, 256, 4, 3, 5);
	}
	
	public void update(){
		
	}
	
	public void draw(Graphics g){
		g.setColor(Color.black);
		g.fillRect(0, 0, 2048, 2048);
		g.setColor(Color.white);
		for(int i = 0; i < dungeon.length; i++){
			for(int j = 0; j < dungeon[0].length; j++){
				if(dungeon[i][j]){
					g.fillRect(i*16 + xDif, j*16 + yDif, 16, 16);
				}
			}
		}
	}

}
