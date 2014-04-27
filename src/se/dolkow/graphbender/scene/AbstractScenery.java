package se.dolkow.graphbender.scene;

import se.dolkow.graphbender.logic.Logic;
import se.dolkow.graphbender.logic.Vertex;
import android.graphics.Canvas;

public abstract class AbstractScenery implements Scenery {
	final VertexData vd = new VertexData();

	@Override
    public void draw(Canvas c, Logic logic, int touchX, int touchY, int selected, int hovered) {
	    final int N = logic.getVertexCount();
	    if (selected != -1) {
	    	
	    }
	    for (int i=0; i<N; ++i) {
	    	final Vertex vi = logic.getVertex(i);
	    	for (int j=i+1; j<N; ++j) {
	    		if (logic.areConnected(i, j)) {
	    			drawEdge(c, vi, logic.getVertex(j));
	    		}
	    	}
	    	vd.selected    = (selected == vi.id);
	    	vd.hovered     = (hovered == vi.id);
	    	vd.connectable = false;
	    	vd.unconnectable = vi.required == 0;
	    	if (selected >= 0 && hovered >= 0) {
	    		vd.connectable = logic.areConnectable(selected, hovered);
	    		vd.unconnectable = !vd.connectable;
	    	}
	    	if (vd.selected) {
	    		drawTargetLine(c, vi, touchX, touchY);
	    	}
	    	drawVertex(c, vi, vd);
	    }
    }
	
	protected final class VertexData {
		boolean selected; // true iff this vertex is the origin of an ongoing connection attempt
		boolean hovered;  // true iff this vertex is the target of an ongoing connection attempt
		boolean unconnectable; // true iff the ongoing connection attempt is impossible
		boolean connectable;   // true iff the ongoing connection attempt is possible
	}
	
	protected abstract void drawEdge(Canvas c, Vertex v1, Vertex v2);
	protected abstract void drawTargetLine(Canvas c, Vertex v, int touchX, int touchY);
	protected abstract void drawVertex(Canvas c, Vertex v, VertexData vd);

}
