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

	private boolean start, check, action, online;

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

		img = new PImage[10];

		img[0] = app.loadImage("spoon.png");

		item = new Item(app, img[0]);
		item.mover(300, app.height / 2);

		color = new int[6][3];

		color[0][0] = 180;
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

		animMsg = 0;

	}

	public void ejecutar() {
		app.background(0);
		// app.textFont(fuente);
		app.textAlign(3);

		if (!online) {
			app.text("Faltan usuarios", app.width / 2, (app.height / 2) + 20);
		} else {
			app.fill(0, 255, 0);
			app.text("Usuarios minimos disponibles", app.width / 2, (app.height / 2) + 20);
			app.fill(255);
		}

		String mensajeCarga = "Esperando...";
		char[] msgCarga = mensajeCarga.toCharArray();
		String mensajeAnimado = "";

		for (int i = 0; i < animMsg; i++) {
			mensajeAnimado += msgCarga[i];
		}

		app.text(mensajeAnimado, app.width / 2, app.height / 2);

		if (app.frameCount % 12 == 0 && animMsg < mensajeCarga.toCharArray().length) {
			animMsg++;
		} else if (animMsg == mensajeCarga.toCharArray().length) {
			animMsg = 0;
		}

		if (start) {
			app.background(color[pantalla][0], color[pantalla][1], color[pantalla][2]);
			switch (pantalla) {
			case 0:
				if (check) {
					item.mover(app.mouseX, app.height / 2);
				}

				if (item.getX() <= 850 && action == false) {
					try {
						red.enviar(new Mensaje(id, "accion"), GROUP_ADDRESS);
					} catch (IOException e) {
						e.printStackTrace();
					}
					action = true;
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
			item.pintar();
		}
	}

	public void click() {
		if (app.mousePressed) {

			switch (pantalla) {
			case 0:
				if (item.validar(app.mouseX, app.mouseY)) {
					check = true;
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

	public void release() {
		check = false;
	}

	@Override
	public void update(Observable o, Object arg) {

		if (arg instanceof Mensaje) {
			Mensaje m = (Mensaje) arg;

			if (m.getMsg().contains("comenzar")) {
				start = true;
				System.out.println(id);
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
