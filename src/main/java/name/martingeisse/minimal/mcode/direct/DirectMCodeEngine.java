/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.minimal.mcode.direct;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import com.google.common.collect.ImmutableList;
import name.martingeisse.minimal.mcode.MCodeEntry;
import name.martingeisse.minimal.mcode.MInstructionExecutionContext;
import name.martingeisse.minimal.mcode.MLabel;
import name.martingeisse.minimal.mcode.NativeCallHandler;
import name.martingeisse.minimal.mcode.NativeFunctionDescriptor;

/**
 * Takes MCode directly as an array of instructions, labels etc. and executes it.
 */
public final class DirectMCodeEngine {

	private final ImmutableList<MCodeEntry> code;
	private final NativeCallHandler nativeCallHandler;
	private boolean stopped;
	private int pc;
	private InstructionExecutionContext instructionExecutionContext;
	private Deque<Integer> integerOperandStack;
	private Map<Integer, Integer> localIntegerVariables;

	/**
	 * Constructor.
	 * @param code the code to execute
	 * @param nativeCallHandler the handler for native-call instructions
	 */
	public DirectMCodeEngine(final ImmutableList<MCodeEntry> code, final NativeCallHandler nativeCallHandler) {
		this.code = code;
		this.nativeCallHandler = nativeCallHandler;
		this.stopped = false;
		this.pc = 0;
		this.instructionExecutionContext = new InstructionExecutionContext();
		this.integerOperandStack = new LinkedList<>();
		this.localIntegerVariables = new HashMap<>();
	}

	/**
	 * Getter method for the stopped.
	 * @return the stopped
	 */
	public final boolean isStopped() {
		return stopped;
	}

	/**
	 * Runs the code.
	 */
	public final void run() {
		while (!stopped) {
			step();
		}
	}

	/**
	 * Executes a single instruction. This method ignores whether this interpreter has
	 * been stopped -- executing further instructions when stopped will lead to code
	 * being executed that wasn't meant for that!
	 */
	public void step() {
		if (pc == code.size()) {
			stopped = true;
			return;
		}
		final MCodeEntry entry = code.get(pc);
		pc++;
		entry.execute(instructionExecutionContext);
	}

	/**
	 *
	 */
	private class InstructionExecutionContext implements MInstructionExecutionContext {

		// override
		@Override
		public void pushInteger(final int value) {
			integerOperandStack.push(value);
		}

		// override
		@Override
		public int popInteger() {
			return integerOperandStack.pop();
		}

		// override
		@Override
		public void storeInteger(final int index, final int value) {
			localIntegerVariables.put(index, value);
		}

		// override
		@Override
		public int loadInteger(final int index) {
			final Integer value = localIntegerVariables.get(index);
			if (value == null) {
				throw new RuntimeException("local variable #" + index + " has not been stored yet");
			}
			return value;
		}

		// override
		@Override
		public void jump(final MLabel label) {
			final int newPc = code.indexOf(label);
			if (newPc == -1) {
				throw new RuntimeException("trying to jump to unknown label");
			}
			pc = newPc;
		}

		// override
		@Override
		public int nativeCall(final NativeFunctionDescriptor descriptor, final int[] arguments) {
			return nativeCallHandler.call(descriptor, arguments);
		}

	}

}
