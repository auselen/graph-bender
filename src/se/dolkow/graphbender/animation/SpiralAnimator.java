package se.dolkow.graphbender.animation;

import se.dolkow.graphbender.logic.Vertex;

public class SpiralAnimator extends AbstractAnimator {

	private static final double SPEED = 5;
	
	@Override
    protected void updateVertex(Vertex v, double timeDiff) {
	    final int dx = v.targetX - v.x;
	    final int dy = v.targetY - v.y;

	    final double distance = ((v.id & 3) + SPEED) * timeDiff * Math.sqrt(dx*dx + dy*dy);
	    final double angle = Math.atan2(dy, dx) + 0.7;
	    
	    int mx = (int)Math.round(distance * Math.cos(angle));
	    int my = (int)Math.round(distance * Math.sin(angle));
	    
	    if (sign(mx) != sign(dx)) {
	    	mx = sign(dx);
	    }
	    if (sign(my) != sign(dy)) {
	    	my = sign(dy);
	    }
	    
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
