package game;

import java.awt.Graphics;

public class Player extends Entity {

	
	public Player(int xLocation, int yLocation, int maxHealth, int attack, int defence, float speed) {
		super(xLocation, yLocation, maxHealth, attack, defence, speed);
	}
	
	public float getSpeed(){
		return speed;
	}

	public void update(){
		super.update();
		
	}
	
	public void draw(Graphics g){
		super.draw(g);
		
	}
	
	
}
