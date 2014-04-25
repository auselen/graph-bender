package se.dolkow.graphbender.logic;

public class Vertex {
	int x, y;
	int required;
	public final int id;
	
	public Vertex(int id) {
		this.id = id;
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getRequired() {
		return required;
	}
}