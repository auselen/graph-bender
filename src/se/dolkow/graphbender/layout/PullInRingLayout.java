package se.dolkow.graphbender.layout;

import se.dolkow.graphbender.logic.Logic;
import se.dolkow.graphbender.logic.Vertex;

import static se.dolkow.graphbender.util.Utils.linterp;

public class PullInRingLayout implements Layout {

	private static final int MARGIN = 50;
	private final Logic mLogic;
	private int mWidth;
	private int mHeight;
	
	public PullInRingLayout(Logic logic) {
		mLogic = logic;
	}

	@Override
    public void updateDesiredPositions() {
		final int midX = mWidth / 2;
		final int midY = mHeight / 2;
		
		final int halfW = mWidth/2 - MARGIN;
		final int halfH = mHeight/2 - MARGIN;
		
		final int N = mLogic.getVertexCount();
		
		for (int i=0; i<N; ++i) {
			final Vertex v = mLogic.getVertex(i);
			final int connCount = mLogic.getConnectionCount(i);
			final int targetCount = connCount + v.getRequired(); 
			double r = linterp(1, 0.1, mLogic.getConnectionCount(i), 0, targetCount);
			v.targetX = (int)( mWidth/2  + halfW * r * Math.cos(i*2*Math.PI / N));
			v.targetY = (int)(mHeight/2 + halfH * r * Math.sin(i*2*Math.PI / N));
		}
    }
	
	@Override
    public void updateBounds(int width, int height) {
	    mWidth  = width;
	    mHeight = height;
	    updateDesiredPositions();
    }

}
