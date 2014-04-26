package se.dolkow.graphbender.layout;


/**
 * A Layout should set the targetX/targetY fields of a logic's vertices.  
 */
public interface Layout {
	/**
	 * Update target positions of vertices in this layout's vertex objects.
	 */
	public void updateDesiredPositions();

	/**
	 * Tell the layout about the width and height of our drawing area.
	 * @param width
	 * @param height
	 */
	public void updateBounds(int width, int height);
}
