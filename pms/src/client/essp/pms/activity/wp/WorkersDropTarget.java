package client.essp.pms.activity.wp;

import java.awt.dnd.DropTargetDropEvent;
import client.framework.view.vwcomp.VWJTable;
import java.awt.dnd.DnDConstants;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTargetEvent;
import java.awt.Point;
import java.awt.dnd.DropTarget;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DropTargetListener;
import java.awt.dnd.DropTargetDragEvent;
import c2s.essp.pms.wbs.DtoActivityWorker;
import c2s.essp.pwm.wp.DtoPwWp;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class WorkersDropTarget implements DropTargetListener {
    private VWJTable tablePlan;

    public WorkersDropTarget(VWJTable aTable) {
        tablePlan = aTable;
    }

    public DropTarget create() {
        return new DropTarget(tablePlan, this);
    }

    public void dragEnter(DropTargetDragEvent event) {
        int a = event.getDropAction();
        if ((a & DnDConstants.ACTION_MOVE) != 0) {
            System.out.println("ACTION_MOVE dragEnter\n");
        }
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

        VMWpListModel model = (VMWpListModel) tablePlan.getModel();
        DataFlavor[] flavors = transferable.getTransferDataFlavors();
        for (int i = 0; i < flavors.length; i++) {
            DataFlavor d = flavors[i];
            try {
                if (d.isMimeTypeEqual(WorkersTransferable.MIME_TYPE)) {
                    DtoActivityWorker worker = (DtoActivityWorker) transferable.
                                               getTransferData(d);
                    DtoPwWp pwWp = (DtoPwWp) model.getRow(dropRow);

                    if (pwWp != null) {
                        String wkLoginId = worker.getLoginId();
                        String wpWorkers = pwWp.getWpWorker();

                        if (wpWorkers == null || "".equals(wpWorkers)) {
                            pwWp.setWpWorker(wkLoginId);
                            pwWp.setWpWorkerName(worker.getEmpName());
                        } else if (wpWorkers.indexOf(wkLoginId) < 0) {
                            pwWp.setWpWorker(wpWorkers + "," + wkLoginId);
                            pwWp.setWpWorkerName(pwWp.getWpWorkerName() +
                                                 "," + worker.getEmpName());
                        }
                        model.fireTableDataChanged();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        event.dropComplete(true);
    }

    public boolean isDragAcceptable(DropTargetDragEvent event) {
        //disallow drag when dto is read only
        if(((VMWpListModel) tablePlan.getModel()).dtoActivity.isReadonly()) {
            return false;
        }
        boolean acceptable = false;
        Transferable transferable = event.getTransferable();
        DataFlavor[] flavors = transferable.getTransferDataFlavors();
        if(flavors != null && flavors.length > 0) {
            try {
                acceptable = flavors[0].isMimeTypeEqual(WorkersTransferable.
                    MIME_TYPE);
                acceptable = (transferable.getTransferData(flavors[0]) instanceof
                              DtoActivityWorker) && acceptable;
            } catch(Exception e) {
            }
        }
        return ((event.getDropAction()
                 & DnDConstants.ACTION_COPY_OR_MOVE) != 0)
            && acceptable;
    }

    public boolean isDropAcceptable(DropTargetDropEvent event) {
        // usually, you check the available data flavors here
        // in this program, we accept all flavors
        return (event.getDropAction()
                & DnDConstants.ACTION_COPY_OR_MOVE) != 0;
    }
}
