package game;

public class Main {

	private boolean running;

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
	}

	private void update(long nanoTime) {

	}

	private void draw() {

	}

	public static void main(String[] args) {
		Main main = new Main();
		main.run();
	}

}
