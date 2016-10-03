package game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import utils.InputHandler;

public class Main extends JFrame {

	private boolean running;
	private Dungeon dungeon;
	private Image offimage;
	private Graphics g;

	public static int width, height;
	public static InputHandler input;

	private void run() {

		init();

		long beforeTime, afterTime, deltaTime = 0;
		;

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
		dungeon = new Dungeon();
		this.add(dungeon);
		
		input = new InputHandler(this);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		width = dim.width;
		height = dim.height;

		offimage = this.createImage(width, height);
		g = this.getGraphics();

		this.setTitle("Dungeon Game");
		this.setSize(dim);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setUndecorated(true);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setVisible(true);

	}

	private void update(long nanoTime) {
		if (input.isKeyDown(KeyEvent.VK_ESCAPE)) {
			running = false;
		} else {
			dungeon.update(nanoTime);
		}
	}

	private void draw() {

		// offimage = this.createVolatileImage(width, height);

		dungeon.repaint();

		// g.drawImage(offimage, 0, 0, width,height,null);
	}

	public static void main(String[] args) {
		Main main = new Main();
		main.run();
	}

}
