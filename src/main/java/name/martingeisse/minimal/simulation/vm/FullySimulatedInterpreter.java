/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.minimal.simulation.vm;


/**
 *
 */
public class FullySimulatedInterpreter extends AbstractInterpreter {

	// override
	@Override
	public void step() {
		int opcode = read();
		switch (opcode) {
			
		}
	}
	
}
