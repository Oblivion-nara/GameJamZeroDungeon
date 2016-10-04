package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Enemy extends Entity{

	public Enemy(int xLocation, int yLocation, int maxHealth, int attack, int defence, float speed, BufferedImage sprite){
		super(xLocation, yLocation, maxHealth, attack, defence, speed, sprite);
	}
	
	public void update(float time){
		
	}
	
	public BufferedImage draw(){
		return sprite;
	}
	
}
