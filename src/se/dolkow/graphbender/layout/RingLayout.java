package se.dolkow.graphbender.layout;

import se.dolkow.graphbender.logic.Logic;
import se.dolkow.graphbender.logic.Vertex;

public class RingLayout implements Layout {

	private static final int MARGIN = 50;
	
	@Override
    public void updateDesiredPositions(Logic logic, int width, int height) {
		final int N = logic.getVertexCount();
		final int w = (width - 2*MARGIN) / 2;
		final int h = (height - 2*MARGIN) / 2;
		for (int i=0; i<N; ++i) {
			final Vertex v = logic.getVertex(i);
			v.targetX = MARGIN + (int)(w * (1+Math.cos(i*2*Math.PI / N)));
			v.targetY = MARGIN + (int)(h * (1+Math.sin(i*2*Math.PI / N)));
		}
    }
}
