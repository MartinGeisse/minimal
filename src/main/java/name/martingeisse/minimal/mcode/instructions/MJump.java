/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.minimal.mcode.instructions;

import name.martingeisse.minimal.mcode.MInstructionExecutionContext;
import name.martingeisse.minimal.mcode.MLabel;

/**
 * Unconditionally jumps to the target label.
 */
public final class MJump extends MJumpLikeInstruction {

	/**
	 * Constructor.
	 * @param targetLabel the target label
	 */
	public MJump(final MLabel targetLabel) {
		super(targetLabel);
	}

	// override
	@Override
	public void execute(final MInstructionExecutionContext context) {
		context.jump(gettargetLabel());
	}

}
