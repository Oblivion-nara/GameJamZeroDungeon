package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Player extends Entity {

	public Player(int xLocation, int yLocation, int maxHealth, int attack, int defence, float speed,
			BufferedImage sprite) {
		super(xLocation, yLocation, maxHealth, attack, defence, speed, sprite);
	}

	public float getSpeed() {
		return speed;
	}

	public void update(float time) {
		super.update(time);

	}

	public BufferedImage draw() {

		return sprite;

	}

}
