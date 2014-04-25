package se.dolkow.graphbender.animation;

import se.dolkow.graphbender.logic.Logic;

/** 
 * An object that will move Vertices towards their target positions. 
 */
public interface Animator {
	/**
	 * @param frameTimeNanos timestamp of the current frame
	 */
	public void update(Logic logic, long frameTimeNanos);
}
