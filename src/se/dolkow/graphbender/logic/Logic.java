package se.dolkow.graphbender.logic;

import java.util.Random;

public class Logic {
	private final Vertex[] vertices;
	private final boolean[][] connected;
	
	public Logic(final int nVertices) {
		vertices = new Vertex[nVertices];
		connected = new boolean[nVertices][nVertices];
		for (int i=0; i<nVertices; ++i) {
			vertices[i] = new Vertex(i);
			connected[i][i] = true;
		}
		
		// randomize "required" numbers by connecting vertices (but not really).
		Random r = new Random();
		for (int i=0; i<nVertices; ++i) {
			for (int j=0; j<i; ++j) {
				if (r.nextInt(3) == 0) {
					getVertex(i).required += 1;
					getVertex(j).required += 1;
				}
			}
		}
		// a final pass to make sure that all vertices require at least one connection
		for (int i=0; i<nVertices; ++i) {
			final Vertex v = getVertex(i);
			if (v.required == 0) {
				int j;
				do {
					j = r.nextInt(nVertices);
				} while (j == i);
				getVertex(j).required += 1;
				v.required += 1;
			}
		}
	}

	public int getVertexCount() {
		return vertices.length;
	}

	public Vertex getVertex(int id) {
		return vertices[id];
	}
	
	public boolean areConnected(int id1, int id2) {
		return connected[id1][id2];
	}
	
	public void connect(int id1, int id2) {
		connected[id1][id2] = true;
		connected[id2][id1] = true;
	}
}
