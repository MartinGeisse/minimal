/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.minimal.simulation.lwjgl;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

/**
 * Uses LWJGL to open an OpenGL window.
 */
public final class LwjglWindow {

	/**
	 * Constructor.
	 * @param width the window width
	 * @param height the window height
	 */
	public LwjglWindow(int width, int height) {
		try {
			LwjglNativeLibraryHelper.prepareNativeLibraries();
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle("Minimal");
			Display.setFullscreen(true);
			Display.create(new PixelFormat(0, 24, 0));
			Keyboard.create();
		} catch (Exception e) {
			throw new RuntimeException("could not open LWJGL window", e);
		}
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, height, 0, -1, 1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
	}

	/**
	 * Does the grunt work necessary to get input from the OS.
	 */
	public void doGruntWork() {
		Display.processMessages();
		Keyboard.poll();
	}
	
	/**
	 * Closes the window.
	 */
	public void close() {
		Keyboard.destroy();
		Display.destroy();
	}

}
