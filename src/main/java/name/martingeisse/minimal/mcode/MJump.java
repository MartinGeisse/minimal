/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.minimal.mcode;

/**
 * Unconditionally jumps to the target location.
 */
public final class MJump extends MJumpLikeInstruction {

	/**
	 * Constructor.
	 * @param targetLocation the target location
	 */
	public MJump(final MLocation targetLocation) {
		super(targetLocation);
	}

	// override
	@Override
	public void execute(final MInstructionExecutionContext context) {
		// TODO
	}

}
