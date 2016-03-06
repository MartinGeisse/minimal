/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.minimal.compiler;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
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
import name.martingeisse.minimal.game.Native;
import name.martingeisse.minimal.game.experiment.Experiment1;

/**
 *
 */
public class Main {

	/**
	 * The main method.
	 * @param args command-line arguments
	 * @throws Exception on errors
	 */
	public static void main(String[] args) throws Exception {
		ClassNode node = new ClassNode();
		new ClassReader(Experiment1.class.getName()).accept(node, Opcodes.ASM5);
		for (Object untypedMethodNode : node.methods) {
			MethodNode methodNode = (MethodNode)untypedMethodNode;
			if (methodNode.name.equals("main")) {
				for (int i=0; i<methodNode.instructions.size(); i++) {
					AbstractInsnNode instruction = methodNode.instructions.get(i);
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
						VarInsnNode varInsnNode = (VarInsnNode)instruction;
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
						IincInsnNode iincInsnNode = (IincInsnNode)instruction;
						iinc(iincInsnNode.var, iincInsnNode.incr);
					} else if (instruction instanceof JumpInsnNode) {
						JumpInsnNode jumpInsnNode = (JumpInsnNode)instruction;
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
						IntInsnNode intInsnNode = (IntInsnNode)instruction;
						if (instruction.getOpcode() == Opcodes.BIPUSH || instruction.getOpcode() == Opcodes.SIPUSH) {
							iconst(intInsnNode.operand);
						} else {
							// NEWARRAY
							unsupported(instruction); 
						}
					} else if (instruction instanceof MethodInsnNode) {
						MethodInsnNode methodInsnNode = (MethodInsnNode)instruction;
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
			}
		}
		System.out.println();
	}

	private static void unsupported(AbstractInsnNode node) {
		System.err.println("unsupported instruction node " + node + ", opcode: " + node.getOpcode());
	}
	
	private static void label(Label label) {
		System.out.println(label.toString() + ":");
	}
	
	private static void iconst(int value) {
		System.out.println("iconst " + value);
	}

	private static void iload(int index) {
		System.out.println("iload " + index);
	}

	private static void istore(int index) {
		System.out.println("istore " + index);
	}

	private static void iinc(int index, int amount) {
		System.out.println("iinc " + index + " by " + amount);
	}

	private static void jump(Label label) {
		System.out.println("jump" + label);
	}

	private static void branch1(Label label, int opcode) {
		System.out.println("branch(x vs. CONST) " + label + " on {" + opcode + "}");
	}

	private static void branch2(Label label, int opcode) {
		System.out.println("branch(x vs. y) " + label + " on {" + opcode + "}");
	}

	private static void jsr(Label label) {
		System.out.println("jsr" + label);
	}

	private static void nativeCall(String name, String descriptor) {
		System.out.println("native " + name + ": " + descriptor);
	}
	
}
