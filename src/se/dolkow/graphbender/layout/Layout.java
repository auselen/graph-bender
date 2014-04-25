package se.dolkow.graphbender.layout;

public interface Layout {
	/**
	 * Update positions of vertices in this layout's vertex objects.
	 */
	public void updateDesiredPositions();

	public void updateBounds(int width, int height);
}
