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
	private Sound snd;

	private int pantalla;
	private PImage[] img;
	private PImage[] logo;
	private int[][] color;
	private PFont fuente;

	private Movie[] movies;

	private boolean start;

	private int dove;
	private int doveWinner;
	private boolean doveDone, llorar;
	private PImage[] bebe;
	private int doveAnim;
	private int doveTint;
	private int animBebe;
	private String doveMsg = "Ser mamá se lleva\na todas partes";

	public LogicaServer(PApplet app) {
		this.app = app;

		red = new ComunicacionServer();
		new Thread(red).start();

		red.addObserver(this);

		GROUP_ADDRESS = red.getGroupAddress();
		id = red.getId() - 1;

		snd = new Sound(app);

		pantalla = 0;
		fuente = app.loadFont("Dosis-Medium-62.vlw");

		movies = new Movie[5];

		// movies[0] = new Movie(app, "movie/movie.mov");
		// movies[1] = new Movie(app, "movie/movie.mov");
		// movies[2] = new Movie(app, "movie/movie.mov");
		// movies[3] = new Movie(app, "movie/movie.mov");
		// movies[4] = new Movie(app, "movie/movie.mov");

		img = new PImage[10];

		img[0] = app.loadImage("dove/dove_Logo.png");
		img[1] = app.loadImage("dove/heart.png");

		bebe = new PImage[15];

		for (int i = 0; i < bebe.length; i++) {
			bebe[i] = app.loadImage("dove/bebeAdelante" + i + ".png");
		}

		color = new int[6][3];

		color[0][0] = 220;
		color[0][1] = 240;
		color[0][2] = 255;

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

		app.textFont(fuente);

		if (start) {
			app.imageMode(3);
			app.background(color[pantalla][0], color[pantalla][1], color[pantalla][2]);
			switch (pantalla) {
			case 0:
				switch (dove) {
				case 0:

					if (app.frameCount % 900 == 0) {
						llorar = true;
					}

					if (llorar) {
						snd.triggerSample(1);
						llorar = false;
					}

					if (app.frameCount % 4 == 0 && animBebe < bebe.length - 1) {
						animBebe++;
					} else if (animBebe >= bebe.length - 1) {
						animBebe = 0;
					}

					app.tint(255, doveTint);
					app.image(bebe[animBebe], app.width / 2, (app.height / 2) - (int) (app.height * 0.08));
					if (doveTint < 255) {
						doveTint += 1;
					}
					app.tint(255, 255);

					if (doveDone) {
						snd.stopSample(1);
						doveTint = 0;
						dove = 1;
					}

					break;
				case 1:
					app.tint(255, doveTint);
					app.image(img[0], 180, 480);
					if (doveTint < 255) {
						doveTint += 3;
					}
					app.tint(255, 255);

					app.image(img[1], 820, 300);

					char[] doveArray = doveMsg.toCharArray();
					String doveMsgAnimado = "";

					for (int i = 0; i < doveAnim; i++) {
						doveMsgAnimado += doveArray[i];
					}

					if (app.frameCount % 4 == 0 && doveAnim < doveArray.length) {
						doveAnim++;
					}

					app.textAlign(PApplet.LEFT);
					app.fill(60, 60, 100);
					app.textLeading(60);
					app.text(doveMsgAnimado, 50, 610);
					app.fill(255);

					

					break;
				}
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

	// public void movieEvent(Movie m) {
	// m.read();
	// }

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof Mensaje) {
			Mensaje m = (Mensaje) arg;
			System.out.println("Tengo mensaje de: " + m.getAutor());

			if (m.getMsg().contains("comenzar")) {
				start = true;
				snd.reproducir();
				// snd.triggerSample(1);
				dove = 1;
				// movies[0].play();
			}

			if (m.getMsg().contains("dove") && !doveDone) {
				doveWinner = m.getAutor();
				doveDone = true;
				snd.triggerSample(0);
			}
		}
	}
}