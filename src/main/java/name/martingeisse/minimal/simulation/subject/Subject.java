/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.minimal.simulation.subject;


/**
 * This object acts as a façade for whatever is manipulated by the VM and its code.
 */
public interface Subject {

	/**
	 * Draws a screen cell.
	 * 
	 * @param x the x coordinate of the cell
	 * @param y the y coordinate of the cell
	 * @param pattern the pattern to use for drawing
	 */
	public void drawCell(int x, int y, int pattern);

	/**
	 * Swaps display buffers.
	 */
	public void swapDisplayBuffers();

	/**
	 * Blocks until the time slice for the current frame has expired.
	 */
	public void endOfFrame();
	
}
