/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.minimal.mcode;


/**
 * The base class for all M instructions.
 */
public abstract class MInstruction {

	/**
	 * Executes this instruction.
	 * 
	 * @param context the context
	 */
	public abstract void execute(MInstructionExecutionContext context);
	
}
