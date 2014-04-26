package se.dolkow.graphbender.scene;

import se.dolkow.graphbender.logic.Logic;
import se.dolkow.graphbender.logic.Vertex;
import android.graphics.Canvas;

public abstract class AbstractScenery implements Scenery {

	@Override
    public void draw(Canvas c, Logic logic, int touchX, int touchY) {
		drawBackground(c);
	    final int N = logic.getVertexCount();
	    for (int i=0; i<N; ++i) {
	    	final Vertex vi = logic.getVertex(i);
	    	for (int j=0; j<i; ++j) {
	    		if (logic.areConnected(i, j)) {
	    			drawEdge(c, vi, logic.getVertex(j));
	    		}
	    	}
	    	if (vi.selected) {
	    		drawTargetLine(c, vi, touchX, touchY);
	    	}
	    	drawVertex(c, vi);
	    }
    }
	
	protected abstract void drawBackground(Canvas c);
	protected abstract void drawEdge(Canvas c, Vertex v1, Vertex v2);
	protected abstract void drawTargetLine(Canvas c, Vertex v, int touchX, int touchY);
	protected abstract void drawVertex(Canvas c, Vertex v);

}
