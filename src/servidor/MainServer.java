package servidor;

import processing.core.PApplet;

public class MainServer extends PApplet {

	private LogicaServer log;

	public static void main(String[] args) {
		System.setProperty("java.net.preferIPv4Stack", "true");
		PApplet.main("servidor.MainServer");
	}

	@Override
	public void settings() {
		size(1200, 720);
		// fullScreen();
	}

	@Override
	public void setup() {
		log = new LogicaServer(this);
	}

	@Override
	public void draw() {
		background(0);
		log.ejecutar();
	}

	@Override
	public void keyPressed() {
		log.tecla();
	}

}