/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.minimal.mcode;


/**
 * Represents the environment in which {@link MInstruction#execute(MInstructionExecutionContext)} runs.
 */
public interface MInstructionExecutionContext {
	
	/**
	 * Pushes an integer value onto the integer operand stack.
	 * 
	 * @param value the value to push
	 */
	public void pushInteger(int value);

	/**
	 * Pops the top-of-stack from the integer operand stack and returns it.
	 * 
	 * @return the pop'ed value
	 */
	public int popInteger();
	
	/**
	 * Stores an integer value in a local integer variable.
	 * 
	 * @param index the variable index
	 * @param value the value to store
	 */
	public void storeInteger(int index, int value);
	
	/**
	 * Loads the value of a local integer variable and returns it.
	 * 
	 * @param index the variable index
	 * @return the loaded value
	 */
	public int loadInteger(int index);
	
	/**
	 * Jumps to the specified label.
	 * 
	 * @param label the label to jump to
	 */
	public void jump(MLabel label);

	/**
	 * Calls a native function.
	 * 
	 * @param descriptor the descriptor for the function to call
	 * @param arguments the integer arguments
	 * @return the function's return value. The returned value should be ignored if the
	 * descriptor indicates that the function has no return value.
	 */
	public int nativeCall(NativeFunctionDescriptor descriptor, int[] arguments);

}
