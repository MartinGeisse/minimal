/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.minimal.simulation.vm;

import name.martingeisse.minimal.game.Native;
import name.martingeisse.minimal.game.experiment.Experiment1;
import name.martingeisse.minimal.simulation.lwjgl.LwjglWindow;
import name.martingeisse.minimal.simulation.subject.FullySimulatedSubject;
import name.martingeisse.minimal.simulation.subject.Subject;

/**
 *
 */
public class Main {

	/**
	 * The main method.
	 * @param args command-line arguments
	 * @throws Exception ...
	 */
	public static void main(String[] args) throws Exception {
		LwjglWindow window = new LwjglWindow(800, 600);
//		byte[] code = {
//			0, 1, 2
//		};
		Subject subject = new FullySimulatedSubject(window);
		
		/*
		subject.swapDisplayBuffers();
		for (int i=0; i<20; i++) {
			subject.drawCell(i, 0, i);
		}
		subject.swapDisplayBuffers();
		Thread.sleep(5000);
//		Interpreter interpreter = new FullySimulatedInterpreter();
//		interpreter.setCode(code);
//		interpreter.setSubject(subject);
//		interpreter.run();
 * */

		// simulate
		Native.subject = subject;
		Experiment1.main();
		
	}
	
}
