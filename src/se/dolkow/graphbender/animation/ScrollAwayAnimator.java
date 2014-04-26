package se.dolkow.graphbender.animation;

import se.dolkow.graphbender.logic.Vertex;

public class ScrollAwayAnimator extends AbstractAnimator {

	public static final int SPEED = 1000;
	
	@Override
    protected void updateVertex(Vertex v, double diff) {
	    v.x -= diff * SPEED;
    }

}
