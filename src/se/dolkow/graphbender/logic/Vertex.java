package se.dolkow.graphbender.logic;

public class Vertex {
	public final int id;
	public int x, y;
	int required;
	
	public Vertex(int id) {
		this.id = id;
	}

	public int getRequired() {
		return required;
	}
}