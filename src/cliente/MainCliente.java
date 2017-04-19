package cliente;

import processing.core.PApplet;

public class MainCliente extends PApplet{

		private LogicaCliente log;

		public static void main(String[] args) {
			System.setProperty("java.net.preferIPv4Stack", "true");
			PApplet.main("cliente.MainCliente");
		}

		@Override
		public void settings() {
			size(1000, 300);
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
		
		@Override
		public void mousePressed() {
			log.click();
		}
		
		@Override
		public void mouseReleased(){
			log.release();
		}
		
	}