/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.minimal.simulation.subject;

import java.lang.reflect.Method;
import java.util.Arrays;
import name.martingeisse.minimal.mcode.NativeCallHandler;
import name.martingeisse.minimal.mcode.NativeFunctionDescriptor;

/**
 * Adapter class that acts as a {@link NativeCallHandler} for the MCode engine
 * and delegates native calls to a {@link Subject}.
 */
public final class SubjectNativeCallHandler implements NativeCallHandler {

	/**
	 *
	 */
	public static final NativeFunctionDescriptor DRAW_CELL = buildDescriptor("drawCell", 3);

	/**
	 *
	 */
	public static final NativeFunctionDescriptor SWAP_DISPLAY_BUFFERS = buildDescriptor("swapDisplayBuffers", 0);

	/**
	 *
	 */
	public static final NativeFunctionDescriptor END_OF_FRAME = buildDescriptor("endOfFrame", 0);

	private final Subject subject;

	/**
	 * Constructor.
	 * @param subject the subject
	 */
	public SubjectNativeCallHandler(final Subject subject) {
		this.subject = subject;
	}

	// override
	@Override
	public int call(final NativeFunctionDescriptor descriptor, final int[] arguments) {
		try {
			final Method method = (Method)descriptor.getToken();
			final Object[] convertedArguments = new Object[arguments.length];
			for (int i = 0; i < arguments.length; i++) {
				convertedArguments[i] = arguments[i];
			}
			final Object returnValue = method.invoke(subject, convertedArguments);
			return (descriptor.isHasIntegerReturnValue() ? ((Integer)returnValue).intValue() : 0);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static NativeFunctionDescriptor buildDescriptor(final String name, final int integerParameterCount) {
		try {
			final Class<?>[] parameterTypes = new Class<?>[integerParameterCount];
			Arrays.fill(parameterTypes, Integer.TYPE);
			final Method method = Subject.class.getMethod(name, parameterTypes);
			return new NativeFunctionDescriptor(method, integerParameterCount, method.getReturnType() == Integer.TYPE);
		} catch (final NoSuchMethodException e) {
			throw new RuntimeException("cannot build descriptor: " + name + "(" + integerParameterCount + ")");
		}
	}

}
