package game;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Player extends Entity {

	private boolean moving;

	public Player(int xLocation, int yLocation, int maxHealth, int attack, int defence, float speed,
			BufferedImage[][] sprite) {
		super(xLocation, yLocation, maxHealth, attack, defence, speed, sprite);
		moving = false;
	}

	public float getSpeed() {
		return speed;
	}
	
	public boolean attacking(){
		return shouldAttack;
	}

	public void moving() {
		moving = true;
	}

	public Point2D.Double getLocation() {
		return new Point2D.Double(xLocation, yLocation);
	}

	public void moveUp(float time) {
		yLocation -= speed * time;
		/*rectUp = new Rectangle(rectUp.x, rectUp.y - (int) (speed * time), rectUp.width, rectUp.height);
		rectLeft = new Rectangle(rectLeft.x, rectLeft.y - (int) (speed * time), rectLeft.width, rectLeft.height);
		rectDown = new Rectangle(rectDown.x, rectDown.y - (int) (speed * time), rectDown.width, rectDown.height);
		rectRight = new Rectangle(rectRight.x, rectRight.y - (int) (speed * time), rectRight.width, rectRight.height);*/
		currentX = 1;
		attackVector.setLocation(0, -1);
	}

	public void moveLeft(float time) {
		xLocation -= speed * time;
		/*rectUp = new Rectangle(rectUp.x - (int) (speed * time), rectUp.y, rectUp.width, rectUp.height);
		rectLeft = new Rectangle(rectLeft.x - (int) (speed * time), rectLeft.y, rectLeft.width, rectLeft.height);
		rectDown = new Rectangle(rectDown.x - (int) (speed * time), rectDown.y, rectDown.width, rectDown.height);
		rectRight = new Rectangle(rectRight.x - (int) (speed * time), rectRight.y, rectRight.width, rectRight.height);*/
		currentX = 3;
		attackVector.setLocation(-1,0);
	}

	public void moveDown(float time) {
		yLocation += speed * time;
		/*rectUp = new Rectangle(rectUp.x, rectUp.y + (int) (speed * time), rectUp.width, rectUp.height);
		rectLeft = new Rectangle(rectLeft.x, rectLeft.y + (int) (speed * time), rectLeft.width, rectLeft.height);
		rectDown = new Rectangle(rectDown.x, rectDown.y + (int) (speed * time), rectDown.width, rectDown.height);
		rectRight = new Rectangle(rectRight.x, rectRight.y + (int) (speed * time), rectRight.width, rectRight.height);*/
		currentX = 0;
		attackVector.setLocation(0,1);
	}

	public void moveRight(float time) {
		xLocation += speed * time;
		/*rectUp = new Rectangle(rectUp.x + (int) (speed * time), rectUp.y, rectUp.width, rectUp.height);
		rectLeft = new Rectangle(rectLeft.x + (int) (speed * time), rectLeft.y, rectLeft.width, rectLeft.height);
		rectDown = new Rectangle(rectDown.x + (int) (speed * time), rectDown.y, rectDown.width, rectDown.height);
		rectRight = new Rectangle(rectRight.x + (int) (speed * time), rectRight.y, rectRight.width, rectRight.height);*/
		currentX = 2;
		attackVector.setLocation(1,0);
	}

	public void moveUL(float time) {
		xLocation -= (speed * time) / Math.sqrt(2);
		yLocation -= (speed * time) / Math.sqrt(2);
		currentX = 1;
		attackVector.setLocation(-1, -1);
	}

	public void moveUR(float time) {
		xLocation += (speed * time) / Math.sqrt(2);
		yLocation -= (speed * time) / Math.sqrt(2);
		currentX = 1;
		attackVector.setLocation(1, -1);
	}

	public void moveDL(float time) {
		xLocation -= (speed * time) / Math.sqrt(2);
		yLocation += (speed * time) / Math.sqrt(2);
		currentX = 0;
		attackVector.setLocation(-1,1);
	}

	public void moveDR(float time) {
		xLocation += (speed * time) / Math.sqrt(2);
		yLocation += (speed * time) / Math.sqrt(2);
		currentX = 0;
		attackVector.setLocation(1,1);
	}

	public void attack() {
		if (System.currentTimeMillis() > attackCooldown) {
			moving = true;
			currentY = 3;
			animationTimer = System.currentTimeMillis() + changeTimer;
			attackCooldown = System.currentTimeMillis() + changeTimer*2;
			shouldAttack = true;
		}

	}

	public void update(float time) {
		if (moving) {
			super.update(time);
		} else if (System.currentTimeMillis() > animationTimer) {
			currentY = 0;
			shouldAttack = false;
		}

	}

	public BufferedImage draw() {

		moving = false;
		return sprite[currentX][currentY];

	}

}
