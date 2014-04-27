package se.dolkow.graphbender.layout;

import static se.dolkow.graphbender.util.Utils.linterp;
import se.dolkow.graphbender.Metric;
import se.dolkow.graphbender.logic.Logic;
import se.dolkow.graphbender.logic.Vertex;

public class PullInRingLayout implements Layout {

	// can't have these static, because class may be loaded before SCALE is set..
	private final int MARGIN = (int)(40 * Metric.SCALE);
	private final int MARGIN_TOP_EXTRA = (int)(20 * Metric.SCALE);

	@Override
    public void updateDesiredPositions(Logic logic, final int width, final int height) {
		final int N = logic.getVertexCount();
		for (int i=0; i<N; ++i) {
			final Vertex v = logic.getVertex(i);
			final int connCount = logic.getConnectionCount(i);
			final int targetCount = connCount + v.getRequired(); 
			double r = linterp(1, 0.1, logic.getConnectionCount(i), 0, targetCount);
			v.targetX = (int)( width/2 + ( width/2 - MARGIN) * r * Math.cos(i*2*Math.PI / N));
			v.targetY = (int)(height/2 + (height/2 - MARGIN - MARGIN_TOP_EXTRA) * r * Math.sin(i*2*Math.PI / N)) + MARGIN_TOP_EXTRA;
		}
    }
	
}
