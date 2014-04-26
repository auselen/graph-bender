package se.dolkow.graphbender.animation;

import se.dolkow.graphbender.logic.Vertex;

public class ProportionalAnimator extends AbstractAnimator {

	private static final double SPEED = 1.5;
	
	@Override
    protected void updateVertex(Vertex v, double timeDiff) {
	    v.x += calcMov(timeDiff, v.targetX - v.x);
	    v.y += calcMov(timeDiff, v.targetY - v.y);
    }

	private final int calcMov(double timeDiff, int delta) {
		int mov = (int)Math.round(SPEED * timeDiff * delta);
		if (mov == 0 && delta != 0) {
			if (delta < 0) {
				mov = -1;
			} else {
				mov = 1;
			}
		}
		return mov;
	}
}

