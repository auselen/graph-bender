package se.dolkow.graphbender.animation;

import se.dolkow.graphbender.logic.Logic;

/** 
 * An object that will move Vertices towards their target positions. 
 */
public interface Animator {
	/**
	 * @param frameTimeNanos delta, in nanoseconds, from the last update.
	 */
	public void update(Logic logic, long nanoDelta);
}
