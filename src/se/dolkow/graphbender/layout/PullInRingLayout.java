package se.dolkow.graphbender.layout;

import android.util.Log;
import se.dolkow.graphbender.logic.Logic;
import se.dolkow.graphbender.logic.Vertex;

import static se.dolkow.graphbender.util.Utils.linterp;

public class PullInRingLayout implements Layout {

	private static final int MARGIN = 50;

	@Override
    public void updateDesiredPositions(Logic logic, final int width, final int height) {
		Log.i("layout", "Layout w=" + width + " h=" + height);
		if (width == 0) {
			throw new RuntimeException("hellO");
		}
		final int N = logic.getVertexCount();
		for (int i=0; i<N; ++i) {
			final Vertex v = logic.getVertex(i);
			final int connCount = logic.getConnectionCount(i);
			final int targetCount = connCount + v.getRequired(); 
			double r = linterp(1, 0.1, logic.getConnectionCount(i), 0, targetCount);
			v.targetX = (int)( width/2 + ( width/2 - MARGIN) * r * Math.cos(i*2*Math.PI / N));
			v.targetY = (int)(height/2 + (height/2 - MARGIN) * r * Math.sin(i*2*Math.PI / N));
		}
    }
	
}
