package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class Entity {

	protected int health, maxHealth, attack, defence;
	protected float xLocation, yLocation, speed;
	protected String name;
	protected BufferedImage Sprite;

	public Entity(int xLocation, int yLocation, int maxHealth, int attack, int defence, float speed) {
		this.xLocation = xLocation;
		this.yLocation = yLocation;
		this.maxHealth = this.health = maxHealth;
		this.attack = attack;
		this.defence = defence;
		this.speed = speed;
	}
	
	public float getX(){
		return xLocation;
	}
	
	public float getY(){
		return yLocation;
	}
	
	public void moveUp(float time){
		yLocation -= speed * time;
	}
	
	public void moveLeft(float time){
		xLocation -= speed * time;
	}
	
	public void moveDown(float time){
		yLocation += speed * time;
	}
	
	public void moveRight(float time){
		xLocation += speed * time;
	}
	
	public void moveUL(float time){
		xLocation -= (speed * time) / Math.sqrt(2);
		yLocation -= (speed * time) / Math.sqrt(2);
	}
	
	public void moveUR(float time){
		xLocation += (speed * time) / Math.sqrt(2);
		yLocation -= (speed * time) / Math.sqrt(2);
	}
	
	public void moveDL(float time){
		xLocation -= (speed * time) / Math.sqrt(2);
		yLocation += (speed * time) / Math.sqrt(2);
	}
	
	public void moveDR(float time){
		xLocation += (speed * time) / Math.sqrt(2);
		yLocation += (speed * time) / Math.sqrt(2);
	}
	
	public void update(){
		
	}
	
	public void draw(Graphics g){
		
	}
	

}
