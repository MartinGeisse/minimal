/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.minimal.simulation;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import com.google.common.collect.ImmutableList;
import name.martingeisse.minimal.compiler.Compiler;
import name.martingeisse.minimal.game.experiment.Experiment1;
import name.martingeisse.minimal.mcode.MCodeEntry;
import name.martingeisse.minimal.mcode.direct.DirectMCodeEngine;
import name.martingeisse.minimal.simulation.lwjgl.LwjglWindow;
import name.martingeisse.minimal.simulation.subject.FullySimulatedSubject;
import name.martingeisse.minimal.simulation.subject.Subject;
import name.martingeisse.minimal.simulation.subject.SubjectNativeCallHandler;

/**
 *
 */
public class MCodeMain {

	/**
	 * The main method.
	 * @param args command-line arguments
	 * @throws Exception ...
	 */
	public static void main(final String[] args) throws Exception {

		// compile
		ImmutableList<MCodeEntry> code = null;
		{
			final ClassNode node = new ClassNode();
			new ClassReader(Experiment1.class.getName()).accept(node, Opcodes.ASM5);
			for (final Object untypedMethodNode : node.methods) {
				final MethodNode methodNode = (MethodNode)untypedMethodNode;
				if (methodNode.name.equals("main")) {
					final Compiler compiler = new Compiler();
					code = compiler.compile(methodNode);
					break;
				}
			}
			if (code == null) {
				throw new RuntimeException("could not find main method to compile");
			}
		}

		// run
		final LwjglWindow window = new LwjglWindow(800, 600);
		final Subject subject = new FullySimulatedSubject(window);
		final DirectMCodeEngine engine = new DirectMCodeEngine(ImmutableList.copyOf(code), new SubjectNativeCallHandler(subject));
		engine.run();
		
	}

}
