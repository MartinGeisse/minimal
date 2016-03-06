/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.minimal.mcode;

/**
 * Represents a native function that can be called from MCode.
 *
 * This object contains some information about the function's signature as
 * well as an opaque "token" that is used by the {@link NativeCallHandler}
 * to perform the actual function call.
 */
public final class NativeFunctionDescriptor {

	private final Object token;
	private final int integerParameterCount;
	private boolean hasIntegerReturnValue;

	/**
	 * Constructor.
	 * @param token the token that represents the actual native function
	 * @param integerParameterCount the number of integer parameters
	 * @param hasIntegerReturnValue whether the function returns an integer value
	 */
	public NativeFunctionDescriptor(final Object token, final int integerParameterCount, final boolean hasIntegerReturnValue) {
		this.token = token;
		this.integerParameterCount = integerParameterCount;
		this.hasIntegerReturnValue = hasIntegerReturnValue;
	}

	/**
	 * Getter method for the hasIntegerReturnValue.
	 * @return the hasIntegerReturnValue
	 */
	public boolean isHasIntegerReturnValue() {
		return hasIntegerReturnValue;
	}

	/**
	 * Setter method for the hasIntegerReturnValue.
	 * @param hasIntegerReturnValue the hasIntegerReturnValue to set
	 */
	public void setHasIntegerReturnValue(final boolean hasIntegerReturnValue) {
		this.hasIntegerReturnValue = hasIntegerReturnValue;
	}

	/**
	 * Getter method for the token.
	 * @return the token
	 */
	public Object getToken() {
		return token;
	}

	/**
	 * Getter method for the integerParameterCount.
	 * @return the integerParameterCount
	 */
	public int getIntegerParameterCount() {
		return integerParameterCount;
	}

}
