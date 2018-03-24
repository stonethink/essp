package client.essp.timesheet.step.management.worker;

import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;

import c2s.dto.DtoBase;
import c2s.essp.common.user.DtoUserBase;
import c2s.essp.timesheet.step.management.DtoStep;
import client.framework.model.VMTable2;
import client.framework.view.vwcomp.VWJTable;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class WorkersDropTarget implements DropTargetListener {
	private VWJTable tableStep;

	public WorkersDropTarget(VWJTable aTable) {
		tableStep = aTable;
	}

	public DropTarget create() {
		return new DropTarget(tableStep, this);
	}

	public void dragEnter(DropTargetDragEvent event) {
		if (!isDragAcceptable(event)) {
			event.rejectDrag();
			return;
		}
	}

	public void dragExit(DropTargetEvent event) {
	}

	public void dragOver(DropTargetDragEvent event) {
		// you can provide visual feedback here
	}

	public void dropActionChanged(DropTargetDragEvent event) {
		if (!isDragAcceptable(event)) {
			event.rejectDrag();
			return;
		}
	}

	public void drop(DropTargetDropEvent event) {
		if (!isDropAcceptable(event)) {
			event.rejectDrop();
			return;
		}

		int eventType = event.getDropAction();
		if ((eventType & DnDConstants.ACTION_MOVE) == 0) {
			return;
		}

		event.acceptDrop(event.getDropAction());
		Transferable transferable = event.getTransferable();

		Point p = event.getLocation();
		int dropRow = tableStep.rowAtPoint(p);
		DataFlavor[] flavors = transferable.getTransferDataFlavors();
		VMTable2 tm = (VMTable2) tableStep.getModel();
		for (int i = 0; i < flavors.length; i++) {
			DataFlavor d = flavors[i];
			try {
				if (d.isMimeTypeEqual(WorkersTransferable.MIME_TYPE)) {
					DtoUserBase worker = (DtoUserBase) transferable
							.getTransferData(d);
					DtoStep step = (DtoStep) tm.getRow(dropRow);
					if (step != null) {
						step.setResourceId(worker.getUserLoginId());
						step.setResourceName(worker.getUserName());
						if (!DtoBase.OP_INSERT.equals(step.getOp())) {
							step.setOp(DtoBase.OP_MODIFY);
						}
						tm.fireTableDataChanged();
						tableStep.setSelectRow(dropRow);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		event.dropComplete(true);
	}

	public boolean isDragAcceptable(DropTargetDragEvent event) {
		return ((event.getDropAction() & DnDConstants.ACTION_COPY_OR_MOVE) != 0);
	}

	public boolean isDropAcceptable(DropTargetDropEvent event) {
		return (event.getDropAction() & DnDConstants.ACTION_COPY_OR_MOVE) != 0;
	}
}
