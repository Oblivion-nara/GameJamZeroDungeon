package game;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import utils.MathHelper;
import utils.Sound;

public class Enemy extends Entity {

	public Enemy(int xLocation, int yLocation, int maxHealth, int attack, int defence, float speed,
			BufferedImage[][] sprite) {
		super(xLocation, yLocation, maxHealth, attack, defence, speed, sprite);
	}

	public void update(float time, Point2D.Double playerLocation) {
		super.update(time);
		Point2D.Double move = MathHelper.getPoint2D(new Point2D.Double(xLocation, yLocation), playerLocation,
				speed * time, 0);
		xLocation += move.x;
		yLocation += move.y;
		double angle = Math.atan(move.y / move.x);
		if (move.x < 0) {
			angle = -angle;
		}
		if (angle > Math.PI / 4) {
			currentX = 0;
		} else if (angle < -Math.PI / 4) {
			currentX = 1;
		} else if (move.x < 0) {
			currentX = 3;
		} else if (move.x > 0) {
			currentX = 2;
		} else {
			currentX = 0;
		}
		playSound();
	}

	public BufferedImage draw() {
		return sprite[currentX][currentY];
	}

	public void playSound() {
		int chance = Main.random.nextInt(10000);
		if (chance < 3) {
			Sound.ghost.play();
		} else if (chance < 6) {
			Sound.ghostLaughter.play();
		}
	}

}
