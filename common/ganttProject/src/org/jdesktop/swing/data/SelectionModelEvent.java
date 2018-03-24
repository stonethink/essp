/*
 * $Id: SelectionModelEvent.java,v 1.1 2006/10/10 03:51:00 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */
package org.jdesktop.swing.data;

import java.util.EventObject;

/**
 * Event for tracking changes in the selected rows in a SelectionModel
 * @author Richard Bair
 */
public class SelectionModelEvent extends EventObject {
	/**
	 * The first index in the range of indexes. Must be &gt;= 0.
	 */
	private int firstIndex;
	/**
	 * The last index in the range of indexes. firstIndex&lt;=lastIndex
	 */
	private int lastIndex;
	
	/**
	 * Create a new SelectionModelEvent. The range between firstIndex and lastIndex
	 * inclusive (firstIndex is less than or equal to lastIndex) represents a change
	 * in selection state of those rows. At least one of the rows within the range
	 * will have changed. A good SelectionModel implementation will keep the range
	 * as small as possible.
	 * @param source the SelectionModel that fired this event
	 * @param firstIndex the first index that changed
	 * @param lastIndex the last index that changed, lastIndex &gt;= firstIndex
	 */
	public SelectionModelEvent(SelectionModel source, int firstIndex, int lastIndex) {
		super(source);
		assert(firstIndex <= lastIndex);
		assert(firstIndex >= 0);
		this.firstIndex = firstIndex;
		this.lastIndex = lastIndex;
	}
	
	/**
	 * @return the first row whose selection value may have changed, where zero is the
	 * first row
	 */
	public int getFirstIndex() {
		return firstIndex;
	}
	
	/**
	 * @return the last row whose selection value may have changed, where zero is the
	 * last row
	 */
	public int getLastIndex() {
		return lastIndex;
	}
}
