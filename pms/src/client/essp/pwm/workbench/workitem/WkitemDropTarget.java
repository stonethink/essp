package client.essp.pwm.workbench.workitem;

import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;

import c2s.essp.pwm.workbench.DtoPwWkitem;
import c2s.essp.pwm.wp.IDtoScope;
import client.framework.view.vwcomp.VWJTable;

public class WkitemDropTarget implements DropTargetListener {
    private VWJTable tablePlan;

    public WkitemDropTarget(VWJTable aTable) {
        tablePlan = aTable;
    }

    public DropTarget create() {
        return new DropTarget(tablePlan, this);
    }

    public void dragEnter(DropTargetDragEvent event) {
        int a = event.getDropAction();
        if ((a & DnDConstants.ACTION_MOVE) != 0)
            System.out.println("ACTION_MOVE dragEnter\n");
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
        int dropRow = tablePlan.rowAtPoint(p);

        VMTableWorkItem model = (VMTableWorkItem) tablePlan.getModel();
        DataFlavor[] flavors = transferable.getTransferDataFlavors();
        for (int i = 0; i < flavors.length; i++) {
            DataFlavor d = flavors[i];
            try {
                if( d.isMimeTypeEqual(DataFlavor.javaSerializedObjectMimeType)){
                    IDtoScope scope = (IDtoScope)transferable.getTransferData(d);

                    DtoPwWkitem wkitem;
                    if (dropRow == model.getRows().size()) {
                        wkitem = model.getTheNewData();
                    } else {
                        wkitem = (DtoPwWkitem) model.getRow(dropRow);
                    }

                    if( wkitem != null ){
                        wkitem.setProjectId(scope.getAcntRid());
                        wkitem.setActivityId(scope.getActivityRid());
                        wkitem.setWpId(scope.getWpRid());

                        //Ë¢ÐÂbelongToÀ¸Î»
                        tablePlan.setValueAt(scope.getScopeInfo(), dropRow, 3);
                        model.fireTableCellUpdated(dropRow, 3);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        event.dropComplete(true);
    }

    public boolean isDragAcceptable(DropTargetDragEvent event) {
        // usually, you check the available data flavors here
        // in this program, we accept all flavors
        return (event.getDropAction()
                & DnDConstants.ACTION_COPY_OR_MOVE) != 0;
    }

    public boolean isDropAcceptable(DropTargetDropEvent event) {
        // usually, you check the available data flavors here
        // in this program, we accept all flavors
        return (event.getDropAction()
                & DnDConstants.ACTION_COPY_OR_MOVE) != 0;
    }
}
