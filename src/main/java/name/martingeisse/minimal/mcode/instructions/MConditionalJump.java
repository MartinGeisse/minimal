/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.minimal.mcode.instructions;

import name.martingeisse.minimal.mcode.MInstructionExecutionContext;
import name.martingeisse.minimal.mcode.MLabel;

/**
 * Pops the top-of-stack value off the integer operand stack. If the value
 * is nonzero, jumps to the target label, otherwise continues with the
 * next instruction.
 */
public final class MConditionalJump extends MJumpLikeInstruction {

	/**
	 * Constructor.
	 * @param targetLabel the target label
	 */
	public MConditionalJump(final MLabel targetLabel) {
		super(targetLabel);
	}

	// override
	@Override
	public void execute(final MInstructionExecutionContext context) {
		if (context.popInteger() != 0) {
			context.jump(gettargetLabel());
		}
	}

}
