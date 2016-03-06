/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.minimal.mcode;

/**
 * Pops the top-of-stack value from the integer operand stack and stores
 * it in a local integer variable.
 */
public final class MStoreInteger extends MInstruction {

	private final int index;

	/**
	 * Constructor.
	 * @param index the variable index
	 */
	public MStoreInteger(final int index) {
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
		context.storeInteger(index, context.popInteger());
	}

}
