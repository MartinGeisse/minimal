/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.minimal.mcode;

/**
 * Takes the two top-of-stack values from the integer operand stack, applies an
 * {@link MBinaryIntegerOperator} on them, and pushes the result onto the
 * integer operand stack.
 */
public final class MBinaryIntegerOperation extends MInstruction {

	private final MBinaryIntegerOperator operator;

	/**
	 * Constructor.
	 * @param operator the operator
	 */
	public MBinaryIntegerOperation(final MBinaryIntegerOperator operator) {
		this.operator = operator;
	}

	/**
	 * Getter method for the operator.
	 * @return the operator
	 */
	public MBinaryIntegerOperator getOperator() {
		return operator;
	}

	// override
	@Override
	public void execute(final MInstructionExecutionContext context) {
		int y = context.popInteger();
		int x = context.popInteger();
		context.pushInteger(operator.apply(x, y));
	}

}
