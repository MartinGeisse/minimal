/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.minimal.mcode;

import java.lang.reflect.Method;

/**
 * Calls a native function (as seen from the MVM, not necessarily "native" in the sense the JVM
 * uses the word), taking arguments from the operand stacks.
 */
public final class MNativeCall extends MInstruction {

	private final Method method;

	/**
	 * Constructor.
	 * @param method the method to call
	 */
	public MNativeCall(final Method method) {
		this.method = method;
	}

	// override
	@Override
	public void execute(final MInstructionExecutionContext context) {
		// TODO
	}

}
