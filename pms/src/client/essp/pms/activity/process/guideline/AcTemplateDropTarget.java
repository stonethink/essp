package client.essp.pms.activity.process.guideline;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;

import javax.swing.JOptionPane;

import c2s.essp.pms.wbs.DtoWbsActivity;
import c2s.essp.pms.wbs.DtoWbsTreeNode;
import client.framework.model.VMComboBox;
import client.framework.view.vwcomp.VWJComboBox;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author wuyi
 * @version 1.0
 */
public class AcTemplateDropTarget implements DropTargetListener {
    private VwAcGuideLine area;

    public AcTemplateDropTarget(VwAcGuideLine area) {
        this.area = area;
    }

    public DropTarget create() {
        return new DropTarget(area, this);
    }

    public void dragEnter(DropTargetDragEvent event) {
//        int a = event.getDropAction();
//        if ((a & DnDConstants.ACTION_MOVE) != 0) {
//            System.out.println("ACTION_MOVE dragEnter\n");
//        }
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

        int eventType = event.getDropAction();
        if ((eventType & DnDConstants.ACTION_MOVE) == 0) {
            return;
        }

        event.acceptDrop(event.getDropAction());
        Transferable transferable = event.getTransferable();


        event.getSource();

        DataFlavor[] flavors = transferable.getTransferDataFlavors();
        for (int i = 0; i < flavors.length; i++) {
            DataFlavor d = flavors[i];
            try {
                if (d.isMimeTypeEqual(DataFlavor.javaSerializedObjectMimeType)) {
                    DtoWbsTreeNode template = (DtoWbsTreeNode) transferable.
                                              getTransferData(d);
                    DtoWbsActivity wbs = ((DtoWbsActivity) template.getDataBean());
                    Long accountRid = wbs.getAcntRid();
                    Long activityRid = wbs.getActivityRid();
                    VWJComboBox tmpCmb = area.getUpGuideLine().getVwjComboBoxTemplate();
                    tmpCmb.setSelectedItem(((VMComboBox)tmpCmb.getModel()).findItemByValue(accountRid));
                    VWJComboBox wbsCmb = area.getUpGuideLine().getVwjComboBoxActivity();
                    wbsCmb.setSelectedItem(((VMComboBox)wbsCmb.getModel()).findItemByValue(activityRid));
                }
            } catch (Exception e) {

            }

        }
        event.dropComplete(true);
    }

    public boolean isDragAcceptable(DropTargetDragEvent event) {
        if(area.isReadOnly) {
            return false;
        }
        boolean acceptable = false;
        Transferable transferable = event.getTransferable();
        DataFlavor[] flavors = transferable.getTransferDataFlavors();
        if(flavors != null && flavors.length > 0
           && flavors[0].isMimeTypeEqual(DataFlavor.javaSerializedObjectMimeType)) {
            DtoWbsTreeNode template = null;
            try {
                Object obj = transferable.getTransferData(flavors[0]);
                if(obj instanceof DtoWbsTreeNode) {
                    template = (DtoWbsTreeNode) obj;
                    DtoWbsActivity wbs=((DtoWbsActivity)template.getDataBean());
                    acceptable = wbs.isActivity();
                }
            } catch (Exception ex) {
            }
        }
        return ((event.getDropAction()
                & DnDConstants.ACTION_COPY_OR_MOVE) != 0) && acceptable;
    }

    public boolean isDropAcceptable(DropTargetDropEvent event) {
        // usually, you check the available data flavors here
        // in this program, we accept all flavors
        return (event.getDropAction()
                & DnDConstants.ACTION_COPY_OR_MOVE) != 0;
    }
}
