package game;

public class Main {

	private boolean running;

	private void run() {
		
		init();
		
		while (running) {
			update();
			if (running) {
				draw();
			}
		}
	}

	private void init() {
		running = true;
	}

	private void update() {

	}

	private void draw() {

	}

	public static void main(String[] args) {
		Main main = new Main();
		main.run();
	}

}
