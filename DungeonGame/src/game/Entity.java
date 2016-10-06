package game;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class Entity {

	protected boolean shouldAttack;
	protected int health, maxHealth, attack, defence, currentX, currentY;
	protected float xLocation, yLocation, speed;
	protected long animationTimer, changeTimer, attackCooldown;
	protected String name;
	protected Point size, attackVector;
	protected BufferedImage[][] sprite;
	protected Rectangle rectUp, rectLeft, rectDown, rectRight;
	protected boolean collideUp, collideLeft, collideDown, collideRight;

	public Entity(int xLocation, int yLocation, int maxHealth, int attack, int defence, float speed,
			BufferedImage[][] sprite) {
		this.xLocation = xLocation;
		this.yLocation = yLocation;
		this.maxHealth = this.health = maxHealth;
		this.attack = attack;
		this.defence = defence;
		this.speed = speed;
		this.sprite = sprite;
		currentX = currentY = 0;
		shouldAttack = false;
		attackVector = new Point(0, 0);
		this.size = new Point(sprite[0][0].getWidth(), sprite[0][0].getHeight());
		changeTimer = 500;
		attackCooldown = System.currentTimeMillis() + changeTimer;
		animationTimer = System.currentTimeMillis() + changeTimer;
		rectUp = new Rectangle(xLocation + size.x / 8, yLocation, 6 * size.x / 8, size.y / 8);
		rectLeft = new Rectangle(xLocation, yLocation + size.y / 8, size.x / 8, 6 * size.y / 8);
		rectDown = new Rectangle(xLocation + size.x / 8, yLocation + 7 * size.y / 8, 6 * size.x / 8, size.y / 8);
		rectRight = new Rectangle(xLocation + 7 * size.x / 8, yLocation + size.y / 8, size.x / 8, 6 * size.y / 8);
	}

	public float getX() {
		return xLocation;
	}

	public float getY() {
		return yLocation;
	}

	public Point getSize() {
		return size;
	}
	
	public void kill(){
		health = 0;
	}

	public void update(float time) {

		if (System.currentTimeMillis() > animationTimer) {
			animationTimer += changeTimer;
			currentY++;
			if (currentY > 2) {
				currentY = 0;
			}
		}
	}

	public BufferedImage draw() {
		return sprite[currentX][currentY];

	}
	
	public boolean isAlive(){
		return health > 0;
	}
	
	public boolean collideUp(Rectangle rect, int sizeMult){
		Rectangle newRect = new Rectangle(rectUp.x - (rectUp.width * sizeMult / 2), rectUp.y - (rectUp.height * sizeMult / 2), rectUp.width * sizeMult, rectUp.height * sizeMult);
		if(rect.intersects(newRect)){
			System.out.println("collision up");
			return true;
		}
		return false;
	}
	
	public boolean collideLeft(Rectangle rect, int sizeMult){
		Rectangle newRect = new Rectangle(rectLeft.x - (rectLeft.width * sizeMult / 2), rectLeft.y - (rectLeft.height * sizeMult / 2), rectLeft.width * sizeMult, rectLeft.height * sizeMult);
		if(rect.intersects(newRect)){
			System.out.println("collision Left");
			return true;
		}
		return false;
	}
	
	public boolean collideDown(Rectangle rect, int sizeMult){
		Rectangle newRect = new Rectangle(rectDown.x - (rectDown.width * sizeMult / 2), rectDown.y - (rectDown.height * sizeMult / 2), rectDown.width * sizeMult, rectDown.height * sizeMult);
		if(rect.intersects(newRect)){
			System.out.println("collision Down");
			return true;
		}
		return false;
	}
	
	public boolean collideRight(Rectangle rect, int sizeMult){
		Rectangle newRect = new Rectangle(rectRight.x - (rectRight.width * sizeMult / 2), rectRight.y - (rectRight.height * sizeMult / 2), rectRight.width * sizeMult, rectRight.height * sizeMult);
		if(rect.intersects(newRect)){
			System.out.println("collision Right");
			return true;
		}
		return false;
	}

}
