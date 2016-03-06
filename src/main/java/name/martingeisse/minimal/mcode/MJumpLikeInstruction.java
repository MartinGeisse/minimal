/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.minimal.mcode;

/**
 * A jump-like instruction, that is, an instruction that (possibly) transfers
 * control to an {@link MLocation}.
 */
public abstract class MJumpLikeInstruction extends MInstruction {

	private final MLocation targetLocation;

	/**
	 * Constructor.
	 * @param targetLocation the target location to jump to
	 */
	public MJumpLikeInstruction(final MLocation targetLocation) {
		this.targetLocation = targetLocation;
	}

	/**
	 * Getter method for the targetLocation.
	 * @return the targetLocation
	 */
	public MLocation getTargetLocation() {
		return targetLocation;
	}
	
}
