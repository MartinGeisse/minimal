/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.minimal.mcode;


/**
 * A location in code. This is used, for example, as a target for jump instructions.
 * 
 * Instances have identity, that is, they are not value objects. In particular, an
 * instance of this class cannot occur more than once in code, and cannot be replaced
 * by another instance with equal properties.
 */
public final class MLabel extends MCodeEntry {

	// override
	@Override
	public void execute(MInstructionExecutionContext context) {
	}
	
}
