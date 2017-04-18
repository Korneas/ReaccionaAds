package servidor;

import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class LogicaServer implements Observer {

	private PApplet app;
	private ComunicacionServer red;
	private int id;
	private final String GROUP_ADDRESS;

	private int pantalla;
	private PImage[] img;
	private PImage[] logo;
	private int[][] color;
	private PFont fuente;

	private boolean start;

	public LogicaServer(PApplet app) {
		this.app = app;

		GROUP_ADDRESS = "";

		pantalla = 0;

		img = new PImage[10];

		color = new int[6][3];

		color[0][0] = 220;
		color[0][1] = 20;
		color[0][2] = 20;

		color[1][0] = 255;
		color[1][1] = 255;
		color[1][2] = 255;

		color[2][0] = 255;
		color[2][1] = 255;
		color[2][2] = 255;

		color[3][0] = 255;
		color[3][1] = 255;
		color[3][2] = 255;

		color[4][0] = 255;
		color[4][1] = 255;
		color[4][2] = 255;

		color[5][0] = 255;
		color[5][1] = 255;
		color[5][2] = 255;
	}

	public void ejecutar() {
		
		if (start) {
			app.background(color[pantalla][0], color[pantalla][1], color[pantalla][2]);
			switch (pantalla) {
			case 0:
				// app.image(img[0], 50, 50);
				break;
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			case 5:
				break;
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}
}