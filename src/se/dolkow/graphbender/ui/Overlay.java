package se.dolkow.graphbender.ui;

public interface Overlay extends Renderable {
	/** Update state for the next frame 
	 * @return true if this object is still alive, false if it should be removed
	 */
	public boolean update(long frameTime, long deltaTime);
}
