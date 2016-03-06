/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.minimal.simulation;

import name.martingeisse.minimal.game.Native;
import name.martingeisse.minimal.game.experiment.Experiment1;
import name.martingeisse.minimal.simulation.lwjgl.LwjglWindow;
import name.martingeisse.minimal.simulation.subject.FullySimulatedSubject;
import name.martingeisse.minimal.simulation.subject.Subject;

/**
 *
 */
public class JavaMain {

	/**
	 * The main method.
	 * @param args command-line arguments
	 * @throws Exception ...
	 */
	public static void main(String[] args) throws Exception {
		LwjglWindow window = new LwjglWindow(800, 600);
		Subject subject = new FullySimulatedSubject(window);
		Native.subject = subject;
		Experiment1.main();
	}
	
}
