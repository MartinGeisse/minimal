/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.minimal.mcode.instructions;

import name.martingeisse.minimal.mcode.MInstruction;
import name.martingeisse.minimal.mcode.MInstructionExecutionContext;
import name.martingeisse.minimal.mcode.NativeFunctionDescriptor;

/**
 * Calls a native function (as seen from the MVM, not necessarily "native" in the sense the JVM
 * uses the word), taking arguments from the operand stacks.
 */
public final class MNativeCall extends MInstruction {

	private final NativeFunctionDescriptor descriptor;

	/**
	 * Constructor.
	 * @param descriptor the descriptor for the native function to call
	 */
	public MNativeCall(final NativeFunctionDescriptor descriptor) {
		this.descriptor = descriptor;
	}

	// override
	@Override
	public void execute(final MInstructionExecutionContext context) {
		int[] arguments = new int[descriptor.getIntegerParameterCount()];
		for (int i = arguments.length - 1; i >= 0; i--) {
			arguments[i] = context.popInteger();
		}
		int returnValue = context.nativeCall(descriptor, arguments);
		if (descriptor.isHasIntegerReturnValue()) {
			context.pushInteger(returnValue);
		}
	}

}
