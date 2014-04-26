package se.dolkow.graphbender.util;

public final class Utils {
	public static double clamp(double min, double v, double max) {
		return Math.min(Math.max(min, v), max);
	}
	
	public static double clamp01(double v) {
		return clamp(0, v, 1);
	}
	
	public static double linterp(double from, double to, double t) {
		t = clamp01(t);
		return from + t * (to-from);
	}
	
	public static double linterp(double from, double to, double t, double tMin, double tMax) {
		return linterp(from, to, (t-tMin)/(tMax-tMin));
	}
}
