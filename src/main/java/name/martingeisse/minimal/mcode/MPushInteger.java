/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.minimal.mcode;

/**
 * Pushes a constant integer value onto the integer operand stack.
 */
public final class MPushInteger extends MInstruction {

	private final int value;

	/**
	 * Constructor.
	 * @param value the value to push
	 */
	public MPushInteger(final int value) {
		this.value = value;
	}

	/**
	 * Getter method for the value.
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	// override
	@Override
	public void execute(final MInstructionExecutionContext context) {
		context.pushInteger(value);
	}

}
