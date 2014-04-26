package se.dolkow.graphbender.ui;

import java.util.Random;

public class OverlayFactory {
	public static Random random = new Random();

	public static Overlay getRandom(String text) {
		switch (random.nextInt(3)) {
		case 0:
			return new FadingTextOverlay(text);
		case 1:
			return new ScalingTextOverlay(text, random.nextBoolean());
		default:
			return new ScrollingTextOverlay(text);
		}
	}
}
