/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.minimal.simulation.subject;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import name.martingeisse.minimal.simulation.lwjgl.LwjglWindow;

/**
 *
 */
public final class FullySimulatedSubject implements Subject {

	private final LwjglWindow window;

	/**
	 * Constructor.
	 * @param window the LWJGL window
	 */
	public FullySimulatedSubject(final LwjglWindow window) {
		this.window = window;
	}

	// override
	@Override
	public void drawCell(final int x, final int y, int pattern) {
		if (pattern < 0 || pattern > 15) {
			pattern = 0;
		}
		switch (pattern) {

			case 0:
				GL11.glColor3f(0.0f, 0.0f, 0.0f);
				break;

			case 1:
				GL11.glColor3f(0.0f, 0.0f, 0.5f);
				break;

			case 2:
				GL11.glColor3f(0.0f, 0.5f, 0.0f);
				break;

			case 3:
				GL11.glColor3f(0.0f, 0.5f, 0.5f);
				break;

			case 4:
				GL11.glColor3f(0.5f, 0.0f, 0.0f);
				break;

			case 5:
				GL11.glColor3f(0.5f, 0.0f, 0.5f);
				break;

			case 6:
				GL11.glColor3f(0.5f, 0.5f, 0.0f);
				break;

			case 7:
				GL11.glColor3f(0.75f, 0.75f, 0.75f);
				break;

			case 8:
				GL11.glColor3f(0.5f, 0.5f, 0.5f);
				break;

			case 9:
				GL11.glColor3f(0.0f, 0.0f, 1.0f);
				break;

			case 10:
				GL11.glColor3f(0.0f, 1.0f, 0.0f);
				break;

			case 11:
				GL11.glColor3f(0.0f, 1.0f, 1.0f);
				break;

			case 12:
				GL11.glColor3f(1.0f, 0.0f, 0.0f);
				break;

			case 13:
				GL11.glColor3f(1.0f, 0.0f, 1.0f);
				break;

			case 14:
				GL11.glColor3f(1.0f, 1.0f, 0.0f);
				break;

			case 15:
				GL11.glColor3f(1.0f, 1.0f, 1.0f);
				break;

		}
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2i(x * 20, y * 20);
		GL11.glVertex2i(x * 20 + 20, y * 20);
		GL11.glVertex2i(x * 20 + 20, y * 20 + 20);
		GL11.glVertex2i(x * 20, y * 20 + 20);
		GL11.glEnd();
	}

	// override
	@Override
	public void swapDisplayBuffers() {
		try {
			Display.swapBuffers();
		} catch (final LWJGLException e) {
			throw new RuntimeException(e);
		}
		window.doGruntWork();
	}

	// override
	@Override
	public void endOfFrame() {
		// this doesn't handle slow frames as it should, but it's sufficient for now
		try {
			Thread.sleep(30);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}
