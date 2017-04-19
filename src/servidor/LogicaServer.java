package servidor;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.core.PShape;
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
	private String doveMsg = "Ser mama se lleva\na todas partes";
	private PShape heart;
	private float doveRotar, doveRotar2;
	private boolean doveMov, doveMov2;
	private float f, var;

	private int nescafe;
	private boolean cafeGo;
	private int[] cafeWinner;
	private boolean[] cafeDone;
	private int cafeAnim, cafeTint, cafe;
	private String cafeMsg = "¿Te arriesgarias a tomar cafe\ncon un extraño?";

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

		heart = app.loadShape("dove/heartDove.svg");

		cafeDone = new boolean[2];

		color = new int[6][3];

		color[0][0] = 220;
		color[0][1] = 240;
		color[0][2] = 255;

		color[1][0] = 220;
		color[1][1] = 25;
		color[1][2] = 25;

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
					app.image(img[0], app.width / 2, 500);
					if (doveTint < 255) {
						doveTint += 3;
					}
					app.tint(255, 255);

					app.image(img[1], app.width / 2, 200);
					app.textAlign(3);
					app.textSize(25);
					app.fill(250, 150, 100);
					app.text("Ganador de 25% de descuento:\nCarrito #" + doveWinner, app.width / 2, 405);

					app.textSize(62);

					char[] doveArray = doveMsg.toCharArray();
					String doveMsgAnimado = "";

					for (int i = 0; i < doveAnim; i++) {
						doveMsgAnimado += doveArray[i];
					}

					if (app.frameCount % 4 == 0 && doveAnim < doveArray.length) {
						doveAnim++;
					}

					app.fill(60, 60, 100);
					app.textLeading(60);
					app.text(doveMsgAnimado, app.width / 2, 620);
					app.fill(255);

					f += 0.06;
					var = (PApplet.sin(f) * 20);

					app.pushMatrix();
					app.translate(app.width / 2 + 150, 250);
					app.rotate(doveRotar);
					app.shapeMode(3);
					app.shape(heart, 0, 0, (int) (heart.getWidth() * 0.4) + var, (int) (heart.getHeight() * 0.4) + var);

					if (doveRotar > 0.6) {
						doveMov = true;
					} else if (doveRotar < -0.6) {
						doveMov = false;
					}

					if (doveMov) {
						doveRotar -= 0.02;
					} else {
						doveRotar += 0.02;
					}
					app.popMatrix();

					app.pushMatrix();
					app.translate(app.width / 2 - 130, 300);
					app.rotate(doveRotar2);
					app.shapeMode(3);
					app.shape(heart, 0, 0, (int) (heart.getWidth() * 0.2) + var, (int) (heart.getHeight() * 0.2) + var);

					if (doveRotar2 > 0.6) {
						doveMov2 = true;
					} else if (doveRotar2 < -0.6) {
						doveMov2 = false;
					}

					if (doveMov2) {
						doveRotar2 -= 0.01;
					} else {
						doveRotar2 += 0.01;
					}
					app.popMatrix();

					break;
				}
				break;
			case 1:
				switch (nescafe) {
				case 0:
					app.textAlign(3);
					char[] cafeArray = cafeMsg.toCharArray();
					String cafeMsgAnimado = "";

					for (int i = 0; i < cafeAnim; i++) {
						cafeMsgAnimado += cafeArray[i];
					}

					if (app.frameCount % 4 == 0 && cafeAnim < cafeArray.length) {
						cafeAnim++;
					}

					if (cafeMsgAnimado.toCharArray().length == cafeArray.length && !cafeGo) {
						try {
							red.enviar(new Mensaje(id, "next"), GROUP_ADDRESS);
						} catch (IOException e) {
							e.printStackTrace();
						}
						cafeGo = true;
					}

					app.fill(255, cafeTint);
					if (cafeTint < 255) {
						cafeTint += 3;
					}
					app.textLeading(60);
					app.text(cafeMsgAnimado, app.width / 2, app.height / 2);

					break;
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
				}
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

				if (dove == 1) {
					pantalla = 1;
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
				pantalla = 1;
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