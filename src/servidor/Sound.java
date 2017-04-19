package servidor;

import ddf.minim.AudioPlayer;
import ddf.minim.AudioSample;
import ddf.minim.Minim;
import processing.core.PApplet;

public class Sound {

	private Minim minim;

	private AudioPlayer[] musica;
	private AudioSample[] samples;

	private AudioPlayer cancion;

	public Sound(PApplet app) {

		minim = new Minim(app);

		musica = new AudioPlayer[10];
		samples = new AudioSample[10];

		musica[0] = minim.loadFile("musica/Young_And_Old_Know_Love.mp3", 1024);

		samples[0] = minim.loadSample("musica/Boy_Laugh.mp3", 1024);
		samples[1] = minim.loadSample("musica/Bebe_Llorar.mp3", 1024);

		cancion = musica[0];

		cancion.setBalance(0);
		cancion.setGain(-20);
	}

	public void reproducir() {
		if (!cancion.isPlaying()) {
			cancion.play();
		}

		if (cancion.position() == cancion.length()) {
			cancion.pause();
			cancion.rewind();
		}
	}

	public void parar() {
		cancion.pause();
		cancion.rewind();
	}
	
	public void triggerSample(int num){
		samples[num].trigger();
	}
	
	public void stopSample(int num){
		samples[num].stop();
	}

	public void setCancion(int num) {
		cancion.pause();
		cancion.rewind();

		cancion = musica[num];
	}
}
