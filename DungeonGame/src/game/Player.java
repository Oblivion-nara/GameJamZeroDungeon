package game;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Player extends Entity {

	public Player(int xLocation, int yLocation, int maxHealth, int attack, int defence, float speed,
			BufferedImage sprite) {
		super(xLocation, yLocation, maxHealth, attack, defence, speed, sprite);
	}

	public float getSpeed() {
		return speed;
	}

	public Point2D.Double getLocation() {
		return new Point2D.Double(xLocation, yLocation);
	}

	public void update(float time) {
		super.update(time);

	}

	public BufferedImage draw() {

		return sprite;

	}

}
