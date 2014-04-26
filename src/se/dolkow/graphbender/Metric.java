package se.dolkow.graphbender;

public class Metric {
	public static float TIMESTAMP_SIZE = 0;
	public static float SCALE;
	public static float VERTEX_RADIUS;
	public static float FINGER_SIZE;
	public static float VERTEX_TEXT_SIZE;

	public static void setScale(float scale) {
		SCALE = scale;
		VERTEX_RADIUS = 25 * scale;
		FINGER_SIZE = 30 * scale;
		TIMESTAMP_SIZE = 16 * scale;
		VERTEX_TEXT_SIZE = 25 * scale;
	}
}
