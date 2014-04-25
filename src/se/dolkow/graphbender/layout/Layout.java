package se.dolkow.graphbender.layout;

public interface Layout {
	/**
	 * Update positions of vertices in this layout's vertex objects.
	 * @param frameTimeNanos a timestamp (in nanoseconds) for the next frame 
	 */
	public void updatePositions(long frameTimeNanos);
}
