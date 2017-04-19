package cliente;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import serial.Mensaje;

public class LogicaCliente implements Observer {

	private PApplet app;
	private Comunicacion red;
	private int id;
	private final String GROUP_ADDRESS;

	private Item item;

	private int pantalla;
	private PImage[] img;
	private int[][] color;
	private PFont fuente;
	private int animMsg;

	private int actualX, actualY;

	private boolean start, check, action, online;

	private int tranquilo;
	private PImage[] bebe;

	private String opcionNo, opcionSi;
	private boolean elegido;

	public LogicaCliente(PApplet app) {
		super();
		this.app = app;

		red = new Comunicacion();
		new Thread(red).start();

		red.addObserver(this);

		GROUP_ADDRESS = red.getGroupAddress();
		id = red.getId() - 1;

		try {
			red.enviar(new Mensaje(id, "conectado"), GROUP_ADDRESS);
		} catch (IOException e) {
			e.printStackTrace();
		}

		pantalla = 0;
		// start = true;

		fuente = app.loadFont("Dosis-Medium-62.vlw");

		img = new PImage[10];

		img[0] = app.loadImage("dove/mano.png");
		img[1] = app.loadImage("markeLogo.png");

		item = new Item(app, img[0]);
		item.mover(app.width / 2, app.height / 2);

		bebe = new PImage[15];

		for (int i = 0; i < bebe.length; i++) {
			bebe[i] = app.loadImage("dove/bebe_Atras" + i + ".png");
		}

		opcionNo = "No lo sé,\nen otra ocasión";
		opcionSi = "Si, con todo\nel gusto";

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

		animMsg = 0;

	}

	public void ejecutar() {
		app.background(0);
		app.imageMode(3);
		app.image(img[1], app.width / 2, app.height / 2- 50);
		app.textFont(fuente);
		app.textSize(20);
		app.textAlign(3);

		app.fill(200, 60, 60);

		if (!online) {
			app.text("Faltan usuarios", app.width / 2, (app.height / 2) + 200);
		} else {
			app.fill(0, 255, 0);
			app.text("Usuarios minimos disponibles", app.width / 2, (app.height / 2) + 200);
			app.fill(255);
		}

		String mensajeCarga = "Esperando...";
		char[] msgCarga = mensajeCarga.toCharArray();
		String mensajeAnimado = "";

		for (int i = 0; i < animMsg; i++) {
			mensajeAnimado += msgCarga[i];
		}

		app.text(mensajeAnimado, app.width / 2, app.height / 2 + 180);

		if (app.frameCount % 12 == 0 && animMsg < mensajeCarga.toCharArray().length) {
			animMsg++;
		} else if (animMsg == mensajeCarga.toCharArray().length) {
			animMsg = 0;
		}

		if (start) {
			app.background(color[pantalla][0], color[pantalla][1], color[pantalla][2]);
			switch (pantalla) {
			case 0:
				item.animar();

				if (check && !action) {
					if (app.mouseX != actualX && app.mouseY != actualY) {
						tranquilo++;
						actualX = app.mouseX;
						actualY = app.mouseY;
						System.out.println(tranquilo);
					}

					app.image(img[0], app.mouseX, app.mouseY);

					if (tranquilo == 100 && !action) {
						try {
							red.enviar(new Mensaje(id, "dove"), GROUP_ADDRESS);
						} catch (IOException e) {
							e.printStackTrace();
						}
						action = true;
					}
				}

				break;
			case 1:
				app.textAlign(3);
				app.textSize(42);
				app.textLeading(42);
				if (!elegido) {
					app.fill(120, 10, 10);
					if (zonaSensible(140, 200, 400, 300)) {
						app.fill(255);
					}
					app.text(opcionNo, app.width / 3, app.height / 2);

					app.fill(120, 10, 10);
					if (zonaSensible(415, 200, 645, 300)) {
						app.fill(255);
					}

					app.text(opcionSi, (app.width / 3) * 2, app.height / 2);
				} else {
					app.text("¡Gracias por tu respuesta!", app.width / 2, app.height / 2);
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

	public void click() {
		if (app.mousePressed) {
			if (item.validar(app.mouseX, app.mouseY)) {
				check = true;
			}

			if (pantalla == 1) {
				System.out.println(app.mouseX + ":" + app.mouseY);
				if (zonaSensible(140, 200, 400, 300)) {
					elegido = true;
				}

				if (zonaSensible(415, 200, 645, 300)) {
					elegido = true;
					try {
						red.enviar(new Mensaje(id, "cafeSi"), GROUP_ADDRESS);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public boolean zonaSensible(int x1, int y1, int x2, int y2) {
		if (app.mouseX > x1 && app.mouseX < x2 && app.mouseY > y1 && app.mouseY < y2) {
			return true;
		}
		return false;
	}

	public void release() {
		check = false;
	}

	@Override
	public void update(Observable o, Object arg) {

		if (arg instanceof Mensaje) {
			Mensaje m = (Mensaje) arg;

			if (m.getMsg().contains("comenzar")) {
				start = true;
				item.setAnimacion(bebe);
			}

			if (m.getAutor() == 2) {
				online = true;
			}

			if (m.getAutor() == 0) {
				if (m.getMsg().contains("next")) {
					pantalla++;
					action = false;
					item.mover(300, app.height / 2);
				}
			}

			if (m.getMsg().contains("win")) {

			}
		}

	}
}
