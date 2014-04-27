package se.dolkow.graphbender.util;

import java.util.Random;

public class TextGenerator {
	private static Random rand = new Random();
	private static final String[] LOSE_STRINGS = new String[] {
		"Try Again!", "Come on!", "You can do it!", "duh!", "Nope.", "You monster!",
		"Need... hugs...", "Are you even trying?"};
	private static final String[] WIN_STRINGS = new String[] {
		"You rock!", "wow!", "Brilliant!", "Excellent!", "Jellyfish love!", "Squee!",
		"Jiggle jiggle!", "Myup myup!", "Yay, hugs!", "♥ ♥ ♥"};
	
	public static String lose() {
		return LOSE_STRINGS[rand.nextInt(LOSE_STRINGS.length)];
	}
	
	public static String win() {
		return WIN_STRINGS[rand.nextInt(WIN_STRINGS.length)];
	}
}
