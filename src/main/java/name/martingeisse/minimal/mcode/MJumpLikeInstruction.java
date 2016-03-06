/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.minimal.mcode;

/**
 * A jump-like instruction, that is, an instruction that (possibly) transfers
 * control to an {@link MLabel}.
 */
public abstract class MJumpLikeInstruction extends MInstruction {

	private final MLabel targetLabel;

	/**
	 * Constructor.
	 * @param targetLabel the target label to jump to
	 */
	public MJumpLikeInstruction(final MLabel targetLabel) {
		this.targetLabel = targetLabel;
	}

	/**
	 * Getter method for the targetLabel.
	 * @return the targetLabel
	 */
	public MLabel gettargetLabel() {
		return targetLabel;
	}
	
}
