package se.dolkow.graphbender.layout;

import android.util.Log;
import se.dolkow.graphbender.Metric;
import se.dolkow.graphbender.logic.Logic;
import se.dolkow.graphbender.logic.Vertex;

public class LineLayout implements Layout {
	private final int MARGIN = (int)(50 * Metric.SCALE);
	private static final int SPRITE_W = 96;
	private static final int SPACE = 14;

	@Override
	public void updateDesiredPositions(Logic logic, int width, int height) {
	    final int N = logic.getVertexCount();
	    
	    final int cols = Math.max(1, 1 + (width - SPRITE_W - 2*MARGIN) / (SPACE + SPRITE_W));
	    
	    for (int r=0; r<(int)Math.round(Math.ceil(N/(double)cols)); ++r) {
	    	final int nOnLine = Math.min(N-r*cols, cols);
	    	final int lineStart = (width - nOnLine*(SPRITE_W+SPACE) + SPACE)/2;
	    	for (int c=0; c<nOnLine; ++c) {
	    		final int i = r * cols + c;
	    		final Vertex v = logic.getVertex(i);
	    		v.targetX = lineStart + c * (SPRITE_W + SPACE);
	    		Log.i("Hej", "index:" + i + " " + v.targetX);
	    		v.targetY = height/3 + r * (SPRITE_W + SPACE);
	    	}
	    }
	}

}
