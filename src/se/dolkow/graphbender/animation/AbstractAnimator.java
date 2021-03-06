package se.dolkow.graphbender.animation;

import se.dolkow.graphbender.logic.Logic;
import se.dolkow.graphbender.logic.Vertex;

public abstract class AbstractAnimator implements Animator {

	@Override
    public final void update(Logic logic, long nanoDiff) {
	    final double diff = nanoDiff / 1000000000.0;
	    final int N = logic.getVertexCount();
	    for (int i=0; i<N; ++i) {
	    	updateVertex(logic.getVertex(i), diff);
	    }
    }

	/** 
	 * @param v the vertex to update
	 * @param diff the time diff since last update, in seconds
	 */
	protected abstract void updateVertex(Vertex v, double diff);

	protected final int sign(int val) {
		if (val < 0) {
			return -1;
		} else if (val > 0) {
			return 1;
		}
		return 0;
	}
}
