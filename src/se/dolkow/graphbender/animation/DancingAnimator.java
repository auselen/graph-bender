package se.dolkow.graphbender.animation;

import se.dolkow.graphbender.logic.Vertex;

public class DancingAnimator extends AbstractAnimator {
	private static final int SPEED = 1000;

	@Override
	protected void updateVertex(Vertex v, double timeDiff) {
	    final int dx = v.targetX - v.x;
	    final int dy = v.targetY - v.y;

	    final double distance = Math.min(timeDiff * SPEED, Math.sqrt(dx*dx + dy*dy));
	    final double angle = Math.atan2(dy, dx);
	    
	    int mx = (int)Math.round(distance * Math.cos(angle));
	    int my = (int)Math.round(distance * Math.sin(angle));
	    
	    if (mx == 0 && my == 0) {
	    	if (dx >= dy) {
	    		mx = sign(dx);
	    	}
	    	if (dx <= dy) {
	    		my = sign(dy);
	    	}
	    }
	    
	    v.x += mx;
	    v.y += my;
	}

}
