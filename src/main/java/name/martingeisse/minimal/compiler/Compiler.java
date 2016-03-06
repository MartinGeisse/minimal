/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.minimal.compiler;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.IincInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;
import com.google.common.collect.ImmutableList;
import name.martingeisse.minimal.game.Native;
import name.martingeisse.minimal.mcode.MCodeBuilder;
import name.martingeisse.minimal.mcode.MCodeEntry;
import name.martingeisse.minimal.mcode.NativeFunctionDescriptor;
import name.martingeisse.minimal.mcode.instructions.MBinaryIntegerOperation;
import name.martingeisse.minimal.mcode.instructions.MBinaryIntegerOperator;
import name.martingeisse.minimal.mcode.instructions.MConditionalJump;
import name.martingeisse.minimal.mcode.instructions.MJump;
import name.martingeisse.minimal.mcode.instructions.MLoadInteger;
import name.martingeisse.minimal.mcode.instructions.MNativeCall;
import name.martingeisse.minimal.mcode.instructions.MPushInteger;
import name.martingeisse.minimal.mcode.instructions.MStoreInteger;
import name.martingeisse.minimal.simulation.subject.Subject;

/**
 * Compiles JVM bytecode to MCode. An instance can be used only once.
 */
public final class Compiler {

	private static Pattern NATIVE_CALL_DESCRIPTOR_PATTERN = Pattern.compile("\\((I*)\\)(I|V)");

	private MCodeBuilder builder = new MCodeBuilder();
	private LabelAllocator labelAllocator = new LabelAllocator();

	/**
	 * Compiles a {@link MethodNode} to MCode.
	 * 
	 * @param methodNode the method node to compile
	 * @return the compiled code
	 */
	public ImmutableList<MCodeEntry> compile(final MethodNode methodNode) {
		for (int i = 0; i < methodNode.instructions.size(); i++) {
			final AbstractInsnNode instruction = methodNode.instructions.get(i);
			if (instruction instanceof LineNumberNode) {
				// ignored
			} else if (instruction instanceof FrameNode) {
				// this could be useful in the future
			} else if (instruction instanceof LabelNode) {
				label(((LabelNode)instruction).getLabel());
			} else if (instruction instanceof InsnNode) {
				switch (instruction.getOpcode()) {

					case Opcodes.ICONST_M1:
						iconst(-1);
						break;

					case Opcodes.ICONST_0:
						iconst(0);
						break;

					case Opcodes.ICONST_1:
						iconst(1);
						break;

					case Opcodes.ICONST_2:
						iconst(2);
						break;

					case Opcodes.ICONST_3:
						iconst(3);
						break;

					case Opcodes.ICONST_4:
						iconst(4);
						break;

					case Opcodes.ICONST_5:
						iconst(5);
						break;

					default:
						unsupported(instruction);
						break;

				}
			} else if (instruction instanceof VarInsnNode) {
				final VarInsnNode varInsnNode = (VarInsnNode)instruction;
				switch (varInsnNode.getOpcode()) {

					case Opcodes.ILOAD:
						iload(varInsnNode.var);
						break;

					case Opcodes.ISTORE:
						istore(varInsnNode.var);
						break;

					default:
						unsupported(instruction);
						break;

				}
			} else if (instruction instanceof IincInsnNode) {
				final IincInsnNode iincInsnNode = (IincInsnNode)instruction;
				iinc(iincInsnNode.var, iincInsnNode.incr);
			} else if (instruction instanceof JumpInsnNode) {
				final JumpInsnNode jumpInsnNode = (JumpInsnNode)instruction;
				switch (jumpInsnNode.getOpcode()) {

					case Opcodes.IFEQ:
					case Opcodes.IFNE:
					case Opcodes.IFLT:
					case Opcodes.IFGE:
					case Opcodes.IFGT:
					case Opcodes.IFLE:
						branch1(jumpInsnNode.label.getLabel(), jumpInsnNode.getOpcode());
						break;

					case Opcodes.IF_ICMPEQ:
					case Opcodes.IF_ICMPNE:
					case Opcodes.IF_ICMPLT:
					case Opcodes.IF_ICMPGE:
					case Opcodes.IF_ICMPGT:
					case Opcodes.IF_ICMPLE:
						branch2(jumpInsnNode.label.getLabel(), jumpInsnNode.getOpcode());
						break;

					case Opcodes.IFNULL:
					case Opcodes.IFNONNULL:
						// unsupported: one-argument reference comparison operator
						unsupported(instruction);
						break;

					case Opcodes.IF_ACMPEQ:
					case Opcodes.IF_ACMPNE:
						// unsupported: two-argument reference comparison operator
						unsupported(instruction);
						break;

					case Opcodes.GOTO:
						jump(jumpInsnNode.label.getLabel());
						break;

					case Opcodes.JSR:
						jsr(jumpInsnNode.label.getLabel());
						break;

					default:
						unsupported(instruction);
						break;

				}
			} else if (instruction instanceof IntInsnNode) {
				final IntInsnNode intInsnNode = (IntInsnNode)instruction;
				if (instruction.getOpcode() == Opcodes.BIPUSH || instruction.getOpcode() == Opcodes.SIPUSH) {
					iconst(intInsnNode.operand);
				} else {
					// NEWARRAY
					unsupported(instruction);
				}
			} else if (instruction instanceof MethodInsnNode) {
				final MethodInsnNode methodInsnNode = (MethodInsnNode)instruction;
				if (methodInsnNode.getOpcode() == Opcodes.INVOKESTATIC) {
					if (methodInsnNode.owner.replace('/', '.').equals(Native.class.getName())) {
						nativeCall(methodInsnNode.name, methodInsnNode.desc);
					} else {
						unsupported(instruction);
					}
				} else {
					unsupported(instruction);
				}
			} else {
				unsupported(instruction);
			}
		}
		return builder.build();
	}

	private void unsupported(final AbstractInsnNode node) {
		System.err.println("unsupported instruction node " + node + ", opcode: " + node.getOpcode());
	}

	private void label(final Label label) {
		builder.add(labelAllocator.allocate(label));
	}

	private void iconst(final int value) {
		builder.add(new MPushInteger(value));
	}

	private void iload(final int index) {
		builder.add(new MLoadInteger(index));
	}

	private void istore(final int index) {
		builder.add(new MStoreInteger(index));
	}

	private void iinc(final int index, final int amount) {
		builder.add(new MLoadInteger(index));
		builder.add(new MPushInteger(amount));
		builder.add(new MBinaryIntegerOperation(MBinaryIntegerOperator.ADD));
		builder.add(new MStoreInteger(index));
	}

	private void jump(final Label label) {
		builder.add(new MJump(labelAllocator.allocate(label)));
	}

	private void branch1(final Label label, final int opcode) {
		builder.add(new MPushInteger(0));
		branch2(label, opcode);
	}

	private void branch2(final Label label, final int opcode) {
		builder.add(new MBinaryIntegerOperation(mapOperator(opcode)));
		builder.add(new MConditionalJump(labelAllocator.allocate(label)));
	}

	private void jsr(final Label label) {
		throw new UnsupportedOperationException("JSR instruction not yet supported");
	}

	private void nativeCall(final String name, final String descriptor) {
		final Matcher matcher = NATIVE_CALL_DESCRIPTOR_PATTERN.matcher(descriptor);
		if (!matcher.matches()) {
			throw new RuntimeException("cannot handle descriptor for native call to method " + name + ": " + descriptor);
		}
		final int integerParameterCount = matcher.group(1).length();
		final boolean hasIntegerReturnValue = matcher.group(2).equals("I");
		NativeFunctionDescriptor nativeFunctionDescriptor;
		try {
			final Class<?>[] parameterTypes = new Class<?>[integerParameterCount];
			Arrays.fill(parameterTypes, Integer.TYPE);
			final Method method = Subject.class.getMethod(name, parameterTypes);
			if (!method.getReturnType().equals(hasIntegerReturnValue ? Integer.TYPE : Void.TYPE)) {
				// this means the Subject.* method has a different return type than the Native.* method
				throw new RuntimeException("method from Subject class has unexpected return type " + method.getReturnType() + " for native call descriptor " + descriptor);
			}
			nativeFunctionDescriptor = new NativeFunctionDescriptor(method, integerParameterCount, hasIntegerReturnValue);
		} catch (final NoSuchMethodException e) {
			throw new RuntimeException("cannot build descriptor: " + name + "(" + integerParameterCount + ")");
		}
		builder.add(new MNativeCall(nativeFunctionDescriptor));
	}

	private MBinaryIntegerOperator mapOperator(final int jvmOpcode) {
		switch (jvmOpcode) {

			case Opcodes.IFEQ:
			case Opcodes.IF_ICMPEQ:
				return MBinaryIntegerOperator.EQUAL;

			case Opcodes.IFNE:
			case Opcodes.IF_ICMPNE:
				return MBinaryIntegerOperator.NOT_EQUAL;

			case Opcodes.IFLT:
			case Opcodes.IF_ICMPLT:
				return MBinaryIntegerOperator.LESS_THAN;

			case Opcodes.IFLE:
			case Opcodes.IF_ICMPLE:
				return MBinaryIntegerOperator.LESS_THAN_OR_EQUAL;

			case Opcodes.IFGT:
			case Opcodes.IF_ICMPGT:
				return MBinaryIntegerOperator.GREATER_THAN;

			case Opcodes.IFGE:
			case Opcodes.IF_ICMPGE:
				return MBinaryIntegerOperator.GREATER_THAN_OR_EQUAL;

			default:
				throw new IllegalArgumentException("cannot map comparison operator for JVM opcode: " + jvmOpcode);

		}
	}

}
