package se.dolkow.graphbender.layout;

import se.dolkow.graphbender.logic.Logic;

public abstract class AbstractLayout implements Layout {

	protected final Logic logic;
	private long lastFrameTimeNanos;

	public AbstractLayout(Logic logic) {
		this.logic = logic;
		this.lastFrameTimeNanos = 0;
	}
	
	@Override
    public final void updatePositions(long frameTimeNanos) {
	    final long nanoDiff;
	    if (lastFrameTimeNanos == 0) {
	    	nanoDiff = 0;
	    } else {
			nanoDiff = frameTimeNanos - lastFrameTimeNanos;
	    }
	    update(nanoDiff);
    }
	
	protected abstract void update(long nanoDiff);
}
