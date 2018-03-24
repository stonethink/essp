/*
 * ListSelectionBinding.java
 *
 * Created on February 24, 2005, 6:33 AM
 */

package org.jdesktop.swing.binding;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.jdesktop.swing.data.SelectionModel;
import org.jdesktop.swing.data.SelectionModelEvent;
import org.jdesktop.swing.data.SelectionModelListener;

/**
 *
 * @author rb156199
 */
public class ListSelectionBinding extends SelectionBinding {
    private boolean indexIsChanging = false;

    /** Creates a new instance of ListSelectionBinding */
    public ListSelectionBinding(final SelectionModel sm, final ListSelectionModel lsm) {
        super(sm);
        
		//listen for changes in the list selection and maintain the
		//synchronicity with the selection model
		lsm.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting() && !indexIsChanging) {
					sm.setSelectionIndices(getSelectedIndices(lsm));
				}
			}
		});
		//add a property change listener to listen for selection change events
		//in the selection model. Set the currently selected row(s) to be the
		//same as the current row(s) in the selection model
		sm.addSelectionModelListener(new SelectionModelListener() {
			public void selectionChanged(SelectionModelEvent evt) {
				indexIsChanging = true;
				setSelectedIndices(sm.getSelectionIndices(), lsm);
				indexIsChanging = false;
			}
		});
    }
    
    private int[] getSelectedIndices(ListSelectionModel sm) {
        int iMin = sm.getMinSelectionIndex();
        int iMax = sm.getMaxSelectionIndex();

        if ((iMin < 0) || (iMax < 0)) {
            return new int[0];
        }

        int[] rvTmp = new int[1+ (iMax - iMin)];
        int n = 0;
        for(int i = iMin; i <= iMax; i++) {
            if (sm.isSelectedIndex(i)) {
                rvTmp[n++] = i;
            }
        }
        int[] rv = new int[n];
        System.arraycopy(rvTmp, 0, rv, 0, n);
        return rv;
    }

    
    private void setSelectedIndices(int[] indices, ListSelectionModel sm) {
        sm.clearSelection();
        for(int i = 0; i < indices.length; i++) {
            sm.addSelectionInterval(indices[i], indices[i]);
        }
    }
}
