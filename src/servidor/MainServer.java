package servidor;

import cliente.LogicaCliente;
import processing.core.PApplet;

public class MainServer extends PApplet{

		private LogicaCliente log;

		public static void main(String[] args) {
			System.setProperty("java.net.preferIPv4Stack", "true");
			PApplet.main("servidor.MainServer");
		}

		@Override
		public void settings() {
//			size(1200, 600);
			fullScreen();
		}

		@Override
		public void setup() {
			log = new LogicaCliente(this);
		}

		@Override
		public void draw() {
			background(0);
			log.ejecutar();
		}
		
	}