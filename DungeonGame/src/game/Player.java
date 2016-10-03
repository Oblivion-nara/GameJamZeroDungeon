package game;

import java.awt.Graphics;

public class Player extends Entity {

	private int speed = 1;
	
	public Player(int maxHealth, int attack, int defence) {
		super(maxHealth, attack, defence);
	}

	public void update(){
		super.update();
		
	}
	
	public void draw(Graphics g){
		super.draw(g);
		
	}
	
	public int getSpeed(){
		return speed;
	}
	
	
}
