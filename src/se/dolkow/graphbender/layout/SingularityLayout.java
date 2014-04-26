package se.dolkow.graphbender.layout;

import se.dolkow.graphbender.logic.Logic;

/** A stupid layout that puts everything in the middle. */
public class SingularityLayout implements Layout {

	@Override
    public void updateDesiredPositions(Logic logic, int width, int height) {
	    final int N = logic.getVertexCount();
	    for (int i=0; i<N; ++i) {
			logic.getVertex(i).x = width/2;
			logic.getVertex(i).y = height/2;
	    }
    }

}
