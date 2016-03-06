/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.minimal.game;

import name.martingeisse.minimal.simulation.subject.Subject;

/**
 * This class acts as a proxy for the {@link Subject}, as seen
 * from the game code.
 * 
 * In Java, this class keeps a {@link Subject} instance to allow simulation.
 * 
 * In native code, calls to methods of Native are replaced by a native operation.
 */
@SuppressWarnings("javadoc")
public final class Native {
	
	public static Subject subject;
	
	public static void drawCell(int x, int y, int pattern) {
		subject.drawCell(x, y, pattern);
	}
	
	public static void swapDisplayBuffers() {
		subject.swapDisplayBuffers();
	}
	
	public static void endOfFrame() {
		subject.endOfFrame();
	}
	
}
