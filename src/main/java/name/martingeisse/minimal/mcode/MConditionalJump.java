/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.minimal.mcode;

/**
 * Pops the top-of-stack value off the integer operand stack. If the value
 * is nonzero, jumps to the target location, otherwise continues with the
 * next instruction.
 */
public final class MConditionalJump extends MJumpLikeInstruction {

	/**
	 * Constructor.
	 * @param targetLocation the target location
	 */
	public MConditionalJump(final MLocation targetLocation) {
		super(targetLocation);
	}

	// override
	@Override
	public void execute(final MInstructionExecutionContext context) {
		if (context.popInteger() != 0) {
			// TODO
		}
	}

}
