/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.minimal.mcode;


/**
 * This interface hides the "outside world" of native-call instructions from the MCode engine.
 */
public interface NativeCallHandler {

	/**
	 * Calls a native function.
	 * 
	 * @param descriptor the descriptor for the function to call
	 * @param arguments the integer arguments
	 * @return the function's return value. The returned value should be ignored if the
	 * descriptor indicates that the function has no return value.
	 */
	public int call(NativeFunctionDescriptor descriptor, int[] arguments);
	
}
