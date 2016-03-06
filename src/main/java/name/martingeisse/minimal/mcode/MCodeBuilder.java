/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.minimal.mcode;

import java.util.ArrayList;
import java.util.List;
import com.google.common.collect.ImmutableList;

/**
 * Used to build an MCode sequence.
 */
public class MCodeBuilder {

	private final List<MCodeEntry> code = new ArrayList<>();
	
	/**
	 * Adds an entry (instruction, label or similar).
	 * 
	 * @param entry the entry to add
	 * @return the argument (useful for one-liners)
	 */
	public <T extends MCodeEntry> T add(T entry) {
		code.add(entry);
		return entry;
	}
	
	/**
	 * Builds the MCode sequence.
	 * 
	 * @return the MCode sequence
	 */
	public ImmutableList<MCodeEntry> build() {
		// TODO patch labels
		return ImmutableList.copyOf(code);
	}
	
}
