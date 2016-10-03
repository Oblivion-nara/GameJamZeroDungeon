package game;

import java.awt.Graphics;

public abstract class Entity {

	protected int health, maxHealth, attack, defence;
	protected float xLocation, yLocation;
	protected String name;

	public Entity(int maxHealth, int attack, int defence) {
		this.maxHealth = this.health = maxHealth;
		this.attack = attack;
		this.defence = defence;
	}
	
	public float getX(){
		return xLocation;
	}
	
	public float getY(){
		return yLocation;
	}
	
	public void update(){
		
	}
	
	public void draw(Graphics g){
		
	}
	

}
