/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.minimal.simulation;

import java.util.ArrayList;
import java.util.List;
import com.google.common.collect.ImmutableList;
import name.martingeisse.minimal.mcode.MCodeEntry;
import name.martingeisse.minimal.mcode.direct.DirectMCodeEngine;
import name.martingeisse.minimal.simulation.lwjgl.LwjglWindow;
import name.martingeisse.minimal.simulation.subject.FullySimulatedSubject;
import name.martingeisse.minimal.simulation.subject.Subject;
import name.martingeisse.minimal.simulation.subject.SubjectNativeCallHandler;

/**
 *
 */
public class MCodeMain {

	/**
	 * The main method.
	 * @param args command-line arguments
	 * @throws Exception ...
	 */
	public static void main(String[] args) throws Exception {
		LwjglWindow window = new LwjglWindow(800, 600);
		Subject subject = new FullySimulatedSubject(window);
		List<MCodeEntry> code = new ArrayList<>();
		// code.add();
		DirectMCodeEngine engine = new DirectMCodeEngine(ImmutableList.copyOf(code), new SubjectNativeCallHandler(subject));
		engine.run();
	}
	
}
