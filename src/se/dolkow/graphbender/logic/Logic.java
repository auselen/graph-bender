package se.dolkow.graphbender.logic;

import java.util.ArrayList;
import java.util.Random;

import android.util.Pair;

public class Logic {
	private final Vertex[] vertices;
	private final boolean[][] connected;
	private final ArrayList<LogicListener> listeners = new ArrayList<LogicListener>();
	
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

	public void addListener(LogicListener ll) {
		listeners.add(ll);
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
	
	public ArrayList<Pair<Vertex,Vertex>> getConnectedPairs() {
		return getConnectedPairs(new ArrayList<Pair<Vertex,Vertex>>());
	}
	
	public ArrayList<Pair<Vertex,Vertex>> getConnectedPairs(ArrayList<Pair<Vertex, Vertex>> fill) {
		for (int i=0; i<vertices.length; ++i) {
			for (int j=0; j<i; ++j) {
				if (areConnected(i, j)) {
					fill.add(new Pair<Vertex, Vertex>(getVertex(i), getVertex(j)));
				}
			}
		}
		return fill;
	}
	
	public void connect(int id1, int id2) {
		final Vertex v1 = getVertex(id1);
		final Vertex v2 = getVertex(id2);
		if (!areConnected(id1, id2) && v1.required > 0 && v2.required > 0) {
			connected[id1][id2] = true;
			connected[id2][id1] = true;
			v1.required -= 1;
			v2.required -= 1;
			for (LogicListener ll : listeners) {
				ll.onConnected(v1, v2);
			}
		}
	}

	public int getConnectionCount(int id) {
		final boolean connected[] = this.connected[id];
		int count = 0;
		for (int j=0; j < connected.length; ++j) {
			if (connected[j]) {
				count += 1;
			}
		}
		return count-1; // vertices are always connected to themselves
	}
	
	public int getConnectionCount(Vertex v) {
	    return getConnectionCount(v.id);
    }
	public boolean satisfied() {
		for (int i = 0; i < vertices.length; i++)
			if (vertices[i].required > 0)
				return false;
		return true;
	}

	public boolean satisfiable() {
		int remaining = 0;
		for (int i = 0; i < vertices.length; i++)
			if (vertices[i].required > 0)
				remaining++;
		return remaining != 1;
	}
}
