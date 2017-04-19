package servidor;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.video.*;
import serial.Mensaje;

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

	private Movie[] movies;

	private boolean start;

	public LogicaServer(PApplet app) {
		this.app = app;

		red = new ComunicacionServer();
		new Thread(red).start();

		GROUP_ADDRESS = red.getGroupAddress();
		id = red.getId() - 1;

		pantalla = 0;

		movies = new Movie[5];

		movies[0] = new Movie(app, "movie/movie.mov");
		movies[1] = new Movie(app, "movie/movie.mov");
		movies[2] = new Movie(app, "movie/movie.mov");
		movies[3] = new Movie(app, "movie/movie.mov");
		movies[4] = new Movie(app, "movie/movie.mov");

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
			app.imageMode(3);
			app.background(color[pantalla][0], color[pantalla][1], color[pantalla][2]);
			switch (pantalla) {
			case 0:
				app.image(movies[0], app.width / 2, app.height / 2);
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

	public void tecla() {
		if (app.keyPressed) {
			if (app.key == ' ') {
				if (pantalla == 0) {
					try {
						red.enviar(new Mensaje(id, "comenzar"), GROUP_ADDRESS);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				if (pantalla != 0) {
					try {
						red.enviar(new Mensaje(id, "next"), GROUP_ADDRESS);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void movieEvent(Movie m) {
		m.read();
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof Mensaje) {
			Mensaje m = (Mensaje) arg;

			if (m.getMsg().contains("comenzar")) {
				start = true;
				movies[0].play();
			}
		}
	}
}