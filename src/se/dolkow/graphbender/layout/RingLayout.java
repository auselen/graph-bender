package se.dolkow.graphbender.layout;

import se.dolkow.graphbender.logic.Logic;
import se.dolkow.graphbender.logic.Vertex;

public class RingLayout implements Layout {

	private static final int MARGIN = 50;
	private final Logic mLogic;
	private int mWidth;
	private int mHeight;
	
	public RingLayout(Logic logic) {
		mLogic = logic;
	}

	@Override
    public void updateDesiredPositions() {
		final int N = mLogic.getVertexCount();
		final int w = (mWidth - 2*MARGIN) / 2;
		final int h = (mHeight - 2*MARGIN) / 2;
		for (int i=0; i<N; ++i) {
			final Vertex v = mLogic.getVertex(i);
			v.x = MARGIN + (int)(w * (1+Math.cos(i*2*Math.PI / N)));
			v.y = MARGIN + (int)(h * (1+Math.sin(i*2*Math.PI / N)));
		}
    }

	@Override
    public void updateBounds(int width, int height) {
	    mWidth  = width;
	    mHeight = height;
	    updateDesiredPositions();
    }

}
