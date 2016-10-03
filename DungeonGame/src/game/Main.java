package game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.VolatileImage;

import javax.swing.JFrame;

public class Main extends JFrame{

	private boolean running;
	private Dungeon dungeon;
	private VolatileImage offimage;
	private Graphics g;

	public static int width, height;
	
	private void run() {
		
		init();
		
		long beforeTime, afterTime, deltaTime = 0;;
		
		while (running) {
			beforeTime = System.nanoTime();
			update(deltaTime);
			if (running) {
				draw();
			}
			afterTime = System.nanoTime();
			deltaTime = afterTime - beforeTime;
		}
	}

	private void init() {
		running = true;
		dungeon = new Dungeon();
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		width = dim.width;
		height = dim.height;
		
		g = this.getGraphics();
		
		this.setTitle("Dungeon Game");
		this.setSize(dim);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setUndecorated(true);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setVisible(true);
		
		
	}

	private void update(long nanoTime) {
		dungeon.update(nanoTime);
	}

	private void draw() {
		
		Graphics offGraphics = offimage.createGraphics();
		
		dungeon.draw(offGraphics);
		
		g.drawImage(offimage, 0, 0, width,height,null);
	}

	public static void main(String[] args) {
		Main main = new Main();
		main.run();
	}

}
