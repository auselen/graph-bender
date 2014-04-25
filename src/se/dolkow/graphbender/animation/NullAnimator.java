package se.dolkow.graphbender.animation;

import se.dolkow.graphbender.logic.Vertex;

public class NullAnimator extends AbstractAnimator {

	@Override
    protected void updateVertex(Vertex v, double diff) {
	    v.x = v.targetX;
	    v.y = v.targetY;
    }

}
