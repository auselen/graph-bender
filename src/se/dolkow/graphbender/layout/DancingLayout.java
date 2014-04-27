package se.dolkow.graphbender.layout;

import se.dolkow.graphbender.logic.Logic;

public class DancingLayout implements Layout {
	final int margin = 100;
	final int spriteW = 96;
	final int spriteS = 14;

	@Override
	public void updateDesiredPositions(Logic logic, int width, int height) {
	    final int N = logic.getVertexCount();
	    int lx = (width - margin * 2) / (spriteW + spriteS);
	    int lh = N / lx;
	    int topMagin = (height - (lh * (spriteW + spriteS))) / 2;
	    for (int i=0; i<N; ++i) {
			logic.getVertex(i).targetX = margin + (spriteW+spriteS) * (i % lx);
			logic.getVertex(i).targetY = topMagin + (spriteW+spriteS) * (i / lx);
	    }
	}

}
