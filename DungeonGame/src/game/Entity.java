package game;

import java.awt.Point;
import java.awt.image.BufferedImage;

public abstract class Entity {

	protected boolean shouldAttack;
	protected int health, maxHealth, attack, defence, currentX, currentY;
	protected float xLocation, yLocation, speed;
	protected long animationTimer, changeTimer, attackCooldown;
	protected String name;
	protected Point size, attackVector;
	protected BufferedImage[][] sprite;

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

}
