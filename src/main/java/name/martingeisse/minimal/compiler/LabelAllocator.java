/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.minimal.compiler;

import java.util.HashMap;
import java.util.Map;
import org.objectweb.asm.Label;
import name.martingeisse.minimal.mcode.MLabel;

/**
 * Allocates {@link MLabel} objects for {@link Label} objects.
 */
public final class LabelAllocator {

	private final Map<Label, MLabel> map = new HashMap<>();
	
	/**
	 * Allocates an MCode label for an ASM label.
	 * 
	 * @param label the ASM label
	 * @return the MCode label
	 */
	public MLabel allocate(Label label) {
		MLabel mlabel = map.get(label);
		if (mlabel == null) {
			mlabel = new MLabel();
			map.put(label, mlabel);
		}
		return mlabel;
	}
	
}
