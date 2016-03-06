/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.minimal.mcode;


/**
 * The base class for instructions and other things that can occur in code.
 * 
 * This subclasses may be value objects or objects with identity.
 */
public abstract class MCodeEntry {

	/**
	 * Executes this entry.
	 * 
	 * @param context the context
	 */
	public abstract void execute(MInstructionExecutionContext context);

}
