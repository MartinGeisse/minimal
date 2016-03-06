/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.minimal.simulation.vm;

import name.martingeisse.minimal.simulation.subject.Subject;

/**
 *
 */
public abstract class AbstractInterpreter implements Interpreter {

	private byte[] code;
	private Subject subject;
	private int pc = 0;
	private boolean stopped;

	/**
	 * Getter method for the code.
	 * @return the code
	 */
	public final byte[] getCode() {
		return code;
	}

	// override
	@Override
	public final void setCode(final byte[] code) {
		this.code = code;
	}

	/**
	 * Getter method for the subject.
	 * @return the subject
	 */
	public final Subject getSubject() {
		return subject;
	}

	// override
	@Override
	public final void setSubject(final Subject subject) {
		this.subject = subject;
	}

	/**
	 * Getter method for the stopped.
	 * @return the stopped
	 */
	public final boolean isStopped() {
		return stopped;
	}

	// override
	@Override
	public final void run() {
		while (!stopped) {
			step();
		}
	}

	/**
	 * Executes a single instruction. This method ignores whether this interpreter has
	 * been stopped -- executing further instructions when stopped will lead to code
	 * being executed that wasn't meant for that!
	 */
	public abstract void step();
	
	/**
	 * Reads a byte from the code, advancing the PC.
	 * 
	 * @return the byte that was read, in the range 0..255
	 */
	protected final int read() {
		int result = code[pc] & 0xff;
		pc++;
		return result;
	}

}
