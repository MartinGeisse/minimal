/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.minimal.mcode;

/**
 * Loads the value of a local integer variable and places it onto the integer
 * operand stack.
 */
public final class MLoadInteger extends MInstruction {

	private final int index;

	/**
	 * Constructor.
	 * @param index the variable index
	 */
	public MLoadInteger(final int index) {
		this.index = index;
	}

	/**
	 * Getter method for the index.
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	// override
	@Override
	public void execute(final MInstructionExecutionContext context) {
		context.pushInteger(context.loadInteger(index));
	}

}
