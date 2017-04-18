package serial;

import java.io.Serializable;

public class Mensaje implements Serializable{
	
	private int autor;
	private String msg;
	
	public Mensaje(int autor, String msg) {
		super();
		this.autor = autor;
		this.msg = msg;
	}

	public int getAutor() {
		return autor;
	}

	public void setAutor(int autor) {
		this.autor = autor;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
