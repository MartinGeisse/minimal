/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.minimal.simulation.vm;

import name.martingeisse.minimal.simulation.subject.Subject;

/**
 * The VM's interpreter. This object interprets code and manipulates a {@link Subject}.
 */
public interface Interpreter {

	/**
	 * Sets the code to interpret.
	 * 
	 * @param code the code
	 */
	public void setCode(byte[] code);
	
	/**
	 * Sets the subject to manipulate.
	 * 
	 * @param subject the subject
	 */
	public void setSubject(Subject subject);
	
	/**
	 * Runs this interpreter.
	 */
	public void run();
	
}
