package se.dolkow.graphbender.util;

import java.util.Random;

public class TextGenerator {
	private static Random rand = new Random();
	private static final String[] LOOSE_STRINGS = new String[] {
		"Try Again!", "Come on!", "You can do it!", "duh!"};
	private static final String[] WIN_STRINGS = new String[] {
		"You rock!", "wow!", "Brilliant!", "Excellent!", "Bend master!"};
	
	public static String loose() {
		return LOOSE_STRINGS[rand.nextInt(LOOSE_STRINGS.length)];
	}
	
	public static String win() {
		return WIN_STRINGS[rand.nextInt(WIN_STRINGS.length)];
	}
}
