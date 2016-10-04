package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JFrame;

import utils.InputHandler;

public class Main extends JFrame {

	private boolean running;
	private Dungeon dungeon;
	private BufferedImage offimage;
	private Graphics g;

	public static int width, height;
	public static Random random;
	public static InputHandler input;

	private void run() {

		init();

		long beforeTime, afterTime, deltaTime = 0;

		while (running) {
			beforeTime = System.nanoTime();
			update(deltaTime);
			if (running) {
				draw();
			}
			afterTime = System.nanoTime();
			deltaTime = afterTime - beforeTime;

		}
		this.dispose();
	}

	private void init() {
		running = true;
		random = new Random();
		
		dungeon = new Dungeon();
		input = new InputHandler(this);


		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		width = dim.width;
		height = dim.height;

		this.setTitle("Dungeon Game");
		this.setSize(dim);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setUndecorated(true);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setVisible(true);

		offimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g = this.getGraphics();

	}

	private void update(long nanoTime) {
		if (input.isKeyDown(KeyEvent.VK_ESCAPE)) {
			running = false;
		} else {
			Float timeS = (float) nanoTime / 1000000000;
			dungeon.update(timeS);
		}
	}

	private void draw() {

		Graphics offgraphics = offimage.getGraphics();

		offgraphics.setColor(Color.BLACK);
		//offgraphics.fillRect(0, 0, width, height);
		dungeon.draw(offgraphics);

		g.drawImage(offimage, 0, 0, width, height, null);
	}

	public static void main(String[] args) {
		Main main = new Main();
		main.run();
	}

}
