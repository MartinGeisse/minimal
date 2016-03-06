/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.minimal.mcode;

/**
 * Operators to use in an {@link MBinaryIntegerOperation}.
 */
public enum MBinaryIntegerOperator {

	/**
	 *
	 */
	ADD((x, y) -> x + y),

	/**
	 *
	 */
	SUBTRACT((x, y) -> x - y),

	/**
	 *
	 */
	MULTIPLY((x, y) -> x * y),

	/**
	 *
	 */
	DIVIDE((x, y) -> x / y),

	/**
	 *
	 */
	REMAINDER((x, y) -> x % y),
	
	/**
	 * 
	 */
	EQUAL((x, y) -> x == y ? 1 : 0),

	/**
	 * 
	 */
	NOT_EQUAL((x, y) -> x != y ? 1 : 0),
	
	/**
	 * 
	 */
	LESS_THAN((x, y) -> x < y ? 1 : 0),
	
	/**
	 * 
	 */
	LESS_THAN_OR_EQUAL((x, y) -> x <= y ? 1 : 0),
	
	/**
	 * 
	 */
	GREATER_THAN((x, y) -> x > y ? 1 : 0),
	
	/**
	 * 
	 */
	GREATER_THAN_OR_EQUAL((x, y) -> x >= y ? 1 : 0);
	
	private final Implementation implementation;

	/**
	 * Constructor.
	 * @param implementation the Java implementation of this operator
	 */
	private MBinaryIntegerOperator(final Implementation implementation) {
		this.implementation = implementation;
	}

	/**
	 * Applies this operator to the specified values, returning the result.
	 *
	 * @param x the first operand
	 * @param y the second operand
	 * @return the result
	 */
	public int apply(int x, int y) {
		return implementation.compute(x, y);
	}

	/**
	 *
	 */
	private static interface Implementation {
		public int compute(int x, int y);
	}

}
