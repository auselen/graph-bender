package se.dolkow.graphbender.layout;

import se.dolkow.graphbender.logic.Logic;


/**
 * A Layout should set the targetX/targetY fields of a logic's vertices.  
 */
public interface Layout {
	/**
	 * Update target positions of vertices in this layout's vertex objects.
	 * @param logic 
	 * @param width drawing area width
	 * @param height drawing area height
	 */
	public void updateDesiredPositions(Logic logic, int width, int height);

}
