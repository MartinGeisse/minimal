/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.minimal.game.experiment;

import name.martingeisse.minimal.game.Native;

/**
 *
 */
public class Experiment1 {

	/**
	 * 
	 */
	public static void main() {
		int pattern = 0;
		int counter = 0;
		while (true) {
			Native.drawCell(0, 0, pattern);
			Native.swapDisplayBuffers();
			Native.endOfFrame();
			counter++;
			if (counter == 10) {
				counter = 0;
				pattern++;
				if (pattern == 16) {
					pattern = 0;
				}
			}
		}
	}
	
}
