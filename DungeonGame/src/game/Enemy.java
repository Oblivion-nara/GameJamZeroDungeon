package game;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import utils.MathHelper;
import utils.Sound;

public class Enemy extends Entity{
	
	public Enemy(int xLocation, int yLocation, int maxHealth, int attack, int defence, float speed, BufferedImage[][] sprite){
		super(xLocation, yLocation, maxHealth, attack, defence, speed, sprite);
	}
	
	public void update(float time,Point2D.Double playerLocation){
		super.update(time);
		Point2D.Double move = MathHelper.getPoint2D(new Point2D.Double(xLocation,yLocation), playerLocation, speed * time, 0.5);
		xLocation += move.x;
		yLocation += move.y;
	}
	
	public BufferedImage draw(){
		return sprite[currentX][currentY];
	}
	
	public void playSound(){
		int chance = Main.random.nextInt(100);
		if(chance < 3){
			Sound.ghost.play();
		}else if(chance < 6){
			Sound.ghostLaughter.play();
		}
	}
	
}
