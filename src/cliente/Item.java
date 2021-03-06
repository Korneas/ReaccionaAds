package cliente;

import processing.core.PApplet;
import processing.core.PImage;

public class Item {

	private PApplet app;
	private int x, y;
	private PImage display;
	private PImage[] animacion;
	private int rate;

	public Item(PApplet app, PImage display) {
		this.app = app;
		this.display = display;
	}

	public void pintar() {
		app.imageMode(3);
		app.image(display, x, y);
	}
	
	public void animar(){
		app.imageMode(3);
		if(app.frameCount%4==0 && rate < animacion.length-1){
			rate++;
		} else if(rate>=animacion.length-1){
			rate=0;
		}
		app.image(animacion[rate], x, y);
	}

	public void mover(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean validar(int x2,int y2){
		if(PApplet.dist(x, y, x2, y2)<150){
			return true;
		}
		return false;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public PImage getDisplay() {
		return display;
	}

	public void setDisplay(PImage display) {
		this.display = display;
	}

	public PImage[] getAnimacion() {
		return animacion;
	}

	public void setAnimacion(PImage[] animacion) {
		this.animacion = animacion;
	}

}
