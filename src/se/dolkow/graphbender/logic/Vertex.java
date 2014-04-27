package se.dolkow.graphbender.logic;

public class Vertex {
	public final int id;
	public int x, y;
	public int targetX, targetY;
	public int required;
	public int personality;
	
	public Vertex(int id) {
		this.id = id;
		this.personality = hashCode() >> 5;
	}

	public int getRequired() {
		return required;
	}
}