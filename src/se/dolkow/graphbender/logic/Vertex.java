package se.dolkow.graphbender.logic;

public class Vertex {
	public final int id;
	public int x, y;
	public int targetX, targetY;
	public int required;
	
	public Vertex(int id) {
		this.id = id;
	}

	public int getRequired() {
		return required;
	}
}